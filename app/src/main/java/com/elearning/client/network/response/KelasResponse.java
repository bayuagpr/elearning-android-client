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


    List<Kelas> kelasListCari;

    @Expose
    @SerializedName("last")
    Boolean last;

    @Expose
    @SerializedName("totalPages")
    Integer totalPages;

    @Expose
    @SerializedName("number")
    Integer number;

    @Expose
    @SerializedName("totalElements")
    Integer totalElements;
}
