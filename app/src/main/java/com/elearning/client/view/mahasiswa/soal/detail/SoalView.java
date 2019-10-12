package com.elearning.client.view.mahasiswa.soal.detail;

import android.content.Context;

import com.elearning.client.network.response.ExistHasilResponse;
import com.elearning.client.network.response.SoalResponse;

public interface SoalView {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusError(String message);
    void isExist(ExistHasilResponse supplierResponse);

}
