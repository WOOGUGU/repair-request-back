package com.kkkoke.networkrepair.exception;

public class PasswordWrongException extends Exception {
    public PasswordWrongException() {
    }

    public PasswordWrongException(String errMsg) {
        super(errMsg);
    }
}
