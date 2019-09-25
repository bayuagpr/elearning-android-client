package com.elearning.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class UserLogin {
    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("password")
    private String password;
}
