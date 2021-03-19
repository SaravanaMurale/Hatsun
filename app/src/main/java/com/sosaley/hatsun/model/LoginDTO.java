package com.sosaley.hatsun.model;

import com.google.gson.annotations.SerializedName;

public class LoginDTO {

    @SerializedName("mobile")
    private String loginMobileNumber;
    @SerializedName("password")
    private String loginPassword;

    public LoginDTO(String loginMobileNumber, String loginPassword) {
        this.loginMobileNumber = loginMobileNumber;
        this.loginPassword = loginPassword;
    }

    public String getLoginMobileNumber() {
        return loginMobileNumber;
    }

    public void setLoginMobileNumber(String loginMobileNumber) {
        this.loginMobileNumber = loginMobileNumber;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
