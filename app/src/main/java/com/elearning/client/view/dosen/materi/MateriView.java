package com.elearning.client.view.dosen.materi;

import android.content.Context;

import com.elearning.client.network.response.KelasResponse;
import com.elearning.client.network.response.MateriResponse;

public interface MateriView {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(MateriResponse kelasResponse);
    void statusError(String message);
    void loadMore(MateriResponse supplierResponse);

}
