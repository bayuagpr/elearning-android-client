package com.elearning.client.view.mahasiswa.kelas.tergabung;

import android.content.Context;

import com.elearning.client.network.response.EnrollmentResponse;
import com.elearning.client.network.response.KelasResponse;

public interface TergabungKelasView {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(EnrollmentResponse kelasResponse);
    void statusError(String message);
    void loadMore(EnrollmentResponse supplierResponse);

}
