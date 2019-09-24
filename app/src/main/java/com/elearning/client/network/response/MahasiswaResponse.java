package com.elearning.client.network.response;

import com.elearning.client.model.Mahasiswa;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class MahasiswaResponse {
    Mahasiswa mahasiswa;

    @Expose
    @SerializedName("content")
    List<Mahasiswa> mahasiswaList;
}
