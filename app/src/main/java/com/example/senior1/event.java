package com.example.senior1;

public class event {
    String id ;
    String email;
    String FName;
    String LName ;
    String PhoneNb;
    String src_Location_Name;
    String src_District;
    String src_Governorate;
    String dest_Location_Name;
    String dest_District;
    String dest_Governorate;
    String date;

    public event() {
        this.id = null;
        this.email = null;
        this.FName = null;
        this.LName = null;
        PhoneNb = null;
        this.src_Location_Name = null;
        this.src_District = null;
        this.src_Governorate = null;
        this.dest_Location_Name = null;
        this.dest_District = null;
        this.dest_Governorate = null;
        this.date = null;
    }

    public event(String id, String email, String FName, String LName, String phoneNb, String src_Location_Name,
                 String src_District, String src_Governorate, String dest_Location_Name, String dest_District, String dest_Governorate, String date) {
        this.id = id;
        this.email = email;
        this.FName = FName;
        this.LName = LName;
        PhoneNb = phoneNb;
        this.src_Location_Name = src_Location_Name;
        this.src_District = src_District;
        this.src_Governorate = src_Governorate;
        this.dest_Location_Name = dest_Location_Name;
        this.dest_District = dest_District;
        this.dest_Governorate = dest_Governorate;
        this.date = date;
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

    public String getSrc_Location_Name() {
        return src_Location_Name;
    }

    public void setSrc_Location_Name(String src_Location_Name) {
        this.src_Location_Name = src_Location_Name;
    }

    public String getSrc_District() {
        return src_District;
    }

    public void setSrc_District(String src_District) {
        this.src_District = src_District;
    }

    public String getSrc_Governorate() {
        return src_Governorate;
    }

    public void setSrc_Governorate(String src_Governorate) {
        this.src_Governorate = src_Governorate;
    }

    public String getDest_Location_Name() {
        return dest_Location_Name;
    }

    public void setDest_Location_Name(String dest_Location_Name) {
        this.dest_Location_Name = dest_Location_Name;
    }

    public String getDest_District() {
        return dest_District;
    }

    public void setDest_District(String dest_District) {
        this.dest_District = dest_District;
    }

    public String getDest_Governorate() {
        return dest_Governorate;
    }

    public void setDest_Governorate(String dest_Governorate) {
        this.dest_Governorate = dest_Governorate;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
