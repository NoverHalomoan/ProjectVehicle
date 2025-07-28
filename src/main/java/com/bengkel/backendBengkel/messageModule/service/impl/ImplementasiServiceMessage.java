package com.bengkel.backendBengkel.messageModule.service.impl;

import org.springframework.http.ResponseEntity;

import com.bengkel.backendBengkel.base.DTO.ConversationDTO;
import com.bengkel.backendBengkel.base.DTO.DTOUserMessage;
import com.bengkel.backendBengkel.employeeModule.model.Employee;

public interface ImplementasiServiceMessage {

    ConversationDTO getConversationMessage(Employee employee);

    ResponseEntity<?> implementInsertMessage(Employee employee, DTOUserMessage request);

    Object implementDeleteMessage(Employee employee);
}
