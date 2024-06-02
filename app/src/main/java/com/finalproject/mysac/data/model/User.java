package com.finalproject.mysac.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    String username;
    String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getJumlahResep() {
        return jumlahResep;
    }

    public void setJumlahResep(int jumlahResep) {
        this.jumlahResep = jumlahResep;
    }

    public String getLinkFb() {
        return linkFb;
    }

    public void setLinkFb(String linkFb) {
        this.linkFb = linkFb;
    }

    public String getLinkIg() {
        return linkIg;
    }

    public void setLinkIg(String linkIg) {
        this.linkIg = linkIg;
    }

    public String getLinkYt() {
        return linkYt;
    }

    public void setLinkYt(String linkYt) {
        this.linkYt = linkYt;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    String password;
    String bio;
    int jumlahResep;
    String linkFb;
    String linkIg;
    String linkYt;
    byte[] photo;

    public User(String username, String name, String password, String bio, int jumlahResep, String linkFb, String linkIg, String linkYt, byte[] profile) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.bio = bio;
        this.jumlahResep = jumlahResep;
        this.linkFb = linkFb;
        this.linkIg = linkIg;
        this.linkYt = linkYt;
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.name);
        dest.writeString(this.password);
        dest.writeString(this.bio);
        dest.writeInt(this.jumlahResep);
        dest.writeString(this.linkFb);
        dest.writeString(this.linkIg);
        dest.writeString(this.linkYt);
    }

    public void readFromParcel(Parcel source) {
        this.username = source.readString();
        this.name = source.readString();
        this.password = source.readString();
        this.bio = source.readString();
        this.jumlahResep = source.readInt();
        this.linkFb = source.readString();
        this.linkIg = source.readString();
        this.linkYt = source.readString();
    }

    protected User(Parcel in) {
        this.username = in.readString();
        this.name = in.readString();
        this.password = in.readString();
        this.bio = in.readString();
        this.jumlahResep = in.readInt();
        this.linkFb = in.readString();
        this.linkIg = in.readString();
        this.linkYt = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
