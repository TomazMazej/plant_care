package com.mazej.plantcare.database;

public class GetPlant {

    private String access_token;
    private String name;
    private int days_water;
    private String info;
    private String image_path;

    public GetPlant(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getImage_path() {
        return image_path;
    }

    public int getDays_water() {
        return days_water;
    }
}
