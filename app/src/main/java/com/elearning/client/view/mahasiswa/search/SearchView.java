package com.elearning.client.view.mahasiswa.search;

import android.content.Context;

import com.elearning.client.model.Kelas;
import com.elearning.client.network.response.ExistEnrollResponse;
import com.elearning.client.network.response.KelasResponse;


public interface SearchView {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(KelasResponse kelasResponse);
    void statusError(String message);
    void loadMore(KelasResponse supplierResponse);
    void isExist(ExistEnrollResponse supplierResponse);
}
