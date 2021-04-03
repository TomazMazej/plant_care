package com.mazej.plantcare.objects;

public class MyPlant {

    private String id;
    private String image;
    private String name;
    private int water;
    private String info;
    private String care;

    public MyPlant(String id, String image, String name, int water, String info, String care) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.water = water;
        this.info = info;
        this.care = care;
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

    public String getName() {
        return name;
    }

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
}