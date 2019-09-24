package com.elearning.client.view.kelas.editor;

import android.content.Context;

import com.elearning.client.network.response.MataKuliahResponse;


public interface KelasView {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(String message);
    void statusError(String message);
    void setListBarang(MataKuliahResponse barangResponse);
}
