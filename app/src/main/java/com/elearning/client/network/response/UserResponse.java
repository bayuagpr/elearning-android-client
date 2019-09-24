package com.elearning.client.network.response;

import com.elearning.client.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class UserResponse {
    User user;

    @Expose
    @SerializedName("content")
    List<User> userList;
}
