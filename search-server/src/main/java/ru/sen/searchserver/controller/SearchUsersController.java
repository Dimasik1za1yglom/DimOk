package ru.sen.searchserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sen.searchserver.controller.api.SearchUsersApi;
import ru.sen.searchserver.dto.SearchRequestDto;
import ru.sen.searchserver.entity.User;
import ru.sen.searchserver.exception.SearchRequestException;
import ru.sen.searchserver.exception.SearchUsersException;
import ru.sen.searchserver.jwt.exception.AuthException;
import ru.sen.searchserver.jwt.service.AuthService;
import ru.sen.searchserver.services.SearchRequestService;
import ru.sen.searchserver.services.SearchUserService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchUsersController implements SearchUsersApi {

    private final SearchUserService searchUserService;
    private final SearchRequestService searchRequestService;
    private final AuthService authService;

    @Override
    public String getSearchPage() {
        log.info("receiving a request for /search");
        return "user/searchUsers";
    }

    @Override
    public String getUsersBySearchRequest(HttpServletRequest request,
                                          SearchRequestDto requestDto,
                                          BindingResult bindingResult,
                                          Model model) {
        log.info("receiving a request for /users by search request");
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("request", requestDto);
            model.addAttribute("errors", errors);
            log.info("/users: Errors were received when filling out the form for change search page fields: {}", errors);
            return "user/searchUsers";
        }
        try {
            Long userId = authService.getIdUserByRefreshToken(request);
            log.info("getting the token from the request was successful: user id {}", userId);
            searchRequestService.addSearchRequest(requestDto, LocalDateTime.now(), userId);
            List<User> users = searchUserService.getAllUsersByTextRequest(requestDto).stream()
                    .filter(user -> !user.getId().equals(userId)).toList();
            model.addAttribute("user", requestDto);
            if (users.isEmpty()) {
                List<String> errors = List.of("Пользоватлей по данному запросу не существует.");
                model.addAttribute("errors", errors);
            } else {
                model.addAttribute("users", users);
            }
            log.info("Was successful get users by search request {}, list : {}", requestDto, users);
            return "user/searchUsers";
        } catch (SearchUsersException | SearchRequestException e) {
            model.addAttribute("error", e.getMessage());
            log.error("Failed get users by search request {}, error : {}", requestDto, e.getMessage());
            return "user/searchUsers";
        } catch (AuthException e) {
            model.addAttribute("error", "Невозможно совершить поиск, попробуйте позднее");
            log.error("getting the token from the request was failed: {}", e.getMessage());
            return "user/searchUsers";
        }
    }
}
