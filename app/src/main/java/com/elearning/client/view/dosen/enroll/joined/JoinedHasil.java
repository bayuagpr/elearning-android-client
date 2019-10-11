package com.elearning.client.view.dosen.enroll.joined;

import android.content.Context;

import com.elearning.client.network.response.EnrollmentResponse;
import com.elearning.client.network.response.HasilResponse;

public interface JoinedHasil {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(EnrollmentResponse kelasResponse);
    void statusError(String message);
    void loadMore(EnrollmentResponse supplierResponse);

}
