package com.bengkel.backendBengkel.base.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDataKendaraan {

    private Integer no;

    private String noRegistrasi;

    private String namaPemilik;

    private String alamat;

    private String merekKendaraan;

    private Integer tahunPembuatan;

    private Integer kapasitasSilinder;

    private String warnaKendaraan;

    private String BahanBakar;

    public ResponseDataKendaraan() {

    }

    public ResponseDataKendaraan(Integer no, String BahanBakar, String alamat, Integer kapasitasSilinder, String merekKendaraan, String namaPemilik, String noRegistrasi, Integer tahunPembuatan, String warnaKendaraan) {
        this.no = no;
        this.BahanBakar = BahanBakar;
        this.alamat = alamat;
        this.kapasitasSilinder = kapasitasSilinder;
        this.merekKendaraan = merekKendaraan;
        this.namaPemilik = namaPemilik;
        this.noRegistrasi = noRegistrasi;
        this.tahunPembuatan = tahunPembuatan;
        this.warnaKendaraan = warnaKendaraan;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public void setNamaPemilik(String namaPemilik) {
        this.namaPemilik = namaPemilik;
    }

    public String getNoRegistrasi() {
        return noRegistrasi;
    }

    public void setNoRegistrasi(String noRegistrasi) {
        this.noRegistrasi = noRegistrasi;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getMerekKendaraan() {
        return merekKendaraan;
    }

    public void setMerekKendaraan(String merekKendaraan) {
        this.merekKendaraan = merekKendaraan;
    }

    public Integer getTahunPembuatan() {
        return tahunPembuatan;
    }

    public void setTahunPembuatan(Integer tahunPembuatan) {
        this.tahunPembuatan = tahunPembuatan;
    }

    public Integer getKapasitasSilinder() {
        return kapasitasSilinder;
    }

    public void setKapasitasSilinder(Integer kapasitasSilinder) {
        this.kapasitasSilinder = kapasitasSilinder;
    }

    public String getWarnaKendaraan() {
        return warnaKendaraan;
    }

    public void setWarnaKendaraan(String warnaKendaraan) {
        this.warnaKendaraan = warnaKendaraan;
    }

    public String getBahanBakar() {
        return BahanBakar;
    }

    public void setBahanBakar(String bahanBakar) {
        BahanBakar = bahanBakar;
    }
}
