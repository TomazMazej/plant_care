package com.mazej.plantcare.objects;

public class Plant {

    private int id;
    private String image_path;
    private String name;
    private int days_water;
    private String info;
    private String care;

    public Plant(int id, String name, String info, String care, String image_path, int days_water) {
        this.id = id;
        this.image_path = image_path;
        this.name = name;
        this.days_water = days_water;
        this.info = info;
        this.care = care;
    }

    public int getId() {
        return id;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getName() {
        return name;
    }

    public int getDays_water() {
        return days_water;
    }

    public String getInfo() {
        return info;
    }

    public String getCare() {
        return care;
    }
}
