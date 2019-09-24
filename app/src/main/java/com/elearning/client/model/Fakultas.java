package com.elearning.client.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Fakultas {
  private static final long serialVersionUID = 1L;

  @Expose
  @SerializedName("id")
  private String id;

  @Expose
  @SerializedName("nama")
  private String nama;

  @Expose
  @SerializedName("deskripsi")
  private String deskripsi;



}