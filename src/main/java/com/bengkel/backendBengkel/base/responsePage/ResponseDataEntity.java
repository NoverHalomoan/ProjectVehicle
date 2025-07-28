package com.bengkel.backendBengkel.base.responsePage;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDataEntity {

    private String id;

    private String name;

    private String salary;

    private String token;

    private String department;

    //file 
    private String fileName;

    private String originalFileName;

    private String Base64File;

    private String typeFile;

    private String createBy;

}
