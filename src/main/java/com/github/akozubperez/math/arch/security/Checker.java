package com.github.akozubperez.math.arch.security;

import com.github.akozubperez.math.arch.auth.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("c")
@AllArgsConstructor
public class Checker {

    private final AuditorAware<String> autidorAware;
    
    private final UserService userService;

    @Transactional(readOnly = true)
    public boolean hasRole(String role) {
        return autidorAware.getCurrentAuditor()
                .map(u -> userService.getRoles(u).contains(role.toLowerCase()))
                .orElse(Boolean.FALSE);
    }

    @Transactional(readOnly = true)
    public boolean hasPrivilege(String privilege) {
        return autidorAware.getCurrentAuditor()
                .map(u -> userService.getPrivileges(u).contains(privilege.toUpperCase()))
                .orElse(Boolean.FALSE);
    }
}
