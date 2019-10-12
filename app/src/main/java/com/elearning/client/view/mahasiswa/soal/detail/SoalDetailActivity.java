package com.elearning.client.view.mahasiswa.soal.detail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.elearning.client.R;
import com.elearning.client.network.response.ExistHasilResponse;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.view.mahasiswa.jawaban.NilaiActivity;
import com.elearning.client.view.mahasiswa.soal.editor.PDFActivity;
import com.elearning.client.view.mahasiswa.soal.editor.SoalActivity;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wolfsoft4 on 22/1/18.
 */

public class SoalDetailActivity extends AppCompatActivity implements SoalView{
    SessionManager session;
   // TabItem tabEarly, tabLate;
   ProgressDialog progressDialog;
    FrameLayout frameLayout;
    SoalPresenter presenter;
    Date dueDate;
    Button buttonAtur, buttonLihat;
    ImageView backToList;
    private TextView price, address, desc;
    private ImageView imagev;
    private String  id, judul_soal, deskripsi_soal, idKelas, attachmentSoal,statusKumpul,isiJawaban,idHasil,nilai,
             komentar, attachmentHasil,tipeSoal ,strDueDate;
    Boolean statusExist,ternilai;
    SimpleDateFormat mFormatter;
    private static final int REQUEST_UPDATE = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soal_detail_mahasiswa);
        session = new SessionManager(this);

        initDataIntent();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        presenter = new SoalPresenter(this);
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
        presenter.getExistHasil(session.getKeyToken(),session.getKeyId(),id);




        backToList.setOnClickListener(v -> {
            finish();
        });
        buttonAtur.setOnClickListener(v -> {

            Intent intent = new Intent(SoalDetailActivity.this, NilaiActivity.class);
            intent.putExtra("idHasil", idHasil);
            intent.putExtra("isi_jawaban", isiJawaban);
            intent.putExtra("status_kumpul", statusKumpul);
            intent.putExtra("tipesoal", tipeSoal);
            intent.putExtra("ternilai", ternilai);
            intent.putExtra("id_soal", id);
            intent.putExtra("attachment_materi", attachmentHasil);
            intent.putExtra("nilai", nilai);
            intent.putExtra("komentar", komentar);
            intent.putExtra("status_exist", statusExist);
            intent.putExtra("due_date", dueDate);
            startActivityForResult(intent, REQUEST_UPDATE);
        });
        buttonLihat.setOnClickListener(v -> lihatMateriPDF());
        price.setText(judul_soal);
        address.setText("Tipe: "+tipeSoal+ "\n" +"Dikumpulkan paling lambat pada: "+ "\n" +strDueDate);
        desc.setText("Deskripsi: "+deskripsi_soal);


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

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }



    @Override
    public void statusError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void isExist(ExistHasilResponse supplierResponse) {
//        Intent intent = new Intent(SoalDetailActivity.this, NilaiActivity.class);
//        startActivityForResult(intent, REQUEST_UPDATE);
        if(supplierResponse.getStatus()){

            idHasil = supplierResponse.getHasilList().get(0).getId();
            isiJawaban = supplierResponse.getHasilList().get(0).getIsiJawaban();
            statusKumpul = supplierResponse.getHasilList().get(0).getStatus();
            tipeSoal = supplierResponse.getHasilList().get(0).getSoal().getTipe();
            ternilai = supplierResponse.getHasilList().get(0).isTernilai();
            attachmentHasil = supplierResponse.getHasilList().get(0).getAttachment();
            nilai = supplierResponse.getHasilList().get(0).getNilai();
            komentar = supplierResponse.getHasilList().get(0).getKomentar();
            statusExist = supplierResponse.getStatus();
            if (ternilai) {
                buttonAtur.setText("LIHAT NILAI");
            } else {
                buttonAtur.setText("PERBARUI JAWABAN");
            }
        }else {
            buttonAtur.setText("JAWAB SOAL");
        }


    }
}
