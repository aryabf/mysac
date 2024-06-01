package com.finalproject.mysac.data.model;

import java.util.ArrayList;

public class Resep {

    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getInstruksi() {
        return instruksi;
    }

    public void setInstruksi(String instruksi) {
        this.instruksi = instruksi;
    }

    public String getLinkGambar() {
        return linkGambar;
    }

    public void setLinkGambar(String linkGambar) {
        this.linkGambar = linkGambar;
    }

    public ArrayList<String> getBahanBahan() {
        return bahanBahan;
    }

    public void setBahanBahan(ArrayList<String> bahanBahan) {
        this.bahanBahan = bahanBahan;
    }

    public String getPembuat() {
        return pembuat;
    }

    public void setPembuat(String pembuat) {
        this.pembuat = pembuat;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public ArrayList<String> getUkuranUkuran() {
        return ukuranUkuran;
    }

    public void setUkuranUkuran(ArrayList<String> ukuranUkuran) {
        this.ukuranUkuran = ukuranUkuran;
    }

    public byte[] getGambar() {
        return gambar;
    }

    public void setGambar(byte[] gambar) {
        this.gambar = gambar;
    }

    String nama;
    String kategori;
    String instruksi;
    String linkGambar;
    ArrayList<String> bahanBahan;
    String pembuat;
    String area;
    ArrayList<String> ukuranUkuran;
    byte[] gambar;

    public Resep(String id, String nama, String kategori, String instruksi, String linkGambar, String pembuat, String area, byte[] gambar) {
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.instruksi = instruksi;
        this.linkGambar = linkGambar;
        this.pembuat = pembuat;
        this.gambar = gambar;
        this.area = area;
    }

}
