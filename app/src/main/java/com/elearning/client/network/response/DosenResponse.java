package com.elearning.client.network.response;

import com.elearning.client.model.Dosen;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class DosenResponse {
    Dosen dosen;

    @Expose
    @SerializedName("content")
    List<Dosen> dosenList;
}
