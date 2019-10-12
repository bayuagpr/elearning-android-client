package com.elearning.client.view.mahasiswa.kelas.detail;

import android.content.Context;

import com.elearning.client.network.response.EnrollmentResponse;

public interface KelasDetailView {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(String message);
    void statusError(String message);

}
