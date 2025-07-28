package com.bengkel.backendBengkel.bengkel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Kendaraan {

    @Id
    private String noRegistrasi;

    @Column(nullable = false)
    private String namaPemilik;

    private String alamat;

    private String merekKendaraan;

    private Integer tahunPembuatan;

    private Integer kapasitasSilinder;

    private String warnaKendaraan;

    private String BahanBakar;

    public Kendaraan() {

    }

    public Kendaraan(String noRegistrasi, String BahanBakar, String alamat, Integer kapasitasSilinder, String merekKendaraan, String namaPemilik, Integer tahunPembuatan, String warnaKendaraan) {
        this.BahanBakar = BahanBakar;
        this.alamat = alamat;
        this.kapasitasSilinder = kapasitasSilinder;
        this.merekKendaraan = merekKendaraan;
        this.namaPemilik = namaPemilik;
        this.noRegistrasi = noRegistrasi;
        this.tahunPembuatan = tahunPembuatan;
        this.warnaKendaraan = warnaKendaraan;
    }

    public String getNoRegistrasi() {
        return noRegistrasi;
    }

    public void setNoRegistrasi(String noRegistrasi) {
        this.noRegistrasi = noRegistrasi;
    }

    public String getnamaPemilik() {
        return namaPemilik;
    }

    public void setnamaPemilik(String namaPemilik) {
        this.namaPemilik = namaPemilik;
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
