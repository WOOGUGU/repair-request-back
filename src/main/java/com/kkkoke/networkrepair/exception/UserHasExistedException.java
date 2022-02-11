package com.kkkoke.networkrepair.exception;

public class UserHasExistedException extends Exception {
    public UserHasExistedException() {
    }

    public UserHasExistedException(String errMsg) {
        super(errMsg);
    }
}
