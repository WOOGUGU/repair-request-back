package com.kkkoke.networkrepair.exception;

public class PickerHasExistedException extends Exception {
    public PickerHasExistedException() {
    }

    public PickerHasExistedException(String errMsg) {
        super(errMsg);
    }
}
