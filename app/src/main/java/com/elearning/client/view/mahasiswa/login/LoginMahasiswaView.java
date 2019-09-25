package com.elearning.client.view.mahasiswa.login;


import android.content.Context;


public interface LoginMahasiswaView {
        Context getContext();
        void showProgress();
        void hideProgress();
        void statusSuccess(String tokenResponse);
        void statusError(String message);

}
