package com.kkkoke.networkrepair.exception;

public class DataHasExistedException extends Exception {
    public DataHasExistedException() {
    }

    public DataHasExistedException(String errMsg) {
        super(errMsg);
    }
}
