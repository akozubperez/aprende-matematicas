package com.github.akozubperez.math.arch.security;

import com.github.akozubperez.math.arch.logging.Logging;
import java.util.ArrayList;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Profile("test-auth")
public class SimpleAuthenticationProvider implements AuthenticationProvider {

    @Logging(disabled = true)
    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        if (!username.isEmpty() && "flores".equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        } else {
            throw new BadCredentialsException("Wrong credentials");
        }
    }

    @Logging(disabled = true)
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
