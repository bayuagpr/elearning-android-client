package com.elearning.client.view.kelas;

import android.content.Context;

import com.elearning.client.network.response.KelasResponse;

public interface KelasView {
    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(KelasResponse penjualanResponse);
    void statusError(String message);

}
