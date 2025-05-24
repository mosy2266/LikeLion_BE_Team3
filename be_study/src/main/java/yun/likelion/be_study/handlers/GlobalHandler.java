package yun.likelion.be_study.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import yun.likelion.be_study.exception.ForbiddenException;
import yun.likelion.be_study.exception.UnauthenticatedException;

import java.util.Map;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> unauth(UnauthenticatedException e) {
        return Map.of("message", e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> forbid(ForbiddenException e) {
        return Map.of("message", e.getMessage());
    }
}
