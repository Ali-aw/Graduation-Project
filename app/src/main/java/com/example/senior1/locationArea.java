package com.example.senior1;

public class locationArea {

    String locationArea_id;
    double Longitude;
    double Latitude;
    String Location_Name_En;//chaqraa
    String District;// bentjbeil
    String Governorate;//nabatie
    boolean isSource;
    boolean isDestination;

    public locationArea() {
        this.locationArea_id = null;
        Longitude = 0;
        Latitude = 0;
        Location_Name_En = null;
        District = null;
        Governorate = null;
        this.isDestination=false;
        this.isSource=false;
    }

    public locationArea(String location_Name_En, String district, String governorate) {
        Location_Name_En = location_Name_En;
        District = district;
        Governorate = governorate;
    }

    public boolean isSource() {
        return isSource;
    }

    public void setSource(boolean source) {
        isSource = source;
    }

    public boolean isDestination() {
        return isDestination;
    }

    public void setDestination(boolean destination) {
        isDestination = destination;
    }

    public String getLocationArea_id() {
        return locationArea_id;
    }

    public void setLocationArea_id(String locationArea_id) {
        this.locationArea_id = locationArea_id;
    }



    public String getLocation_Name_En() {
        return Location_Name_En;
    }

    public void setLocation_Name_En(String location_Name_En) {
        Location_Name_En = location_Name_En;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getGovernorate() {
        return Governorate;
    }

    public void setGovernorate(String governorate) {
        Governorate = governorate;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }
}
