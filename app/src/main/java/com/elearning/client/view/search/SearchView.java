package com.elearning.client.view.search;

import android.content.Context;

import com.elearning.client.network.response.KelasResponse;

public interface SearchView {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(KelasResponse penjualanResponse);
    void statusError(String message);
}
