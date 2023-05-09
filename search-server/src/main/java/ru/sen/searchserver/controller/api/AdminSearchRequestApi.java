package ru.sen.searchserver.controller.api;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface AdminSearchRequestApi {

    @GetMapping("/requests/{user-id}")
    String getSearchRequests(@PathVariable("user-id") Long userId, Model model,
                             RedirectAttributes redirectAttributes);

    @PostMapping("/requests/{user-id}/delete")
    String deleteSearchRequest(@PathVariable("user-id") Long userId,
                               RedirectAttributes redirectAttributes);
}
