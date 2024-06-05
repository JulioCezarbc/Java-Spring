package com.julio.CandyShop.service.exceptions;

public class EntityExistsException extends RuntimeException{

    public EntityExistsException(String msg) {
        super(msg);
    }
}
