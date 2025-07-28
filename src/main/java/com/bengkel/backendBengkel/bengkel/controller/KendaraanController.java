package com.bengkel.backendBengkel.bengkel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bengkel.backendBengkel.base.DTO.DTOKendaraan;
import com.bengkel.backendBengkel.bengkel.service.ServiceKendaraan;

@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:3030"})
@RestController
@RequestMapping(path = "/api/v1/Kendaraan")
public class KendaraanController {

    private Logger log = LoggerFactory.getLogger(KendaraanController.class);

    @Autowired
    private ServiceKendaraan serviceKendaraan;

    public KendaraanController(ServiceKendaraan serviceKendaraan) {
        this.serviceKendaraan = serviceKendaraan;
    }

    @PostMapping(path = "/CreateKendaraan", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> KendaraanSaveProduct(@RequestBody DTOKendaraan kendaraan) {
        return new ResponseEntity<>(serviceKendaraan.SaveDataKendaraanBaru(kendaraan), HttpStatus.OK);
    }

    @PutMapping(path = "/UpdateKendaraan", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> KendaraanUpdateProduct(@RequestBody DTOKendaraan kendaraan) {
        return new ResponseEntity<>(serviceKendaraan.UpdateDataKendaraan(kendaraan), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{noRegistrasi}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> DeleteDataKendaraanProduct(@PathVariable(name = "noRegistrasi", required = true) String noRegistrasi) {
        return new ResponseEntity<>(serviceKendaraan.DeleteDataKendaraan(noRegistrasi), HttpStatus.OK);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> ListSearchDataKendaraan(@RequestParam(value = "noRegistrasi", required = false) String noRegistrasi, @RequestParam(value = "namaPemilik", required = false) String namaPemilik,
            @RequestParam(value = "pageInt", required = false, defaultValue = "0") Integer pageInt, @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return new ResponseEntity<>(serviceKendaraan.GetSearchDataKendaraan(noRegistrasi, namaPemilik, pageInt, size), HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> ListAllDataKendaraanProduct() {
        return new ResponseEntity<>(serviceKendaraan.GetAllDataKendaraan(), HttpStatus.OK);
    }

}
