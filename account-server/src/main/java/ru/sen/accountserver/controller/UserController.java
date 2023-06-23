package ru.sen.accountserver.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.accountserver.controller.api.UserApi;
import ru.sen.accountserver.dto.UserDto;
import ru.sen.accountserver.dto.remote.ResponseDialogDto;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.gateway.DialogGateway;
import ru.sen.accountserver.jwt.entity.JwtResponse;
import ru.sen.accountserver.jwt.exception.AuthException;
import ru.sen.accountserver.jwt.service.AuthService;
import ru.sen.accountserver.jwt.util.cookie.CookieUtil;
import ru.sen.accountserver.kafka.producer.service.KafkaAlertService;
import ru.sen.accountserver.security.details.UserDetailsImpl;
import ru.sen.accountserver.services.AuthorizationDataService;
import ru.sen.accountserver.services.ErrorInterceptorService;
import ru.sen.accountserver.services.UserService;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements UserApi {

    private static final String jwtTokenCookieName = "JWT-TOKEN";

    private final AuthorizationDataService dataService;
    private final ErrorInterceptorService interceptorService;
    private final UserService userService;
    private final AuthService authService;
    private final DialogGateway dialogGateway;
    private final KafkaAlertService kafkaAlertService;

    @Override
    public String getUserProfile(HttpServletRequest request, Long userId, Model model, RedirectAttributes redirectAttributes) {
        log.info("/profile/{user-id}: request to receive the user's page by id {}", userId);
        try {
            User user = userService.getUserById(userId);
            model.addAttribute("user", user);
            log.info("/profile/{user-id}: getting a user page was successful: {}", user);
            Long createUserId = authService.getIdUserByRefreshToken(request);
            log.info("getting the token from the request was successful:user id {}", userId);
            ResponseDialogDto responseDialogDto = dialogGateway.existsDialog(createUserId, userId);
            log.info("getting a response from dialog service: {}", responseDialogDto);
            if (!responseDialogDto.isSuccess()) {
                model.addAttribute("createDialog", true);
                log.info("users don't have a common dialog. Users id {}, {}", createUserId, userId);
            } else {
                model.addAttribute("dialogId", responseDialogDto.getDialogId());
                log.info("users have a common dialog. Users id {}, {}", createUserId, userId);
            }
            return "user/userProfile";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Пользователь отсутсвует");
            log.error("/profile/{user-id}: Getting a user page is failed: {}", e.getMessage());
            return "redirect:user/searchUsers";
        } catch (AuthException e) {
            log.error("getting the token from the request was failed: {}", e.getMessage());
            return "redirect:/user/logout";
        }
    }

    @Override
    public String getMyProfile(Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            if (dataService.checkIfUserExists(getUserEmail())) {
                log.info("/myprofile: Checking that the user's page is empty. " +
                        "Redirecting to a page with fields filled in");
                return "user/userFields";
            } else {
                User user = dataService.getData(getUserEmail()).getUser();
                model.addAttribute("user", user);
                log.info("/myprofile: Checking that the user's page is full. Redirection to the user's page.");
                return "user/myProfile";
            }
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            log.error("/myprofile: An error occurred with the user's page: {}", e.getMessage());
            return "registration";
        }
    }

    @Override
    public String addUser(HttpServletRequest request,
                          HttpServletResponse httpServletResponse,
                          UserDto userDto,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        log.info("receiving a request for /add");
        var errors = Stream.<String>builder();
        if (bindingResult.hasErrors()) {
            log.warn("/add: Error entering values into the form");
            bindingResult.getAllErrors()
                    .forEach(objectError -> errors.add(objectError.getDefaultMessage()));
            model.addAttribute("user", userDto);
            model.addAttribute("errors", errors.build().toList());
            log.info("/add: Errors were received when filling out the form for creating user page fields: {}", errors);
            return "user/userFields";
        }
        if (userService.checkIfPhoneExists(userDto.getPhone())) {
            errors.add("Пользователь с таким номером уже существует. Проверте правильность");
            log.error("The received phone number is already in the database: {}", userDto.getPhone());
            model.addAttribute("user", userDto);
            model.addAttribute("errors", errors.build().toList());
            return "user/userFields";
        }
        log.info("the received phone number is unique");
        if (interceptorService.checkIfAddingUserSuccessful(userDto, getUserEmail())) {
            try {
                log.info("/add: Adding fields to the user's page was successful");
                JwtResponse response = authService.getRefresh(request);
                CookieUtil.create(httpServletResponse, jwtTokenCookieName, response, -1, "localhost");
                log.info("/add:Changing the user's token to a new one was successful");
                kafkaAlertService.sendAlertNewUser(userDto);
                log.info("sending a notification about a new user");
                return "redirect:/user/myprofile";
            } catch (AuthException e) {
                errors.add("Пользователь добавлен, но возникли ошибки. Попробуйте зайти заного");
                log.error("/add: Adding fields to the user's page wasn't successful");
                redirectAttributes.addFlashAttribute("errors", errors.build().toList());
                return "redirect:/user/logout";
            }
        } else {
            errors.add("Добавление полей пользователя не удалось. Попробуйте позднее");
            model.addAttribute("user", userDto);
            model.addAttribute("errors", errors.build().toList());
            log.error("/add: Errors occurred when adding user data: {}", errors.build().toList());
            return "user/userFields";
        }
    }

    @Override
    public String deleteUser(Long userId, RedirectAttributes redirectAttributes) {
        if (interceptorService.checkIfDeletingUserSuccessful(userId, getUserEmail())) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось удалить пользователя. Попробуйте позднее");
            log.error("/delete: Error on deleting a user under id: {}", userId);
            return "redirect:/user/myprofile";
        } else {
            log.info("/delete: Deleting the user was successful, exiting the session");
            return "redirect:/user/logout";
        }
    }

    @Override
    public String updateUser(HttpServletRequest request, UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("user", userDto);
            model.addAttribute("errors", errors);
            log.info("/update: Errors were received when filling out the form for change user page fields: {}", errors);
            return "user/changeFields";
        }
        try {
            Long userId = authService.getIdUserByRefreshToken(request);
            log.info("getting the token from the request was successful:user id {}", userId);
            if (interceptorService.checkIfUpdateUserSuccessful(userDto, userId)) {
                log.info("/update: user data update was successful");
                return "redirect:/user/myprofile";
            } else {
                model.addAttribute("error", "Не удалось изменить данные");
                log.error("/update: Sending a message that the user's data could not be updated");
                return "redirect:/user/change";
            }
        } catch (AuthException e) {
            log.error("getting the token from the request was failed: {}", e.getMessage());
            return "redirect:/user/logout";
        }
    }

    @Override
    public String changeUserFields(Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = dataService.getData(getUserEmail()).getUser();
            model.addAttribute("user", user);
            log.info("/change: getting a form of fields for changing user data");
            return "user/changeFields";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Заполните пожалуйста поля");
            log.error("/change: Errors occurred when change user data: {}", e.getMessage());
            return "redirect:user/changeFields";
        }
    }

    private String getUserEmail() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
