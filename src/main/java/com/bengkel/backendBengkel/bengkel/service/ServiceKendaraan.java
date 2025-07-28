package com.bengkel.backendBengkel.bengkel.service;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bengkel.backendBengkel.base.DTO.DTOKendaraan;
import com.bengkel.backendBengkel.base.DTO.ResponseDataKendaraan;
import com.bengkel.backendBengkel.base.exception.CostumeResponse;
import com.bengkel.backendBengkel.base.responsePage.PagingResponse;
import com.bengkel.backendBengkel.base.responsePage.WebResponse;
import com.bengkel.backendBengkel.bengkel.model.Kendaraan;
import com.bengkel.backendBengkel.bengkel.repository.KendaraanRepository;
import com.bengkel.backendBengkel.bengkel.service.impl.ImplServiceKendaraan;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ServiceKendaraan extends ImplServiceKendaraan {

    private Logger log = LoggerFactory.getLogger(ServiceKendaraan.class);

    public ServiceKendaraan(KendaraanRepository kendaraanRepository, ObjectMapper objectMapper) {
        super(kendaraanRepository, objectMapper);
        //TODO Auto-generated constructor stub
    }

    public String SaveDataKendaraanBaru(DTOKendaraan dto) {
        log.info("Save Data ");
        Kendaraan kendaraan = new Kendaraan(dto.noRegistrasi(), dto.BahanBakar(), dto.alamat(), dto.kapasitasSilinder(), dto.merekKendaraan(), dto.namaPemilik(), dto.tahunPembuatan(), dto.warnaKendaraan());
        String ResponseProcess = ProcessNewDataKendaraan(kendaraan);
        return ResponseProcess;
    }

    public String UpdateDataKendaraan(DTOKendaraan dto) {
        String ResponseUpdate = ImplUpdateDataKendaraan(dto);
        return ResponseUpdate;
    }

    public String DeleteDataKendaraan(String noRegistrasi) {
        if (Objects.isNull(noRegistrasi) || noRegistrasi.isEmpty()) {
            throw new CostumeResponse(HttpStatus.BAD_REQUEST, "Data No Registrasi Not Found");
        }
        String ResponseDelete = ImplDeleteDataKendaraan(noRegistrasi);
        return ResponseDelete;
    }

    public WebResponse<List<ResponseDataKendaraan>> GetSearchDataKendaraan(String noRegistrasi, String namaPemilik, Integer pageInt, Integer size) {
        Page<ResponseDataKendaraan> response = searchRegisterNoAndNamePemilik(noRegistrasi, namaPemilik, pageInt, size);
        return new WebResponse<>(response.getContent(), true, new PagingResponse(response.getNumber(), response.getTotalPages(), response.getSize()), HttpStatus.OK.value(), "Success get data");
    }

    public List<ResponseDataKendaraan> GetAllDataKendaraan() {
        List<ResponseDataKendaraan> response = ImplgetAllDataKendaraan();
        //log.info("Data " + response.get(0).getNamaPemilik());
        return response;
    }

}
