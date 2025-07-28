package com.bengkel.backendBengkel.base.exception;

import org.springframework.http.HttpStatus;

public class CostumeResponse extends RuntimeException {

    private HttpStatus httpStatus;

    public CostumeResponse(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
