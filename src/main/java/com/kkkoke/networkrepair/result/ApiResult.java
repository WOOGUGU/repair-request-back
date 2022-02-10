package com.kkkoke.networkrepair.result;

import io.swagger.annotations.Api;
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

    public static ApiResult fail(String code, String userMsg, String errMsg) {
        return new ApiResult(code, null, userMsg, errMsg);
    }

    // 返回给前端的状态值
    public final static String INCOMPLETE_DATA = "Incomplete_data"; // 前端传入的数据不完整
    public final static String HANDLE_SUCCESS = "handle_success"; // 处理成功
    public final static String DATA_NOT_EXIST = "data_not_exist"; // 数据库中不存在此数据
    public final static String DATA_EXIST = "data_exist"; // 数据库中已经存在此数据
    public final static String WRONG_USER = "wrong_user"; // 此用户不存在
    public final static String WRONG_PASSWORD = "wrong_password"; // 密码错误
    public final static String LOGIN_SUCCESS = "login_success"; // 登录成功
    public final static String USER = "user"; // 用户身份
    public final static String ADMIN = "admin"; // 管理员身份
    public final static String EXCEPTION_HAPPEN = "exception_happen"; // 出现异常
    public final static String WRONG_TOKEN = "wrong_token"; // token错误
    public final static String EXPIRED_TOKEN = "expired_token"; // token过期
}
