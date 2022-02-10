package com.kkkoke.networkrepair.controller.exceptionController;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.kkkoke.networkrepair.exception.AdminHasExistedException;
import com.kkkoke.networkrepair.exception.TokenErrorException;
import com.kkkoke.networkrepair.result.ApiResult;
import com.kkkoke.networkrepair.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        log.info("TokenExpiredException:{}", e.getMessage());
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
        log.info("TokenErrorException:{}", e.getMessage());
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
        log.info("missing param.errMsg:{}", e.getMessage());
        return ApiResult.fail(ResultCode.MISSING_PARAM, "请求出现问题，请重试", e.getMessage());
    }

    /**
     * 管理员已经存在
     *
     * @param e
     *            异常
     * @return ApiResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AdminHasExistedException.class)
    public ApiResult adminHasExistedException(AdminHasExistedException e) {
        log.info("missing param.errMsg:{}", e.getMessage());
        return ApiResult.fail(ResultCode.DATA_EXISTED, "管理员已经存在，请重新输入", e.getMessage());
    }
}
