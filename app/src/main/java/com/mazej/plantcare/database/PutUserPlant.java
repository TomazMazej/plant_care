package com.mazej.plantcare.database;

import com.mazej.plantcare.objects.Plant;

import java.util.Date;

public class PutUserPlant {
    private String access_token;
    private Plant plant;
    private int id;
    private Date last_water_day;
    private int remaining_water_days;

    public PutUserPlant(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Plant getPlant(){
        return plant;
    }

    public int getId() {
        return id;
    }

    public Date getLast_water_day() {
        return last_water_day;
    }

    public int getRemaining_water_days() {
        return remaining_water_days;
    }
}
