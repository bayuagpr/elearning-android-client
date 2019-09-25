package com.elearning.client.view.dosen.kelas;

import android.content.Context;

import com.elearning.client.network.response.KelasResponse;

public interface KelasView {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(KelasResponse kelasResponse);
    void statusError(String message);

}
