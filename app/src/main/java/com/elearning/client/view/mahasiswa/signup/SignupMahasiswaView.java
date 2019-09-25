package com.elearning.client.view.mahasiswa.signup;


import android.content.Context;


public interface SignupMahasiswaView {
        Context getContext();
        void showProgress();
        void hideProgress();
        void statusSuccess(String tokenResponse);
        void statusError(String message);

}
