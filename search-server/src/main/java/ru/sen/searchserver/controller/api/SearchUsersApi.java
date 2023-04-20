package ru.sen.searchserver.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.searchserver.dto.SearchRequestDto;

public interface SearchUsersApi {

    @GetMapping()
    String getSearchPage();

    @PostMapping("/users")
    String getUsersBySearchRequest(HttpServletRequest request,
                                   @Valid SearchRequestDto requestDto,
                                   BindingResult bindingResult,
                                   Model model);


}
