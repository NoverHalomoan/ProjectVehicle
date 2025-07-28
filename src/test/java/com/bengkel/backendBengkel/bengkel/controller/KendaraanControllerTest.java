package com.bengkel.backendBengkel.bengkel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.bengkel.backendBengkel.base.DTO.DTOKendaraan;
import com.bengkel.backendBengkel.base.DTO.DTOLogin;
import com.bengkel.backendBengkel.base.DTO.ResponseDataKendaraan;
import com.bengkel.backendBengkel.base.responsePage.ResponseDataEntity;
import com.bengkel.backendBengkel.base.responsePage.WebResponse;
import com.bengkel.backendBengkel.base.untilize.directComponentEmployee;
import com.bengkel.backendBengkel.bengkel.model.Kendaraan;
import com.bengkel.backendBengkel.bengkel.repository.KendaraanRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class KendaraanControllerTest {

    private Logger log = LoggerFactory.getLogger(KendaraanControllerTest.class);
    String NoTestKendaraan = "";
    String token = "";
    @Autowired
    private MockMvc mocMvc;

    @Autowired
    private ObjectMapper objectmapper;

    @Autowired
    private directComponentEmployee dComponentEmployee;

    @Autowired
    private KendaraanRepository kendaraanRepository;

    @BeforeEach
    void setUp() throws Exception {
        Kendaraan kend = new Kendaraan("TESTING10034", "Test", "Test", 100, "Test", "Merah", 1990, "Test");
        Kendaraan kendSave = kendaraanRepository.save(kend);
        NoTestKendaraan = kendSave.getNoRegistrasi();
        //CristinLiverseege@gmail.com adn pass :CristinLiverseege
        //DonallAssad and email DonallAssad@gmail.com
        DTOLogin login = new DTOLogin("DonallAssadJaguar@gmail.com", "DonallAssad");
        ResponseEntity<?> responseLogin = dComponentEmployee.employeeService.loginEmployeService(login);
        String jsonLogin = objectmapper.writeValueAsString(responseLogin.getBody());
        WebResponse<ResponseDataEntity> LoginData = objectmapper.readValue(jsonLogin, new TypeReference<>() {
        });
        token = LoginData.getData().getToken();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testKendaraanSaveProductSuccess() throws Exception {
        log.info("Token Data " + token);
        DTOKendaraan kendaraan = new DTOKendaraan("TESTING1001", "Test", "Test", "Test", 1990, 100, "Merah", "Solar");
        String DataTesting = objectmapper.writeValueAsString(new DTOKendaraan("TESTING1001", "Test", "Test", "Test", 1990, 100, "Merah", "Solar"));
        log.info("Data process " + DataTesting);
        mocMvc.perform(post("/api/v1/Kendaraan/CreateKendaraan")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(DataTesting))
                .andExpectAll(status().isOk())
                .andDo(results -> {
                    String Response = results.getResponse().getContentAsString();
                    assertEquals("Success Save Data Kendaraan " + kendaraan.noRegistrasi(), Response);
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testKendaraanSaveProductFail() throws Exception {
        String DataTesting = objectmapper.writeValueAsString(new DTOKendaraan("", "Test", "Test", "Test", 1990, 100, "Merah", "Solar"));
        mocMvc.perform(post("/api/v1/Kendaraan/CreateKendaraan")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(DataTesting))
                .andExpectAll(status().isBadRequest())
                .andDo(results -> {
                    String Response = results.getResponse().getContentAsString();
                    assertNotNull(Response);
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testKendaraanUpdateSuccess() throws Exception {
        String DataTesting = objectmapper.writeValueAsString(new DTOKendaraan(NoTestKendaraan, "Testing", "Test", "Test", 1990, 100, "Merah", "Solar"));
        mocMvc.perform(put("/api/v1/Kendaraan/UpdateKendaraan")
                .header("Authorization", "Bearer "
                        + token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(DataTesting)
        )
                .andExpectAll(status().isOk())
                .andDo(results -> {
                    String Response = results.getResponse().getContentAsString();
                    assertEquals("Success Update Data Kendaraan " + NoTestKendaraan, Response);
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testKendaraanDeleteSuccess() throws Exception {
        String noRegistrasi = "TESTING10011";
        Kendaraan kendaraan = new Kendaraan(noRegistrasi, "Test", "Test", 100, "Test", "Merah", 1990, "Test");
        kendaraanRepository.save(kendaraan);

        mocMvc.perform(delete("/api/v1/Kendaraan/{noRegistrasi}", noRegistrasi)
                .header("Authorization", "Bearer "
                        + token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpectAll(status().isOk())
                .andDo(results -> {
                    String Response = results.getResponse().getContentAsString();
                    assertEquals("Success Delete Data Kendaraan " + noRegistrasi, Response);
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testKendaraanGetAllData() throws Exception {

        mocMvc.perform(get("/api/v1/Kendaraan/all").header("Authorization", "Bearer " + token).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(status().isOk())
                .andDo(results -> {
                    List<ResponseDataKendaraan> responseList = objectmapper.readValue(results.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    // for (ResponseDataKendaraan dtKendaraan : responseList) {
                    //     log.info("Data No Registrasi: " + dtKendaraan.getNoRegistrasi());
                    // }
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testSearchDataKendataan() throws Exception {
        Map<String, Object> paramValues = new HashMap<>();
        paramValues.put("noRegistrasi", "Test");
        // paramValues.put("namaPemilik", null);
        paramValues.put("pageInt", 0);
        paramValues.put("size", 10);

        mocMvc.perform(get("/api/v1/Kendaraan")
                .param("noRegistrasi", "Test")
                .param("pageInt", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(status().isOk())
                .andDo(results -> {
                    WebResponse<List<ResponseDataKendaraan>> response = objectmapper.readValue(results.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    for (ResponseDataKendaraan dtKendaraan : response.getData()) {
                        log.info("Data No Registrasi: " + dtKendaraan.getNoRegistrasi());
                        log.info("Data Bahan Bakar: " + dtKendaraan.getBahanBakar());
                    }
                });
    }

}
