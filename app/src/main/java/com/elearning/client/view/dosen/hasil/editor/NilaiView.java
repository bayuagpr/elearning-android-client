package com.elearning.client.view.dosen.hasil.editor;

import android.content.Context;

import com.elearning.client.network.response.UploadFileResponse;

public interface NilaiView {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(String message);
    void statusError(String message);
}
