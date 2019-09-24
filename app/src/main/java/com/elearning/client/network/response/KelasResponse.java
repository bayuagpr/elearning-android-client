package com.elearning.client.network.response;

import com.elearning.client.model.Kelas;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class KelasResponse {
    Kelas kelas;

    @Expose
    @SerializedName("content")
    List<Kelas> kelasList;
}
