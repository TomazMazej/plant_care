package com.mazej.plantcare.database;

public class GetPlant {

    private String access_token;
    private String name;
    private int days_water;
    private String info;
    private String care;
    private String image_path;
    private int id;

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

    public void setName(String name) {
        this.name = name;
    }

    public int getDays_water() {
        return days_water;
    }

    public void setDays_water(int days_water) {
        this.days_water = days_water;
    }

    public String getInfo() {
        return info;
    }

    public int getId() {return id;}

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCare() {
        return care;
    }

    public void setCare(String care) {
        this.care = care;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
