package com.elearning.client.view.dosen.soal.detail;

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
import com.elearning.client.view.dosen.kelas.detail.KelasDetailActivity;
import com.elearning.client.view.dosen.kelas.editor.KelasActivity;
import com.elearning.client.view.dosen.soal.editor.PDFActivity;
import com.elearning.client.view.dosen.soal.editor.SoalActivity;
import com.fxn.BubbleTabBar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wolfsoft4 on 22/1/18.
 */

public class SoalDetailActivity extends AppCompatActivity {
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
    private String  id, judul_soal, deskripsi_soal, idKelas, attachmentSoal,tipeSoal ,strDueDate;
    SimpleDateFormat mFormatter;
    private static final int REQUEST_UPDATE = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soal_detail_dosen);
        session = new SessionManager(this);

        initDataIntent();
        bubbleTabBar = findViewById(R.id.bubbleTabBarSoal);
        //tabEarly = findViewById(R.id.tabEarly);
        //tabLate = findViewById(R.id.tabLate);
        price = findViewById(R.id.tvpriceSoal);
        address = findViewById(R.id.tvaddressSoal);
        desc = findViewById(R.id.tvdescSoal);
        backToList = findViewById(R.id.backToListSoal);
        buttonAtur = findViewById(R.id.btnAturSoal);
        buttonLihat = findViewById(R.id.btnLihatSoal);
        mFormatter = new SimpleDateFormat(" dd MMMM yyyy hh:mm aa");
        strDueDate = mFormatter.format(dueDate);
        backToList.setOnClickListener(v -> {
            finish();
        });
        buttonAtur.setOnClickListener(v -> {
            Intent intent = new Intent(SoalDetailActivity.this, SoalActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("judul_soal", judul_soal);
            intent.putExtra("deskripsi_soal", deskripsi_soal);
            intent.putExtra("id_kelas", idKelas);
            intent.putExtra("attachment_soal", attachmentSoal);
            intent.putExtra("tipe_soal", tipeSoal);
            intent.putExtra("due_date", dueDate);
            startActivityForResult(intent, REQUEST_UPDATE);
        });
        buttonLihat.setOnClickListener(v -> lihatMateriPDF());
        price.setText(judul_soal);
        address.setText("Tipe: "+tipeSoal+ "\n" +"Dikumpulkan paling lambat pada: "+ "\n" +strDueDate);
        desc.setText("Deskripsi: "+deskripsi_soal);
        viewpager = findViewById(R.id.viewpager);
        viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(bubbleTabBar));

        bubbleTabBar.setupWithViewPager(viewpager);

    }
    void lihatMateriPDF(){
        Intent intent = new Intent(SoalDetailActivity.this, PDFActivity.class);
        intent.putExtra("attachment_soal", attachmentSoal);
        startActivity(intent);
    }
    private void initDataIntent() {

            Intent intent= getIntent();
            id = intent.getStringExtra("id");
            judul_soal = intent.getStringExtra("judul_soal");
            deskripsi_soal = intent.getStringExtra("deskripsi_soal");
            idKelas = intent.getStringExtra("id_kelas");
            attachmentSoal = intent.getStringExtra("attachment_soal");
            tipeSoal = intent.getStringExtra("tipe_soal");
            dueDate = (Date)intent.getSerializableExtra("due_date");


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
