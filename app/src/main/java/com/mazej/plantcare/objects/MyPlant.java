package com.mazej.plantcare.objects;

public class MyPlant {

    private String id;
    private String image;
    private String name;
    private String text;

    public MyPlant(String id, String image, String name, String text) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
