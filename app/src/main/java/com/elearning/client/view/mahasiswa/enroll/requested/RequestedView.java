package com.elearning.client.view.mahasiswa.enroll.requested;

import android.content.Context;

import com.elearning.client.network.response.EnrollmentResponse;

public interface RequestedView {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(EnrollmentResponse kelasResponse);
    void statusError(String message);
    void loadMore(EnrollmentResponse supplierResponse);
    void refresh();

}
