package com.sosaley.hatsun.model;

import com.google.gson.annotations.SerializedName;

public class UserResponseDTO extends BaseDTO {

    @SerializedName("id")
    private int userId;

    @SerializedName("name")
    private String userName;

    @SerializedName("mobile")
    private String userMobile;

    @SerializedName("email")
    private String userEmail;

    @SerializedName("useridentity")
    private String userIdentity;

    public UserResponseDTO(int userId, String userName, String userMobile, String userEmail, String userIdentity) {
        this.userId = userId;
        this.userName = userName;
        this.userMobile = userMobile;
        this.userEmail = userEmail;
        this.userIdentity = userIdentity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }
}
