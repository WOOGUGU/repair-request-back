package com.kkkoke.networkrepair.controller.exceptionController;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.kkkoke.networkrepair.exception.UserHasExistedException;
import com.kkkoke.networkrepair.exception.TokenErrorException;
import com.kkkoke.networkrepair.exception.UserHasNotExistedException;
import com.kkkoke.networkrepair.result.ApiResult;
import com.kkkoke.networkrepair.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    /**
     * Token过期
     *
     * @param e
     *            异常
     * @return ApiResult
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenExpiredException.class)
    public ApiResult tokenExpiredException(TokenExpiredException e) {
        log.info("TokenExpiredException.errMsg:{}", e.getMessage());
        return ApiResult.fail(ResultCode.EXPIRED_TOKEN, "登录已过期，请重新登录", e.getMessage());
    }

    /**
     * Token错误
     *
     * @param e
     *            异常
     * @return ApiResult
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenErrorException.class)
    public ApiResult tokenErrorException(TokenErrorException e) {
        log.info("TokenErrorException.errMsg:{}", e.getMessage());
        return ApiResult.fail(ResultCode.ERROR_TOKEN, "登录已过期，请重新登录", e.getMessage());
    }

    /**
     * 缺失query参数
     *
     * @param e
     *            异常
     * @return ApiResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResult missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.info("MissingServletRequestParameterException.errMsg:{}", e.getMessage());
        return ApiResult.fail(ResultCode.MISSING_PARAM, "缺少必要参数，请重试", e.getMessage());
    }

    /**
     * 用户已经存在
     *
     * @param e
     *            异常
     * @return ApiResult
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UserHasExistedException.class)
    public ApiResult userHasExistedException(UserHasExistedException e) {
        log.info("UserHasExistedException.errMsg:{}", e.getMessage());
        return ApiResult.fail(ResultCode.DATA_EXISTED, "用户已经存在，请重新输入", e.getMessage());
    }

    /**
     * 用户不存在
     *
     * @param e
     *            异常
     * @return ApiResult
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UserHasNotExistedException.class)
    public ApiResult userHasNotExistedException(UserHasNotExistedException e) {
        log.info("UserHasNotExistedException.errMsg:{}", e.getMessage());
        return ApiResult.fail(ResultCode.DATA_NOT_EXIST, "用户不存在，请重新输入", e.getMessage());
    }

    /**
     * 指定参数为空
     *
     * @param e
     *            异常
     * @return ApiResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResult constraintViolationException(ConstraintViolationException e) {
        log.info("ConstraintViolationException.errMsg:{}", e.getMessage());
        return ApiResult.fail(ResultCode.MISSING_PARAM, "缺少必要参数，请重试", e.getMessage());
    }
}
