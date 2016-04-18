package com.spr.exception;

import org.springframework.core.ErrorCoded;

public class ShopNotFound extends RuntimeException implements ErrorCoded {

    private static final long serialVersionUID = 1L;
    public static final String ERROR_CODE = "ShopNotFound";

    public ShopNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getErrorCode() {
        return ERROR_CODE;
    }
}
