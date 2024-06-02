package com.finalproject.mysac.ui.home.model;

public class Bahan {
    public String getNamaBahan() {
        return namaBahan;
    }

    public Bahan(String namaBahan, String takaran) {
        this.namaBahan = namaBahan;
        this.takaran = takaran;
    }

    public void setNamaBahan(String namaBahan) {
        this.namaBahan = namaBahan;
    }

    public String getTakaran() {
        return takaran;
    }

    public void setTakaran(String takaran) {
        this.takaran = takaran;
    }

    String namaBahan;
    String takaran;
}
