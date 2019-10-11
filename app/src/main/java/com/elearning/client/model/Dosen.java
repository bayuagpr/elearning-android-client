package com.elearning.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

@Data
public class Dosen {

	@Expose
	@SerializedName("nidn")
  	private String nidn;

	@Expose
	@SerializedName("alamat")
  	private String alamat;

	@Expose
	@SerializedName("nama")
  	private String nama;

	@Expose
	@SerializedName("tanggal_lahir")
	private long tanggal_lahir;

	@Expose
	@SerializedName("tempat_lahir")
	private String tempat_lahir;

	@Expose
	@SerializedName("user")
	private User user;
}