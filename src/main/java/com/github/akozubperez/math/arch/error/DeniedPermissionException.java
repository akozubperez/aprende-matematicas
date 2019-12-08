package com.github.akozubperez.math.arch.error;

import lombok.Getter;

public class DeniedPermissionException extends RuntimeException {
    
    @Getter
    private final String requiredPermission;
    
    public DeniedPermissionException(String message, String requiredPermission) {
        super(message);
        this.requiredPermission = requiredPermission;
    }
}
