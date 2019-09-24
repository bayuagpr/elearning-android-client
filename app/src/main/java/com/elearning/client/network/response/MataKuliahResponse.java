package com.elearning.client.network.response;

import com.elearning.client.model.MataKuliah;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class MataKuliahResponse {
    MataKuliah mataKuliah;

    @Expose
    @SerializedName("content")
    List<MataKuliah> mataKuliahList;
}
