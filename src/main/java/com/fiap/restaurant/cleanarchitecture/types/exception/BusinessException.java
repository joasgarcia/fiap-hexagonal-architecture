package com.fiap.restaurant.cleanarchitecture.types.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
