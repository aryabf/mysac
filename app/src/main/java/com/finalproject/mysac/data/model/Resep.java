package com.finalproject.mysac.data.model;

public class Resep {

    String id;
    String nama;
    String kategori;
    String instruksi;
    String linkGambar;
    String[] bahanBahan;
    String pembuat;
    String[] ukuranUkuran;
    byte[] gambar;

    public Resep(String id, String nama, String kategori, String instruksi, String linkGambar, String pembuat, byte[] gambar) {
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.instruksi = instruksi;
        this.linkGambar = linkGambar;
        this.pembuat = pembuat;
        this.gambar = gambar;
    }

}
