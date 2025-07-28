package com.bengkel.backendBengkel.employeeModule.service.implService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bengkel.backendBengkel.base.Services.GeneralFunctionCase;
import com.bengkel.backendBengkel.base.exception.CostumeResponse;
import com.bengkel.backendBengkel.base.responsePage.ResponseDataEntity;
import com.bengkel.backendBengkel.base.untilize.directRepositoryEmployee;
import com.bengkel.backendBengkel.employeeModule.model.DocumentFiles;
import com.bengkel.backendBengkel.employeeModule.model.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ImpFileServiceDocument {

    protected final directRepositoryEmployee dRepositoryEmployee;

    private String getTypeOfFile(String fileName) {
        return fileName.split("[.]")[1].toLowerCase();
    }

    protected ResponseDataEntity GetResponseFile(DocumentFiles fileData) {
        byte[] fileBytes = fileData.getBase64Json().getBytes(StandardCharsets.UTF_8);
        String fileBase64 = Base64.getEncoder().encodeToString(fileBytes);
        Employee EmployeeCreate = dRepositoryEmployee.employeRepository.findById(fileData.getCreateBy()).orElseThrow(() -> new CostumeResponse(HttpStatus.NOT_FOUND, "User created is not found"));

        return ResponseDataEntity.builder().Base64File(fileBase64).fileName(fileData.getFileName()).originalFileName(fileData.getOriginalName()).typeFile(fileData.getTypeFile()).createBy(EmployeeCreate.getUsername())
                .createBy(GeneralFunctionCase.getDateTransform(fileData.getCreateAt())).build();
    }

    private String fileNameSetNames(String fileName) {
        Random rnd = new Random();
        String typeFile = getTypeOfFile(fileName);
        Long nilaiLong = (long) rnd.nextInt();
        nilaiLong = nilaiLong > 0 ? nilaiLong : nilaiLong * -1;
        String fileReport = "File_" + nilaiLong + LocalDateTime.now() + "." + typeFile;
        return fileReport;
    }

    public ImpFileServiceDocument(directRepositoryEmployee dRepositoryEmployee) {
        this.dRepositoryEmployee = dRepositoryEmployee;
    }

    protected void checkFileUploadDocument(String fileName) {
        String typeFile = getTypeOfFile(fileName);
        if (typeFile.equals("mp4") || typeFile.equals("avi") || typeFile.equals("mov") || typeFile.equals("mp3")) {
            throw new CostumeResponse(HttpStatus.BAD_REQUEST, "Type of file is not required");
        }

    }

    protected DocumentFiles ConvertDocumentFiles(String idUser, MultipartFile fileRequest) throws Exception {
        try {
            byte[] fileBytes = fileRequest.getBytes();
            DocumentFiles FileNew = new DocumentFiles();
            FileNew.setCreateBy(idUser);
            FileNew.setMimeType(fileRequest.getContentType());
            FileNew.setSize(Double.valueOf(fileRequest.getSize()));
            FileNew.setBase64Json(Base64.getEncoder().encodeToString(fileBytes));
            FileNew.setFileName(fileNameSetNames(fileRequest.getOriginalFilename()));
            FileNew.setOriginalName(fileRequest.getOriginalFilename());
            FileNew.setTypeFile(getTypeOfFile(fileRequest.getOriginalFilename()));
            return FileNew;
        } catch (IOException e) {
            log.debug("Error file " + e.getMessage());
            throw e;
        }
    }

    protected DocumentFiles saveDocumentFile(DocumentFiles fileRequest) {
        return dRepositoryEmployee.fileRepository.save(fileRequest);
    }

}
