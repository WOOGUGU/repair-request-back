package com.kkkoke.networkrepair.exception;

public class UserHasNotExistedException extends Exception {
    public UserHasNotExistedException() {
    }

    public UserHasNotExistedException(String errMsg) {
        super(errMsg);
    }
}
