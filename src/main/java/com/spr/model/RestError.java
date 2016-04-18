package com.spr.model;

import org.springframework.core.ErrorCoded;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestError implements ErrorCoded {

    private String code;
    private String message;

    public RestError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    @JsonProperty("code")
    public String getErrorCode() {
        return code;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("resource")
    public String getResource() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().build().toString();
    }
}
