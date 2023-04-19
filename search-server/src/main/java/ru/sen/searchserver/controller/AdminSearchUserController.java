package ru.sen.searchserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.searchserver.controller.api.AdminSearchUsersApi;
import ru.sen.searchserver.controller.api.SearchUsersApi;
import ru.sen.searchserver.dto.SearchRequestDto;
import ru.sen.searchserver.entity.User;
import ru.sen.searchserver.exception.SearchUsersException;
import ru.sen.searchserver.services.SearchUserService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/search/admin")
public class AdminSearchUserController implements AdminSearchUsersApi, SearchUsersApi {

    private final SearchUserService searchUserService;

    @Override
    public String getAllUsers(Model model, RedirectAttributes redirectAttributes) {
        log.info("receiving a request for /admin/users/all");
        try {
            List<User> users = searchUserService.getAllUsers();
            model.addAttribute("users", users);
            log.info("Was successful get all users: {}", users);
            return "admin/adminSearchUsers";
        } catch (SearchUsersException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            log.error("Failed get all users: {}", e.getMessage());
            return "redirect:admin/adminSearchUsers";
        }
    }

    @Override
    public String getSearchPage(Model model, RedirectAttributes redirectAttributes) {
        log.info("receiving a request for /admin/users/search");
        return "admin/adminSearchUsers";
    }

    @Override
    public String getUsersBySearchRequest(HttpServletRequest request, SearchRequestDto requestDto, BindingResult bindingResult, Model model) {
        log.info("receiving a request for /admin/users by search request");
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("request", requestDto);
            model.addAttribute("errors", errors);
            log.info("/users: Errors were received when filling out the form for change search page fields: {}", errors);
            return "admin/adminSearchUsers";
        }
        try {
            List<User> users = searchUserService.getAllUsersByTextRequest(requestDto);
            model.addAttribute("request", requestDto);
            model.addAttribute("users", users);
            log.info("Was successful get users by search request {}, list : {}", requestDto, users);
            return "admin/adminSearchUsers";
        } catch (SearchUsersException e) {
            model.addAttribute("error", e.getMessage());
            log.error("Failed get users by search request {}, error : {}", requestDto, e.getMessage());
            return "admin/adminSearchUsers";
        }
    }
}
