package com.github.akozubperez.math.arch.auth.controllers;

import com.github.akozubperez.math.arch.auth.dtos.CredentialsDto;
import com.github.akozubperez.math.arch.auth.dtos.UserDto;
import com.github.akozubperez.math.arch.auth.services.UserService;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthenticationController {

    private static final ResponseEntity<?> FORBIDDEN_RESPONSE = ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

    private final AuthenticationManager authenticationManager;

    private final SessionRegistry sessionRegistry;

    private final UserService userService;

    private final AuditorAware<String> auditorAware;

    private static <T> ResponseEntity<T> forbidden() {
        return (ResponseEntity<T>) FORBIDDEN_RESPONSE;
    }

    @GetMapping("/auth/login")
    public ResponseEntity<UserDto> login() {
        return auditorAware.getCurrentAuditor()
                .map(username -> userService.findUser(username)
                .map(ResponseEntity::ok)
                .orElseGet(AuthenticationController::forbidden))
                .orElseGet(AuthenticationController::forbidden);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserDto> login(HttpServletRequest request, @RequestBody CredentialsDto credentialsDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentialsDto.getUsername(), credentialsDto.getPassword());
        try {
            return userService.findUser(credentialsDto.getUsername()).map(userDto -> {
                Authentication auth = authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
                sessionRegistry.registerNewSession(request.getSession().getId(), auth.getPrincipal());
                return ResponseEntity.ok(userDto);
            }).orElseGet(AuthenticationController::forbidden);
        } catch (BadCredentialsException ex) {
            log.error("Bad credentials error", ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
