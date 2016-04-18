package com.spr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.spr.exception.ShopNotFound;
import com.spr.model.RestError;

@ControllerAdvice
public class ShopControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ShopControllerAdvice.class);

    @ExceptionHandler(value = { ShopNotFound.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public HttpEntity<RestError> handleShopNotFoundException(ShopNotFound exception) {
        return new ResponseEntity<RestError>(new RestError(exception.getErrorCode(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
