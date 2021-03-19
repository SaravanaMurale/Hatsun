package com.sosaley.hatsun.model;

import com.google.gson.annotations.SerializedName;

public class UserDTO {

    @SerializedName("name")
    private String userName;
    @SerializedName("mobile")
    private String userMobile;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;


    public UserDTO(String userMobile) {
        this.userMobile = userMobile;
    }



    public UserDTO(String userMobile, String email, String password) {
        this.userMobile = userMobile;
        this.email = email;
        this.password = password;
    }

    public UserDTO(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public UserDTO(String userName, String userMobile, String email, String password) {
        this.userName = userName;
        this.userMobile = userMobile;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
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
}
