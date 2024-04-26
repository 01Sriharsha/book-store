package dev.sriharsha.bookstore.backend.service;

import dev.sriharsha.bookstore.backend.dto.LoginRequest;
import dev.sriharsha.bookstore.backend.dto.RegisterRequest;
import dev.sriharsha.bookstore.backend.dto.Response;
import dev.sriharsha.bookstore.backend.entity.Role;
import dev.sriharsha.bookstore.backend.entity.Token;
import dev.sriharsha.bookstore.backend.entity.User;
import dev.sriharsha.bookstore.backend.enums.EmailTemplate;
import dev.sriharsha.bookstore.backend.repository.RoleRepository;
import dev.sriharsha.bookstore.backend.repository.TokenRepository;
import dev.sriharsha.bookstore.backend.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    public Response register(RegisterRequest request) throws MessagingException {
        var existingUser = userRepository.findByEmail(request.getEmail()).isPresent();
        if (existingUser) {
            throw new RuntimeException("User already exists");
        }
        Role role = roleRepository.findById(601)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        User user = User.builder()
                .fullname(request.getFullname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(role))
                .enabled(false)
                .build();
        User savedUser = userRepository.save(user);
        // send mail
        sendActivationMail(savedUser);

        String message = "Registration Successfull! Sent an activation code to your email.";
        return Response.builder().message(message).build();
    }

    public Response activateAccount(String activationToken) throws MessagingException {
        final String message;
        Token foundToken = tokenRepository.findByToken(activationToken)
                .orElseThrow(() -> new RuntimeException("Activation Token not found"));
        boolean isExpired = foundToken.getExpiresAt().isBefore(LocalDateTime.now());
        User user = foundToken.getUser();

        if (isExpired) {
            tokenRepository.deleteById(foundToken.getId());
            sendActivationMail(user);
            message = "The activation code has been expired! A new code was sent to your email.";
            return Response.builder().message(message).build();
        } else {
            user.setEnabled(true);
            User enabledUser = userRepository.save(user);
            message = "Account successfully activated";
            return Response.builder().message(message).data(enabledUser).build();
        }
    }

    public Response login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);
        User user = (User) authentication.getPrincipal();
        String message = "Welcome back! " + user.getFullname();
        return Response.builder().message(message).data(user).build();
    }

    private void sendActivationMail(User user) throws MessagingException {
        String activationToken = getActivationToken(user);
        // send email
        emailService.sendEmail(
                user.getEmail(),
                user.getFullname(),
                EmailTemplate.ACTIVATE_ACCOUNT,
                activationToken,
                "Account Activation");
    }

    private String getActivationToken(User user) {
        String code = generateActivationCode(6);
        Token token = Token.builder()
                .token(code)
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        Token savedToken = tokenRepository.save(token);
        return savedToken.getToken();
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            var randomIndex = random.nextInt(characters.length());
            codeBuilder.append(randomIndex);
        }
        return codeBuilder.toString();
    }
}
