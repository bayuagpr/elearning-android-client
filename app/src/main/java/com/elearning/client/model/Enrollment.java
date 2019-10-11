package com.elearning.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Enrollment{
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("disetujui")
    private boolean disetujui;

    @Expose
    @SerializedName("kelas")
    private Kelas kelas;


    @Expose
    @SerializedName("mahasiswa")
    private Mahasiswa mahasiswa;

 public boolean getDisetujui() {
  // TODO Auto-generated method stub
  return disetujui;
}
}