package com.example.senior1;

import android.net.Uri;

public class Profile {
    String id ;
    String email;
    String password ;
    String FName;
    String LName ;
    String PhoneNb;
    Uri image;
    String ProfileType;
    String genre;


    public Profile() {
        this.id = null;
        this.email = null;
        this.password = null;
        this.FName = null;
        this.LName = null;
        this.PhoneNb = null;
        this.image = null;
        this.ProfileType=null;
        this.genre=null;
    }


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getProfileType() {
        return ProfileType;
    }

    public void setProfileType(String profileType) {
        ProfileType = profileType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getPhoneNb() {
        return PhoneNb;
    }

    public void setPhoneNb(String phoneNb) {
        PhoneNb = phoneNb;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
