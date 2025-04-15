package com.inditex.domain.exception;

public class PriceListEmptyException extends  RuntimeException {

    public PriceListEmptyException(String message) {
        super(message);
    }
}
