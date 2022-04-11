package com.kkkoke.networkrepair.exception;

public class RequestLimitException extends Exception {
    public RequestLimitException() {
    }

    public RequestLimitException(String errMsg) {
        super(errMsg);
    }
}
