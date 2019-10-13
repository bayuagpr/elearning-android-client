package com.elearning.client.view.auth;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.cardview.widget.CardView;
import android.widget.FrameLayout;

import com.elearning.client.R;
import com.elearning.client.view.BaseLoginActivity;
import com.elearning.client.view.dosen.login.LoginDosenActivityActivity;
import com.elearning.client.view.mahasiswa.login.LoginMahasiswaMahasiswaActivityActivity;

import butterknife.ButterKnife;
import butterknife.BindView;

public class AuthStartActivityActivity extends BaseLoginActivity {

    @BindView(R.id.header) LinearLayout header;
    @BindView(R.id.scrollView) ScrollView scrollView;
    @BindView(R.id.itemdosen) CardView itemdosen;
    @BindView(R.id.button1) FrameLayout button1;
    @BindView(R.id.item1) CardView item1;
    @BindView(R.id.button2) FrameLayout button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_start_activity);
        ButterKnife.bind(this);
        button1.setOnClickListener(view -> {

            Intent intentDosen = new Intent(AuthStartActivityActivity.this, LoginDosenActivityActivity.class);
            startActivity(intentDosen);
        });
        button2.setOnClickListener(v -> {
            Intent intentDosen = new Intent(AuthStartActivityActivity.this, LoginMahasiswaMahasiswaActivityActivity.class);
            startActivity(intentDosen);
        });

    }



}
