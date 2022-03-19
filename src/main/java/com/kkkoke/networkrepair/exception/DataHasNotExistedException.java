package com.kkkoke.networkrepair.exception;

public class DataHasNotExistedException extends Exception {
    public DataHasNotExistedException() {
    }

    public DataHasNotExistedException(String errMsg) {
        super(errMsg);
    }
}
