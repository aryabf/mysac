package com.finalproject.mysac.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Resep implements Parcelable {

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
    byte[] gambarPembuat;
    String namaPembuat;

    public byte[] getGambarPembuat() {
        return gambarPembuat;
    }

    public void setGambarPembuat(byte[] gambarPembuat) {
        this.gambarPembuat = gambarPembuat;
    }

    public String getNamaPembuat() {
        return namaPembuat;
    }

    public void setNamaPembuat(String namaPembuat) {
        this.namaPembuat = namaPembuat;
    }

    public Resep(String id, String nama, String kategori, String instruksi, String linkGambar, String pembuat, String area, byte[] gambar, byte[] gambarPembuat, String namaPembuat) {
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.instruksi = instruksi;
        this.linkGambar = linkGambar;
        this.pembuat = pembuat;
        this.gambar = gambar;
        this.area = area;
        this.gambarPembuat = gambarPembuat;
        this.namaPembuat = namaPembuat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.kategori);
        dest.writeString(this.instruksi);
        dest.writeString(this.linkGambar);
        dest.writeStringList(this.bahanBahan);
        dest.writeString(this.pembuat);
        dest.writeString(this.area);
        dest.writeStringList(this.ukuranUkuran);
        dest.writeByteArray(this.gambar);
        dest.writeByteArray(this.gambarPembuat);
        dest.writeString(this.namaPembuat);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.nama = source.readString();
        this.kategori = source.readString();
        this.instruksi = source.readString();
        this.linkGambar = source.readString();
        this.bahanBahan = source.createStringArrayList();
        this.pembuat = source.readString();
        this.area = source.readString();
        this.ukuranUkuran = source.createStringArrayList();
        this.gambar = source.createByteArray();
        this.gambarPembuat = source.createByteArray();
        this.namaPembuat = source.readString();
    }

    protected Resep(Parcel in) {
        this.id = in.readString();
        this.nama = in.readString();
        this.kategori = in.readString();
        this.instruksi = in.readString();
        this.linkGambar = in.readString();
        this.bahanBahan = in.createStringArrayList();
        this.pembuat = in.readString();
        this.area = in.readString();
        this.ukuranUkuran = in.createStringArrayList();
        this.gambar = in.createByteArray();
        this.gambarPembuat = in.createByteArray();
        this.namaPembuat = in.readString();
    }

    public static final Parcelable.Creator<Resep> CREATOR = new Parcelable.Creator<Resep>() {
        @Override
        public Resep createFromParcel(Parcel source) {
            return new Resep(source);
        }

        @Override
        public Resep[] newArray(int size) {
            return new Resep[size];
        }
    };
}
