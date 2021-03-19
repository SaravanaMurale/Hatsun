package com.sosaley.hatsun.model;

import com.google.gson.annotations.SerializedName;

public class BaseDTO {

    @SerializedName("Response Code")
    private String responseCode;

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseCode() {
        return responseCode;
    }
}
