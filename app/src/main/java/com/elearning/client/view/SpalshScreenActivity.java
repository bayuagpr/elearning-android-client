package com.elearning.client.view;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import com.elearning.client.R;
import  com.elearning.client.utils.SessionManager;
import com.elearning.client.view.auth.AuthStartActivityActivity;
import com.elearning.client.view.dosen.MainDosenActivity;
import com.elearning.client.view.mahasiswa.MainMahasiswaActivity;

public class SpalshScreenActivity extends AppCompatActivity {
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new SessionManager(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);
        new Handler().postDelayed(() -> {
            Intent activityIntent;
            if (session.isLoggedIn()) {
               // activityIntent = new Intent(this, MainDosenActivity.class);
                if(session.isDosen()){
                    activityIntent = new Intent(this, MainDosenActivity.class);
                }else if(session.isMahasiswa()){
                    activityIntent = new Intent(this, MainMahasiswaActivity.class);
                }else{
                    activityIntent = new Intent(this, AuthStartActivityActivity.class);
                }

            } else {
                activityIntent = new Intent(this, AuthStartActivityActivity.class);
            }

            startActivity(activityIntent);
            finish();
        },2000);
    }
}
