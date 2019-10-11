package com.elearning.client.view.dosen.kelas.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.elearning.client.R;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.view.dosen.MainDosenActivity;
import com.elearning.client.view.dosen.enroll.AnggotaKelasActivity;
import com.elearning.client.view.dosen.kelas.editor.KelasActivity;
import com.fxn.BubbleTabBar;

/**
 * Created by wolfsoft4 on 22/1/18.
 */

public class KelasDetailActivity extends AppCompatActivity {
    SessionManager session;
    BubbleTabBar bubbleTabBar;
    FrameLayout frameLayout;
    ViewPager viewpager;
    Button buttonAtur, buttonAnggota;
    ImageView backToList;
    private TextView price, address;
    private ImageView imagev;
    private String  id, nama, nama_kelas, matkul_id, namaDosen;
    private static final int REQUEST_UPDATE = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kelas_detail_dosen);
        session = new SessionManager(this);

        initDataIntent();
        bubbleTabBar = findViewById(R.id.bubbleTabBar);
        price = (TextView) findViewById(R.id.tvprice);
        address = (TextView) findViewById(R.id.tvaddress);
        backToList = findViewById(R.id.backToList);
        buttonAtur = findViewById(R.id.btnAtur);
        buttonAnggota = findViewById(R.id.btnAnggota);
        backToList.setOnClickListener(v -> {
            Intent intent = new Intent(KelasDetailActivity.this, MainDosenActivity.class);
            startActivity(intent);
        });
        buttonAtur.setOnClickListener(v -> {
            Intent intent = new Intent(KelasDetailActivity.this, KelasActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("matkul_id", matkul_id);
            intent.putExtra("nama_kelas", nama_kelas);
            startActivityForResult(intent, REQUEST_UPDATE);
        });
        buttonAnggota.setOnClickListener(v -> {
            Intent intent = new Intent(KelasDetailActivity.this, AnggotaKelasActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("matkul_id", matkul_id);
            intent.putExtra("nama_kelas", nama_kelas);
            startActivity(intent);
        });
        price.setText(nama_kelas);
        address.setText(namaDosen);
        viewpager = findViewById(R.id.viewpager);
        viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        bubbleTabBar.addBubbleListener(i -> {
            switch (i) {
                case R.id.home:
                    viewpager.setCurrentItem(0);
                    break;
                case R.id.log:
                    viewpager.setCurrentItem(1);
                    break;
            }
        });

        bubbleTabBar.setupBubbleTabBar(viewpager);

    }
    private void initDataIntent() {
        Intent intent= getIntent();
        id = intent.getStringExtra("id");
        matkul_id = intent.getStringExtra("matkul_id");
        nama_kelas = intent.getStringExtra("nama_kelas");
        namaDosen = session.getKeyNama();

    }


}
