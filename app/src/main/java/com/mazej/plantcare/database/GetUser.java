package com.mazej.plantcare.database;

public class GetUser {
    private String access_token;
    private String email;
    private String username;
    private Boolean notifications;
    private String user_role;

    public GetUser(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getNotifications() {
        return notifications;
    }

    public String getUser_role() {
        return user_role;
    }

}
