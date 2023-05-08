package ru.sen.searchserver.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface AdminSearchUsersApi {

    @GetMapping("/users/all")
    String getAllUsers(HttpServletRequest request,
                       Model model,
                       RedirectAttributes redirectAttributes);
}
