package com.elearning.client.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class User{

  @Expose
  @SerializedName("id")
  private String id;

  @Expose
  @SerializedName("email")
  private String email;

  @Expose
  @SerializedName("password")
  private String password;

  @Expose
  @SerializedName("role")
  private Role role;

  @Expose
  @SerializedName("token") String token;

  @Expose
  @SerializedName("authorities")
  private List<Authority> authorities;

  @Expose
  @SerializedName("enabled")
  private Boolean enabled;

  @Expose
  @SerializedName("username")
  private String username;
}