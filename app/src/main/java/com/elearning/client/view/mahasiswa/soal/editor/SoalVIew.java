package com.elearning.client.view.mahasiswa.soal.editor;

import android.content.Context;

import com.elearning.client.network.response.UploadFileResponse;

public interface SoalVIew {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(String message);
    void statusError(String message);
    void handleFileUpload(UploadFileResponse uploadFileResponse);
}
