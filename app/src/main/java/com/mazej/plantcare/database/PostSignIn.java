package com.mazej.plantcare.database;

public class PostSignIn {

    private String email;
    private String password;
    private String access_token;

    public PostSignIn(String email, String password, String access_token) {
        this.email = email;
        this.password = password;
        this.access_token = access_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return access_token;
    }

    public void setToken(String access_token) {
        this.access_token = access_token;
    }
}
