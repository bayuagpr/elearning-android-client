package com.elearning.client.model;

import java.util.Date;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import lombok.Data;
@Data
public class Soal {
  private static final long serialVersionUID = 1L;

  @Expose
  @SerializedName("id")
  private String id;

  @Expose
  @SerializedName("judul")
  private String judul;
  @Expose
  @SerializedName("deskripsi")
  private String deskripsi;
  @Expose
  @SerializedName("attachment")
  private String attachment;
  @Expose
  @SerializedName("tipe")
  private String tipe;

  @Expose
  @SerializedName("dueDate")
  private long dueDate;

  @Expose
  @SerializedName("kelas")
  private Kelas kelas;
}