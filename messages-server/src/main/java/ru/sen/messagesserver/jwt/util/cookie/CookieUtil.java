package ru.sen.messagesserver.jwt.util.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.WebUtils;
import ru.sen.messagesserver.jwt.entity.JwtResponse;

public class CookieUtil {

    public static void create(HttpServletResponse httpServletResponse, String name, JwtResponse value, Integer maxAge, String domain) {
        Cookie cookie = new Cookie(name, value.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }

    public static String getValue(HttpServletRequest httpServletRequest, String name) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, name);
        return cookie != null ? cookie.getValue() : null;
    }
}
