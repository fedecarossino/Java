package com.gildedrose.api.exceptions;

public class ItemBadRequestException extends RuntimeException {

    public ItemBadRequestException(){
        super();
    }

    public ItemBadRequestException(String message) {
        super(message);
    }

    public ItemBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
