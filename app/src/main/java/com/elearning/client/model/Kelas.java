package com.elearning.client.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Kelas{

  @Expose
  @SerializedName("id")
  private String id;

  @Expose
  @SerializedName("nama")
  private String nama;

  public Kelas(String id) {
    this.id = id;
  }

  @Expose
  @SerializedName("dosen")
  private Dosen dosen;

  @Expose
  @SerializedName("mataKuliah")
  private MataKuliah mataKuliah;

}