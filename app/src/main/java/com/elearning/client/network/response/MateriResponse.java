package com.elearning.client.network.response;

import com.elearning.client.model.Materi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class MateriResponse {
    Materi materi;

    @Expose
    @SerializedName("content")
    List<Materi> materiList;
}
