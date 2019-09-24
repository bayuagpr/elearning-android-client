package com.elearning.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import lombok.Data;

@Data
public class Mahasiswa {

  @Expose
  @SerializedName("nim")
  private String nim;

  @Expose
  @SerializedName("nama")
  private String nama;

  @Expose
  @SerializedName("tanggal_lahir")
  private Date tanggal_lahir;

  @Expose
  @SerializedName("tempat_lahir")
  private String tempat_lahir;

}