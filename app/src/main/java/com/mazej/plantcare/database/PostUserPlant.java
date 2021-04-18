package com.mazej.plantcare.database;

import com.mazej.plantcare.objects.MyPlant;

public class PostUserPlant {

    private String access_token;
    private int plant_id;
    private int id;

    public PostUserPlant(String access_token, int plant_id) {
        this.access_token = access_token;
        this.plant_id = plant_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public int getId() {
        return id;
    }
}
