package com.elearning.client.view.dosen.enroll.requested;

import android.content.Context;

import com.elearning.client.network.response.EnrollmentResponse;
import com.elearning.client.network.response.HasilResponse;

public interface RequestedView {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(EnrollmentResponse kelasResponse);
    void statusError(String message);
    void loadMore(EnrollmentResponse supplierResponse);
    void refresh();

}
