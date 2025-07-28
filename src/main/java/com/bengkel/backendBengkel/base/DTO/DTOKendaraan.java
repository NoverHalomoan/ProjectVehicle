package com.bengkel.backendBengkel.base.DTO;

public record DTOKendaraan(
        String noRegistrasi,
        String namaPemilik,
        String alamat,
        String merekKendaraan,
        Integer tahunPembuatan,
        Integer kapasitasSilinder,
        String warnaKendaraan,
        String BahanBakar
        ) {

}
