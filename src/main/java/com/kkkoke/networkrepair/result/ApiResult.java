package com.kkkoke.networkrepair.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@ApiModel("通用返回")
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ApiResult {

    // 状态代码
    @ApiModelProperty(value = "状态码")
    private String code;

    // 返回的数据
    @ApiModelProperty(value = "数据")
    private Object data;

    // 返回的用户提示信息
    @ApiModelProperty(value = "用户提示信息")
    private String userMsg;

    // 返回的错误提示信息
    @ApiModelProperty(value = "debug报错提示信息")
    private String errMsg;

    // errMsg
    public final static String INCOMPLETE_DATA = "incomplete data"; // 前端传入的数据不完整
    public final static String HANDLE_SUCCESS = "handle success"; // 处理成功
    public final static String DATA_NOT_EXIST = "data not exist"; // 数据库中不存在此数据
    public final static String DATA_EXIST = "data exist"; // 数据库中已经存在此数据
    public final static String WRONG_USER = "wrong user"; // 此用户不存在
    public final static String WRONG_PASSWORD = "wrong password"; // 密码错误
    public final static String LOGIN_SUCCESS = "login success"; // 登录成功
    public final static String EXCEPTION_HAPPEN = "exception happen"; // 出现异常
    public final static String WRONG_TOKEN = "wrong token"; // token错误
    public final static String EXPIRED_TOKEN = "expired token"; // token过期
    public final static String UNAUTHENTICATED = "unauthenticated"; // token过期
    public final static String LOGIN_FAIL = "login fail"; // 登录失败
    public final static String FORBIDDEN = "forbidden"; // 权限不够
    public final static String MISSING_PARAM = "miss param"; // 缺少参数

    public static ApiResult fail(String code, String userMsg, String errMsg) {
        return new ApiResult(code, null, userMsg, errMsg);
    }

    public static ApiResult fail(String code, Object data, String userMsg, String errMsg) {
        return new ApiResult(code, data, userMsg, errMsg);
    }

    public static ApiResult success(String userMsg) {
        return new ApiResult(ResultCode.SUCCESS, null, userMsg, ApiResult.HANDLE_SUCCESS);
    }

    public static ApiResult success(Object data, String userMsg) {
        return new ApiResult(ResultCode.SUCCESS, data, userMsg, ApiResult.HANDLE_SUCCESS);
    }

    public static ApiResult success(String code, String userMsg, String errMsg) {
        return new ApiResult(ResultCode.SUCCESS, null, userMsg, errMsg);
    }

    public static ApiResult success(String code, Object data, String userMsg, String errMsg) {
        return new ApiResult(code, data, userMsg, errMsg);
    }
}
