package com.elearning.client.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Jurusan {

  @Expose
  @SerializedName("id")
  private String id;

  @Expose
  @SerializedName("nama")
  private String nama;

  @Expose
  @SerializedName("fakultas")
  private Fakultas fakultas;
}