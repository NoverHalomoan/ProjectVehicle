package com.bengkel.backendBengkel.base.untilize;

import org.springframework.stereotype.Component;

import com.bengkel.backendBengkel.employeeModule.service.EmployeeService;
import com.bengkel.backendBengkel.employeeModule.service.FileServices;

@Component
public class directComponentEmployee {

    public EmployeeService employeeService;

    public FileServices fileServices;

    public directComponentEmployee(EmployeeService employeeService, FileServices fileServices) {
        this.employeeService = employeeService;
        this.fileServices = fileServices;
    }

}
