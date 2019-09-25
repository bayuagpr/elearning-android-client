package com.elearning.client.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Kelas{

  @Expose
  @SerializedName("id")
  private String id;

  @Expose
  @SerializedName("nama")
  private String nama;

  @Expose
  @SerializedName("dosen")
  private Dosen dosen;

  @Expose
  @SerializedName("mataKuliah")
  private MataKuliah mataKuliah;

}