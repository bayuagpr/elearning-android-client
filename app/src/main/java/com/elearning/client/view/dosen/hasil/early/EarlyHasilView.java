package com.elearning.client.view.dosen.hasil.early;

import android.content.Context;

import com.elearning.client.network.response.HasilResponse;
import com.elearning.client.network.response.MateriResponse;

public interface EarlyHasilView {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(HasilResponse kelasResponse);
    void statusError(String message);
    void loadMore(HasilResponse supplierResponse);

}
