package com.elearning.client.network.response;

import com.elearning.client.model.Fakultas;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class FakultasResponse {
    Fakultas fakultas;

    @Expose
    @SerializedName("content")
    List<Fakultas> fakultasList;
}
