package com.kkkoke.networkrepair.exception;

public class InternalApiFailedException extends Exception {
    public InternalApiFailedException() {
    }

    public InternalApiFailedException(String errMsg) {
        super(errMsg);
    }
}
