package com.elearning.client.view.dosen.login;


import android.content.Context;


public interface LoginView {
        Context getContext();
        void showProgress();
        void hideProgress();
        void statusSuccess(String tokenResponse);
        void statusError(String message);

}
