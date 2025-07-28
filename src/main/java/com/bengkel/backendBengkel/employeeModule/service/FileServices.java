package com.bengkel.backendBengkel.employeeModule.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bengkel.backendBengkel.base.exception.CostumeResponse;
import com.bengkel.backendBengkel.base.responsePage.ResponseDataEntity;
import com.bengkel.backendBengkel.base.responsePage.WebResponse;
import com.bengkel.backendBengkel.base.untilize.directRepositoryEmployee;
import com.bengkel.backendBengkel.employeeModule.model.DocumentFiles;
import com.bengkel.backendBengkel.employeeModule.model.Employee;
import com.bengkel.backendBengkel.employeeModule.service.implService.ImpFileServiceDocument;
import com.bengkel.backendBengkel.employeeModule.service.implService.InterfaceMethodFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileServices extends ImpFileServiceDocument implements InterfaceMethodFile {

    public FileServices(directRepositoryEmployee dRepositoryEmployee) {
        super(dRepositoryEmployee);
        //TODO Auto-generated constructor stub
    }

    @Transactional
    @Override
    public ResponseEntity<?> deleteFileDocument(Employee employee, String fileName) {
        // TODO Auto-generated method stub
        log.info("Start processing delete document");
        List<DocumentFiles> file = dRepositoryEmployee.fileRepository.findByFileName(fileName);
        if (file.isEmpty()) {
            throw new CostumeResponse(HttpStatus.NOT_FOUND, "File " + fileName + " is not found");
        }
        dRepositoryEmployee.fileRepository.delete(file.get(0));
        return new ResponseEntity<>(WebResponse.builder().message("Delete file " + fileName + " successfully").status(true).StatusCode(200).build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> insertFileDocument(Employee employee, MultipartFile file) {
        // TODO Auto-generated method stub
        try {
            log.info("Starting process Service inserting file");
            checkFileUploadDocument(file.getOriginalFilename());
            log.info("check Service inserting file");
            DocumentFiles fileRequest = ConvertDocumentFiles(employee.getId(), file);
            log.info("Create Document File");
            saveDocumentFile(fileRequest);
            return new ResponseEntity<>(WebResponse.builder().data(ResponseDataEntity.builder().createBy(employee.getUsername()).typeFile(fileRequest.getTypeFile()).fileName(fileRequest.getFileName()).build()).StatusCode(HttpStatus.OK.value()).status(true).message("Success save file").build(), HttpStatus.OK);
        } catch (Exception e) {
            log.debug("Error inserting file " + e.getMessage());
            throw new CostumeResponse(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> searchFileDocument(Map<String, String> searchFile) {
        // TODO Auto-generated method stub
        String filename = searchFile.get("fileName");
        log.info("Service search file by name " + filename);
        List<DocumentFiles> files = dRepositoryEmployee.fileRepository.findByFileName(filename);
        List<ResponseDataEntity> dataResponse = files.stream().map(doc -> GetResponseFile(doc)).collect(Collectors.toList());
        return new ResponseEntity<>(WebResponse.builder().status(true).message("Success get Document files").StatusCode(HttpStatus.OK.value()).data(dataResponse).build(), HttpStatus.OK);
    }

}
