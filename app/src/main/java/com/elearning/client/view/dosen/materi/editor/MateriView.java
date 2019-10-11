package com.elearning.client.view.dosen.materi.editor;

import android.content.Context;

import com.elearning.client.network.response.MataKuliahResponse;
import com.elearning.client.network.response.UploadFileResponse;

public interface MateriView {

    Context getContext();
    void showProgress();
    void hideProgress();
    void statusSuccess(String message);
    void statusError(String message);
    void handleFileUpload(UploadFileResponse uploadFileResponse);
}
