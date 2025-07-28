package com.bengkel.backendBengkel.messageModule.controller;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bengkel.backendBengkel.base.DTO.DTOUserMessage;
import com.bengkel.backendBengkel.base.exception.CostumeResponse;
import com.bengkel.backendBengkel.employeeModule.model.Employee;
import com.bengkel.backendBengkel.messageModule.service.ServiceMessage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v2/message")
public class MessageController {

    private final ServiceMessage serviceMessage;

    @PostMapping(path = "/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertMessage(Employee employee, @RequestBody DTOUserMessage request) {
        
        log.info("Start processing controller of Message : " + request);
        if (Objects.isNull(request) || Objects.isNull(request.getSender())) {
            throw new CostumeResponse(HttpStatus.BAD_REQUEST, "Data request is invalid");
        }
        return serviceMessage.implementInsertMessage(employee, request);
    }

}
