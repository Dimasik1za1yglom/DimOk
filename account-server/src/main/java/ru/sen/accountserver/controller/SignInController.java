package ru.sen.accountserver.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.accountserver.controller.api.SignInApi;
import ru.sen.accountserver.dto.AuthorizationDataDto;
import ru.sen.accountserver.jwt.entity.JwtResponse;
import ru.sen.accountserver.jwt.exception.AuthException;
import ru.sen.accountserver.jwt.service.AuthService;
import ru.sen.accountserver.jwt.util.cookie.CookieUtil;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/input")
public class SignInController implements SignInApi {

    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private final AuthService authService;

    @Override
    public String input() {
        return "input";
    }

    @Override
    public String input(HttpServletResponse httpServletResponse,
                        AuthorizationDataDto dataDto,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            redirectAttributes.addFlashAttribute("errors", errors);
            log.error("/input: Errors were received when entering data. Do not meet certain requirements: {}", errors);
            return "redirect:/input";
        }
        try {
            JwtResponse response = authService.login(dataDto);
            CookieUtil.create(httpServletResponse, jwtTokenCookieName, response, -1, "localhost");
            log.info("/input: the user's login was successful. Data {}. Token issued {}", dataDto, response);
            return "redirect:/user/myprofile";
        } catch (AuthException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            log.error("/input: Login error under such data {}: {}", dataDto, e.getMessage());
            return "redirect:/input";
        }
    }
}
