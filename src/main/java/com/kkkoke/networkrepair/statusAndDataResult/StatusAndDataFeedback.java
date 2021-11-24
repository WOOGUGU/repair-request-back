package com.kkkoke.networkrepair.statusAndDataResult;

import lombok.Data;

@Data
public class StatusAndDataFeedback {
    // 返回的数据
    private Object data;
    // 返回的状态值
    private String status;

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

    public StatusAndDataFeedback(Object data, String status) {
        this.data = data;
        this.status = status;
    }
}
