package com.gildedrose.api.exceptions;

public class ItemInternalErrorException extends RuntimeException {

    public ItemInternalErrorException() {
        super();
    }

    public ItemInternalErrorException(String message) {
        super(message);
    }

    public ItemInternalErrorException(String message, Throwable cause) {
        super(message, cause);
    }

}
