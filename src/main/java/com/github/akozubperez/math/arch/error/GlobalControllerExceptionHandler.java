package com.github.akozubperez.math.arch.error;

import com.github.akozubperez.math.arch.common.MessageDto;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = {DeniedPermissionException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public MessageDto deniedPermissionException(DeniedPermissionException ex) {
        log.error("deniedPermissionException ",ex);
        return new MessageDto("Denied permission");
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageDto constraintViolationException(ConstraintViolationException ex) {
        log.error("constraintViolationException ",ex);
        return new MessageDto("Bad request");
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageDto internalServerError(Exception ex) {
        log.error("internalServerError ",ex);
        return new MessageDto("Internal error");
    }
}
