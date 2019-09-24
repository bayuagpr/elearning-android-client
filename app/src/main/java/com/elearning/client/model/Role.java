package com.elearning.client.model;



import lombok.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Data
public class Role{
  private static final long serialVersionUID = 1L;

  @Expose
  @SerializedName("id")
  private Integer id;

  @Expose
  @SerializedName("nama")
  private NamaRole nama;

  @Expose
  @SerializedName("deskripsi")
  private String deskripsi;

}