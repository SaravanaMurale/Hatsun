package com.sosaley.hatsun.model;

import com.google.gson.annotations.SerializedName;

public class GetUserDTO {

    @SerializedName("id")
    private int userId;

    public GetUserDTO(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
