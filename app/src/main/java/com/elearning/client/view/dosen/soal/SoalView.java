package com.elearning.client.view.dosen.soal;

import android.content.Context;

import com.elearning.client.network.response.SoalResponse;

public interface SoalView {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(SoalResponse kelasResponse);
    void statusError(String message);
    void loadMore(SoalResponse supplierResponse);

}
