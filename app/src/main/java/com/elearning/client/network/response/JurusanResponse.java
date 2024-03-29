package com.elearning.client.network.response;

import com.elearning.client.model.Jurusan;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class JurusanResponse {
    Jurusan jurusan;

    @Expose
    @SerializedName("content")
    List<Jurusan> jurusanList;
}
