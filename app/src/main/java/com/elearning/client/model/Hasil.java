package com.elearning.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;




import lombok.Data;

@Data
public class Hasil{

  @Expose
  @SerializedName("id")
  private String id;

  @Expose
  @SerializedName("isiJawaban")
  private String isiJawaban;

  @Expose
  @SerializedName("attachment")
  private String attachment;

  @Expose
  @SerializedName("nilai")
  private String nilai;

  @Expose
  @SerializedName("komentar")
  private String komentar;

  @Expose
  @SerializedName("status")
  private StatusHasil status;

  @Expose
  @SerializedName("mahasiswa")
  private Mahasiswa mahasiswa;

  @Expose
  @SerializedName("soal")
  private Soal soal;
}