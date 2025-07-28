package com.bengkel.backendBengkel.bengkel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bengkel.backendBengkel.base.DTO.DTOKendaraan;
import com.bengkel.backendBengkel.base.DTO.ResponseDataKendaraan;
import com.bengkel.backendBengkel.base.exception.CostumeResponse;
import com.bengkel.backendBengkel.bengkel.model.Kendaraan;
import com.bengkel.backendBengkel.bengkel.repository.KendaraanRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.criteria.Predicate;

@Service
public class ImplServiceKendaraan {

    private Logger log = LoggerFactory.getLogger(ImplServiceKendaraan.class);

    private final ObjectMapper objectMapper;

    private final KendaraanRepository kendaraanRepository;

    public ImplServiceKendaraan(
            KendaraanRepository kendaraanRepository, ObjectMapper objectMapper) {
        this.kendaraanRepository = kendaraanRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    private Kendaraan cekDataKendaraan(String noRegistrasi) {
        return kendaraanRepository.findById(noRegistrasi).orElseThrow(() -> new CostumeResponse(HttpStatus.ALREADY_REPORTED, "Data Already Reported"));
    }

    @Transactional
    private Kendaraan cekForSaveDataKendaraan(String noRegistrasi) {
        return kendaraanRepository.findById(noRegistrasi).orElseThrow(() -> new CostumeResponse(HttpStatus.NOT_FOUND, "Data not found"));
        //return Objects.isNull(kendaraanData) ? null : kendaraanData;
    }

    @Transactional
    private void SaveDataKendaraan(Kendaraan entity) {
        try {
            kendaraanRepository.save(entity);
        } catch (Exception e) {
            throw new CostumeResponse(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Transactional
    private void DeleteRepDataKendaraan(Kendaraan entity) {
        try {
            kendaraanRepository.delete(entity);
        } catch (Exception e) {
            throw new CostumeResponse(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    private void MandatoryCekDataKendaraan(Kendaraan entity) {
        //List<String> ListBahanKedaraan = new ArrayList<>(Arrays.asList("Merah", "Hitam", "Biru", "Abu-Abu"));
        if (Objects.isNull(entity.getNoRegistrasi()) || entity.getNoRegistrasi().equals("")) {
            throw new CostumeResponse(HttpStatus.BAD_REQUEST, "No Registrasi Kendaraan Is Null");
        }
        if (Objects.isNull(entity.getnamaPemilik()) || entity.getnamaPemilik().equals("")) {
            throw new CostumeResponse(HttpStatus.BAD_REQUEST, "Nama Pemilik Kendaraan Is Null");
        }
        if (Objects.isNull(entity.getBahanBakar()) || entity.getBahanBakar().equals("")) {
            throw new CostumeResponse(HttpStatus.BAD_REQUEST, "Bahan Kendaraan Is Null");
        }
    }

    public ResponseDataKendaraan tResponseDataKendaraan(Kendaraan kendaraan) {
        try {
            String dataString = objectMapper.writeValueAsString(kendaraan);
            ResponseDataKendaraan response = objectMapper.readValue(dataString, ResponseDataKendaraan.class);
            return response;
        } catch (Exception e) {
            log.info("Error Set Data Mapping");
        }
        return null;
    }

    protected String ProcessNewDataKendaraan(Kendaraan entity) {
        MandatoryCekDataKendaraan(entity);
        if (kendaraanRepository.findById(entity.getNoRegistrasi()).isPresent()) {
            return "Data Kendaraan " + entity.getNoRegistrasi() + " Had Been Saved";
        } else {
            SaveDataKendaraan(entity);
            Kendaraan kendaraan = cekForSaveDataKendaraan(entity.getNoRegistrasi());
            return "Success Save Data Kendaraan " + kendaraan.getNoRegistrasi();
        }

    }

    protected String ImplUpdateDataKendaraan(DTOKendaraan dto) {
        Kendaraan kendaraan = cekForSaveDataKendaraan(dto.noRegistrasi());
        if (!Objects.isNull(dto.namaPemilik()) && !dto.namaPemilik().equals("")) {
            kendaraan.setnamaPemilik(dto.namaPemilik());
        }
        if (!Objects.isNull(dto.alamat()) && !dto.alamat().equals("")) {
            kendaraan.setAlamat(dto.alamat());
        }
        if (!Objects.isNull(dto.merekKendaraan()) && !dto.merekKendaraan().equals("")) {
            kendaraan.setMerekKendaraan(dto.merekKendaraan());
        }
        if (!Objects.isNull(dto.tahunPembuatan()) && !dto.tahunPembuatan().equals("")) {
            kendaraan.setTahunPembuatan(dto.tahunPembuatan());
        }
        if (!Objects.isNull(dto.kapasitasSilinder()) && !dto.kapasitasSilinder().equals("")) {
            kendaraan.setKapasitasSilinder(dto.kapasitasSilinder());
        }
        if (!Objects.isNull(dto.warnaKendaraan()) && !dto.warnaKendaraan().equals("")) {
            kendaraan.setWarnaKendaraan(dto.warnaKendaraan());
        }
        if (!Objects.isNull(dto.BahanBakar()) && !dto.BahanBakar().equals("")) {
            kendaraan.setBahanBakar(dto.BahanBakar());
        }
        SaveDataKendaraan(kendaraan);
        return "Success Update Data Kendaraan " + kendaraan.getNoRegistrasi();
    }

    protected String ImplDeleteDataKendaraan(String noRegistrasi) {
        Kendaraan kendaraan = cekForSaveDataKendaraan(noRegistrasi);
        if (kendaraan == null) {
            throw new CostumeResponse(HttpStatus.NOT_FOUND, "Data Kendaraan " + noRegistrasi + " not Found");
        }
        DeleteRepDataKendaraan(kendaraan);
        return "Success Delete Data Kendaraan " + noRegistrasi;
    }

    @Transactional(readOnly = true)
    protected Page<ResponseDataKendaraan> searchRegisterNoAndNamePemilik(String noRegistrasi, String namaPemilik, int pageInt, int size) {
        Specification<Kendaraan> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(noRegistrasi)) {
                predicates.add(builder.like(root.get("noRegistrasi"), "%" + noRegistrasi + "%"));
            }
            if (Objects.nonNull(namaPemilik)) {
                predicates.add(builder.like(root.get("namaPemilik"), "%" + namaPemilik + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        AtomicInteger counter = new AtomicInteger();
        Pageable pageable = PageRequest.of(pageInt, size);
        Page<Kendaraan> kendaraanAll = kendaraanRepository.findAll(specification, pageable);
        List<ResponseDataKendaraan> kendaraanResponse = kendaraanAll.stream().map(ken -> {
            ResponseDataKendaraan response = new ResponseDataKendaraan();
            response.setNo(counter.getAndIncrement());
            response.setAlamat(ken.getAlamat());
            response.setNoRegistrasi(ken.getNoRegistrasi());
            response.setWarnaKendaraan(ken.getWarnaKendaraan());
            response.setBahanBakar(ken.getBahanBakar());
            response.setKapasitasSilinder(ken.getKapasitasSilinder());
            response.setTahunPembuatan(ken.getTahunPembuatan());
            response.setMerekKendaraan(ken.getMerekKendaraan());
            response.setNamaPemilik(ken.getnamaPemilik());
            return response;
        }).collect(Collectors.toList());
        return new PageImpl<>(kendaraanResponse, pageable, kendaraanAll.getTotalElements());
    }

    protected List<ResponseDataKendaraan> ImplgetAllDataKendaraan() {
        AtomicInteger counter = new AtomicInteger();
        List<Kendaraan> KendaraanAll = kendaraanRepository.findAll();
        List<ResponseDataKendaraan> kendaraanResponse = KendaraanAll.stream().map(ken -> {
            ResponseDataKendaraan response = new ResponseDataKendaraan();
            response.setNo(counter.getAndIncrement());
            response.setAlamat(ken.getAlamat());
            response.setNoRegistrasi(ken.getNoRegistrasi());
            response.setWarnaKendaraan(ken.getWarnaKendaraan());
            response.setBahanBakar(ken.getBahanBakar());
            response.setKapasitasSilinder(ken.getKapasitasSilinder());
            response.setTahunPembuatan(ken.getTahunPembuatan());
            response.setMerekKendaraan(ken.getMerekKendaraan());
            response.setNamaPemilik(ken.getnamaPemilik());
            return response;
        }).collect(Collectors.toList());
        return kendaraanResponse;
    }

}
