package com.kkkoke.networkrepair.result;

public class ResultCode {
    public static final String SUCCESS = "00000";

    public static final String UNSUPPORTED_HTTP_METHOD = "A0101";

    public static final String MISSING_PARAM = "A0102";

    public static final String UNAUTHENTICATED = "A0200";

    public static final String USERNAME_INVALID = "A0201";

    public static final String PWD_WRONG = "A0202";

    public static final String INVALID_OPERATION = "A0414";

    public static final String EXPIRED_TOKEN = "D0001";

    public static final String ERROR_TOKEN = "D0100";

    public static final String DATA_EXISTED = "E0001";

    public static final String DATA_NOT_EXIST = "E0100";

    public static final String LOGIN_FAIL = "F0001";

    public static final String FORBIDDEN = "F100";

    public static final String PARAM_ERROR = "F200";

    public static final String PARAM_LENGTH_ERROR = "F200";

    public static final String EXPIRED_SESSION = "F300";

    public static final String EFFECTIVE_SESSION = "F400";

    public static final String ILLegal_FORMDATA = "F500";

    public static final String REQUEST_OVER_LIMIT = "F600";
}
