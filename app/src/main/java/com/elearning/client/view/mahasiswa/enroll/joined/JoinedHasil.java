package com.elearning.client.view.mahasiswa.enroll.joined;

import android.content.Context;

import com.elearning.client.network.response.EnrollmentResponse;

public interface JoinedHasil {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(EnrollmentResponse kelasResponse);
    void statusError(String message);
    void loadMore(EnrollmentResponse supplierResponse);

}
