package com.bengkel.backendBengkel.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bengkel.backendBengkel.base.responsePage.WebResponse;

import jakarta.mail.MessagingException;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(CostumeResponse.class)
    public ResponseEntity<?> HandlecostumeRepository(CostumeResponse exp) {
        WebResponse response = new WebResponse();
        response.setStatusCode(exp.getHttpStatus().value());
        response.setData(null);
        response.setMessage(exp.getMessage());
        return new ResponseEntity<>(response, exp.getHttpStatus());
    }

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<?> HandleException(Exception exp) {
    //     WebResponse response = new WebResponse();
    //     response.setStatusCode(HttpStatus.CONFLICT.value());
    //     response.setData(null);
    //     response.setMessage(exp.getMessage());
    //     System.out.println("Errorr " + exp.getMessage());
    //     return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    // }
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<?> handleMessagingException(MessagingException exp) {
        WebResponse response = new WebResponse();
        response.setStatusCode(HttpStatus.EXPECTATION_FAILED.value());
        response.setData(null);
        response.setMessage(exp.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
