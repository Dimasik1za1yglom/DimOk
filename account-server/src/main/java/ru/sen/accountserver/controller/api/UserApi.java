package ru.sen.accountserver.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.accountserver.dto.UserDto;

public interface UserApi {

    @GetMapping("/profile/{user-id}")
    String getUserProfile(HttpServletRequest request,
                          @PathVariable("user-id") Long userId,
                          Model model,
                          RedirectAttributes redirectAttributes);

    @GetMapping("/myprofile")
    String getMyProfile(Model model,
                        RedirectAttributes redirectAttributes);

    @PostMapping("/add")
    String addUser(HttpServletRequest request,
                   HttpServletResponse httpServletResponse,
                   @Valid UserDto userDto,
                   BindingResult bindingResult,
                   Model model,
                   RedirectAttributes redirectAttributes);

    @PostMapping("/{user-id}/delete")
    String deleteUser(@PathVariable("user-id") Long userId,
                      RedirectAttributes redirectAttributes);

    @PostMapping("/update")
    String updateUser(HttpServletRequest request,
                      @Valid UserDto userDto,
                      BindingResult bindingResult,
                      Model model);

    @GetMapping("/change")
    String changeUserFields(Model model, RedirectAttributes redirectAttributes);

}
