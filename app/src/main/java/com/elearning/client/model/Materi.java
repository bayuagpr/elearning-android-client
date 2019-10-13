package com.elearning.client.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class Materi implements Serializable{
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
  @SerializedName("creationDate")
  private Date creationDate;

  @Expose
  @SerializedName("kelas")
  private Kelas kelas;
}