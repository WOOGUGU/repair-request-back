package com.kkkoke.networkrepair.result;

/**
 * 返回码
 * <p>
 * A: 请求类错误
 * B: 权限类错误
 * C: 返回数据类错误
 */
public class ResultCode {
    public static final String SUCCESS = "00000";

    public static final String UNSUPPORTED_HTTP_METHOD = "A0100";

    public static final String MISSING_PARAM = "A0200";

    public static final String UNSUPPORTED_METHOD = "A0300";

    public static final String PARAM_ERROR = "A0400";

    public static final String PARAM_LENGTH_ERROR = "A0500";

    public static final String ILLEGAL_FORMDATA = "A0600";

    public static final String REQUEST_OVER_LIMIT = "A0700";

    public static final String EXPIRED_SESSION = "B0100";

    public static final String EFFECTIVE_SESSION = "B0200";

    public static final String UNAUTHENTICATED = "B0300";

    public static final String USERNAME_INVALID = "B0400";

    public static final String PWD_WRONG = "B0500";

    public static final String INVALID_OPERATION = "B0600";

    public static final String EXPIRED_TOKEN = "B0700";

    public static final String ERROR_TOKEN = "B0800";

    public static final String LOGIN_FAIL = "B0900";

    public static final String FORBIDDEN = "B1000";

    public static final String DATA_EXISTED = "C0100";

    public static final String DATA_NOT_EXIST = "C0200";
}
