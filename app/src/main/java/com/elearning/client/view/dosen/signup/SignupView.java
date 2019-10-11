package com.elearning.client.view.dosen.signup;


import android.content.Context;


public interface SignupView {
        Context getContext();
        void showProgress();
        void hideProgress();
        void statusSuccess(String tokenResponse);
        void statusError(String message);
        void afterSubmitDosen();

}
