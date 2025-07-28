package com.bengkel.backendBengkel.base.untilize;

import org.springframework.stereotype.Component;

import com.bengkel.backendBengkel.employeeModule.repository.CustomeEmployeRepositor;
import com.bengkel.backendBengkel.employeeModule.repository.DocumentFileRepository;
import com.bengkel.backendBengkel.employeeModule.repository.EmployeRepository;
import com.bengkel.backendBengkel.messageModule.repository.ConversationRepository;
import com.bengkel.backendBengkel.messageModule.repository.MessageRepository;

@Component
public class directRepositoryEmployee {

    public final EmployeRepository employeRepository;

    public final CustomeEmployeRepositor customeEmployeRepositor;

    public final DocumentFileRepository fileRepository;

    public final ConversationRepository conversationRepository;

    public final MessageRepository messageRepository;

    public directRepositoryEmployee(EmployeRepository employeRepository, CustomeEmployeRepositor customeEmployeRepositor,
            DocumentFileRepository fileRepository,
            ConversationRepository conversationRepository,
            MessageRepository messageRepository) {
        this.employeRepository = employeRepository;
        this.customeEmployeRepositor = customeEmployeRepositor;
        this.fileRepository = fileRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

}
