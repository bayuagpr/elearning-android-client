package com.elearning.client.network.response;

import com.elearning.client.model.Soal;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class SoalResponse {
    Soal soal;

    @Expose
    @SerializedName("content")
    List<Soal> soalList;

}
