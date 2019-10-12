package com.elearning.client.view.mahasiswa.signup;


import android.content.Context;

import com.elearning.client.network.response.JurusanResponse;


public interface SignupMahasiswaView {
        Context getContext();
        void showProgress();
        void hideProgress();
        void statusSuccess(String tokenResponse);
        void statusError(String message);
        void afterSubmitMahasiswa();
        void setListJurusan(JurusanResponse mataKuliahResponse);
}
