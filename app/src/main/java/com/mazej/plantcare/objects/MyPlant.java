package com.mazej.plantcare.objects;

import java.util.Date;

public class MyPlant {

    private String id;
    private String image;
    private String name;
    private int water;
    private String info;
    private String care;

    private int apiPlantId;
    private Date last_water_date;
    private int remaining_water_days;

    public MyPlant(String id, String image, String name, int water, String info, String care) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.water = water;
        this.info = info;
        this.care = care;
    }

    //constructor used in myPlantsFragment
    public MyPlant(String id, String image, String name, int water, String info, String care, int apiPlantId, Date last_water_date, int remaining_water_days) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.water = water;
        this.info = info;
        this.care = care;
        this.apiPlantId = apiPlantId;
        this.last_water_date = last_water_date;
        this.remaining_water_days = remaining_water_days;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCare() {
        return care;
    }

    public void setCare(String care) {
        this.care = care;
    }

    public int getApiPlantId() {
        return apiPlantId;
    }

    public void setApiPlantId(int apiPlantId) {
        this.apiPlantId = apiPlantId;
    }

    public Date getLast_water_date() {
        return last_water_date;
    }

    public void setLast_water_date(Date last_water_date) {
        this.last_water_date = last_water_date;
    }

    public int getRemaining_water_days() {
        return remaining_water_days;
    }

    public void setRemaining_water_days(int remaining_water_days) {
        this.remaining_water_days = remaining_water_days;
    }
}