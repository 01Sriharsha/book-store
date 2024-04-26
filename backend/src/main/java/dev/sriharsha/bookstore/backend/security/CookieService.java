package dev.sriharsha.bookstore.backend.security;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import dev.sriharsha.bookstore.backend.util.Constants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CookieService {

    @Value("${application.security.jwt.expiration}")
    private Long EXPIRATION;

    public HttpCookie generateCookie(String token) {
        return ResponseCookie.from(Constants.COOKIE_NAME, token)
                .path("/")
                .httpOnly(true)
                .maxAge(Duration.ofMillis(EXPIRATION))
                .build();
    }

    public String getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        System.out.println("cookies length " + cookies.length);
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
