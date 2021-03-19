package com.sosaley.hatsun.model;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordDTO {

    @SerializedName("mobile")
    private String mobile;
    @SerializedName("password")
    private String password;

    public ResetPasswordDTO(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
