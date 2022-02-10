package com.kkkoke.networkrepair.exception;

public class PickerHasNotExistedException extends Exception {
    public PickerHasNotExistedException() {
    }

    public PickerHasNotExistedException(String errMsg) {
        super(errMsg);
    }
}
