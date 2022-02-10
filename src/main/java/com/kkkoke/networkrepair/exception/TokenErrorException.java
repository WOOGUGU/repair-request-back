package com.kkkoke.networkrepair.exception;

public class TokenErrorException extends Exception {
    public TokenErrorException() {
    }

    public TokenErrorException(String errMsg) {
        super(errMsg);
    }
}
