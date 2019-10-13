package com.elearning.client.view.mahasiswa.enroll;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.elearning.client.R;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.view.BaseActivity;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wolfsoft4 on 22/1/18.
 */

public class AnggotaKelasActivity extends BaseActivity {
    SessionManager session;
    TabLayout bubbleTabBar;
   // TabItem tabEarly, tabLate;
    FrameLayout frameLayout;
    ViewPager viewpager;
    Date dueDate;
    Button buttonAtur, buttonLihat;
    ImageView backToList;
    private TextView price, address, desc;
    private ImageView imagev;
    private String  id, matkul_id, nama_kelas, idKelas, attachmentSoal,tipeSoal ,strDueDate;
    SimpleDateFormat mFormatter;
    private static final int REQUEST_UPDATE = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anggota_kelas_activity);
        session = new SessionManager(this);

        initDataIntent();
        bubbleTabBar = findViewById(R.id.bubbleTabBarSoal);
        //tabEarly = findViewById(R.id.tabEarly);
        //tabLate = findViewById(R.id.tabLate);
        price = findViewById(R.id.tvlistSoal);
        backToList = findViewById(R.id.backToListSoal);

        backToList.setOnClickListener(v -> {
            finish();
        });

        price.setText("Anggota Kelas "+nama_kelas);

        viewpager = findViewById(R.id.viewpager);
        viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(bubbleTabBar));

        bubbleTabBar.setupWithViewPager(viewpager);

    }

    private void initDataIntent() {
        Intent intent= getIntent();
        id = intent.getStringExtra("id");
        matkul_id = intent.getStringExtra("matkul_id");
        nama_kelas = intent.getStringExtra("nama_kelas");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
