package com.github.akozubperez.math.arch.security;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuditorServiceBase implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName().toLowerCase());
        }
        return Optional.empty();
    }
}
