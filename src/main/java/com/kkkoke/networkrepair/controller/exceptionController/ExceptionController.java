package com.kkkoke.networkrepair.controller.exceptionController;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.kkkoke.networkrepair.statusAndDataResult.StatusAndDataFeedback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenExpiredException.class)
    public StatusAndDataFeedback tokenExpiredException(TokenExpiredException e) {
        log.info("TokenExpiredException:{}", e.getMessage());

        return new StatusAndDataFeedback(true, StatusAndDataFeedback.EXPIRED_TOKEN);
    }
}
