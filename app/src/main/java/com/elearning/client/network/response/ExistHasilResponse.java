package com.elearning.client.network.response;

import com.elearning.client.model.Enrollment;
import com.elearning.client.model.Hasil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class ExistHasilResponse {
    @Expose
    @SerializedName("status")
    Boolean status;

    @Expose
    @SerializedName("hasilList")
    List<Hasil> hasilList;
}
