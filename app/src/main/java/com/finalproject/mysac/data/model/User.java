package com.finalproject.mysac.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    String username;
    String name;
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
