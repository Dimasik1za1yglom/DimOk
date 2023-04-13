package ru.sen.searchserver.controller.api;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.searchserver.dto.SearchRequestDto;

public interface SearchUsersApi {

    @GetMapping("/users/search")
    String getSearchPage(Model model,
                         RedirectAttributes redirectAttributes);

    @PostMapping("/users")
    String getUsersBySearchRequest(@Valid SearchRequestDto requestDto, BindingResult bindingResult,
                                   Model model);


}
