package org.hse.bonusokapplication.Models;

public class DeviceModel {

    private String access_token;
    private String username;
    private int user_id;

    public DeviceModel (String token, String phone, int id)
    {
        this.access_token = token;
        this.username = phone;
        this.user_id = id;
    }

    public DeviceModel() {

    }

    //Getters
    public String getToken() { return access_token; }

    public String getPhone() { return username; }

    public int getUserId() { return user_id; }
}
