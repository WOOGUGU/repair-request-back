package com.kkkoke.networkrepair.result;

public class ResultCode {
    public static final String SUCCESS = "00000";

    public static final String CLIENT_ERROR = "A0001";

    public static final String INVALID_PARAM = "A0100";

    public static final String UNSUPPORTED_HTTP_METHOD = "A0101";

    public static final String MISSING_PARAM = "A0102";

    public static final String UNAUTHENTICATED = "A0200";

    public static final String USERNAME_INVALID = "A0201";

    public static final String PASSWORD_WRONG = "A0202";

    public static final String TIME_OUT = "A0203";

    public static final String INVALID_SIGN = "A0204";

    public static final String CLIENT_NOT_EXIST = "A0205";

    public static final String USER_NOT_EXIST = "A0206";

    public static final String USER_NOT_BOUND_MAIL = "A0207";

    public static final String INVALID_CODE = "A0208";

    public static final String NOT_EXIST = "A0209";

    public static final String IS_DISABLED = "A0210";

    public static final String HAS_EXISTED = "A0211";

    public static final String USER_HAS_NOT_BIND = "A0212";

    public static final String NO_PERMISSION = "A0300";

    public static final String QUOTA_EXHAUSTED = "A0301";

    public static final String PARAM_CONFLICT = "A0303";

    public static final String USER_NOT_ACTIVATED = "A0304";

    public static final String PRIVATE_EXTERNAL_INTERFACE_CALL_FAILED = "A0400";

    public static final String USER_VISITED = "A0402";

    public static final String HAS_BEEN_DELETED = "A0404";

    public static final String HAS_BEEN_CLOSED = "A0405";

    public static final String HOMEWORK_ERROR = "A0408";

    public static final String DEADLINE_NOT_REACHED = "A0409";

    public static final String REQUEST_FILE_ERROR = "A0410";

    public static final String SUBMIT_DATE_BEYOND_CURRENT = "A0411";

    public static final String FILE_EXCEEDS_LIMIT = "A0412";

    public static final String OPERATE_FREQUENTLY = "A0413";

    public static final String INVALID_OPERATION = "A0414";

    public static final String FILE_NOT_AVAILABLE = "A0415";

    public static final String WECHAT_BOUND_ERROR = "A0500";

    public static final String WECHAT_HAS_BEEN_BOUND = "A0501";

    public static final String USER_HAS_BOUND_OTHER_WECHAT = "A0502";

    public static final String INTERNAL_SERVER_ERROR = "B0001";

    public static final String ERROR_WRITING_OPERATION_LOG = "B0100";

    public static final String INTERNAL_INTERFACE_CALL_ERROR = "B0200";

    public static final String DATABASE_ERROR = "B0300";

    public static final String EXTERNAL_INTERFACE_CALL_FAILED = "C0001";

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
}
