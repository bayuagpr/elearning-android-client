package com.elearning.client.network.response;

import com.elearning.client.model.Hasil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class HasilResponse {
    Hasil hasil;

    @Expose
    @SerializedName("content")
    List<Hasil> hasilList;
}
