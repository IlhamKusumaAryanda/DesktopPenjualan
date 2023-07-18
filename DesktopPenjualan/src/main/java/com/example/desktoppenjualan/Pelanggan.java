package com.example.desktoppenjualan;


public class Pelanggan {
    private int idpelanggan;
    private String nama;
    private String alamat;

    public Pelanggan (int idpelanggan, String nama, String alamat) {
        this.idpelanggan = idpelanggan;
        this.nama = nama;
        this.alamat = alamat;
    }


    public int getIdpelanggan() {
        return idpelanggan;
    }

    public void setIdpelanggan(int idpelanggan) {
        this.idpelanggan = idpelanggan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
