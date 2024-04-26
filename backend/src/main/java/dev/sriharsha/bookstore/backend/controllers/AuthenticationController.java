package dev.sriharsha.bookstore.backend.controllers;

import dev.sriharsha.bookstore.backend.dto.LoginRequest;
import dev.sriharsha.bookstore.backend.dto.RegisterRequest;
import dev.sriharsha.bookstore.backend.dto.Response;
import dev.sriharsha.bookstore.backend.entity.User;
import dev.sriharsha.bookstore.backend.security.CookieService;
import dev.sriharsha.bookstore.backend.security.JwtService;
import dev.sriharsha.bookstore.backend.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final CookieService cookieService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request)
            throws MessagingException {
        System.out.println("Email " + request.getEmail());
        Response response = authenticationService.register(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/activate/{activationToken}")
    public ResponseEntity<Response> activateAccount(@PathVariable String activationToken)
            throws MessagingException {
        Response response = authenticationService.activateAccount(activationToken);
        if (response.getData() == null) {
            return ResponseEntity.ok().body(response);
        } else {
            String jwtToken = jwtService.generateToken((User) response.getData());
            HttpCookie cookie = cookieService.generateCookie(jwtToken);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        Response response = authenticationService.login(request);
        String jwtToken = jwtService.generateToken((User) response.getData());
        HttpCookie cookie = cookieService.generateCookie(jwtToken);
        System.out.println("cookie " + cookie.toString());
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }
}
