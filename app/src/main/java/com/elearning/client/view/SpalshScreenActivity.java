package com.elearning.client.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.elearning.client.R;
import  com.elearning.client.utils.SessionManager;
import com.elearning.client.view.auth.AuthStartActivityActivity;

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
                activityIntent = new Intent(this, MainActivity.class);
            } else {
                activityIntent = new Intent(this, AuthStartActivityActivity.class);
            }

            startActivity(activityIntent);
            finish();
        },2000);
    }
}
