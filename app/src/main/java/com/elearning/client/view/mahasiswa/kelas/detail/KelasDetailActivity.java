package com.elearning.client.view.mahasiswa.kelas.detail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.elearning.client.model.Enrollment;
import com.elearning.client.model.Kelas;
import com.elearning.client.model.Mahasiswa;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.view.BaseActivity;
import com.elearning.client.view.dosen.MainDosenActivity;
import com.elearning.client.view.mahasiswa.enroll.AnggotaKelasActivity;
import com.elearning.client.view.mahasiswa.MainMahasiswaActivity;
import com.fxn.BubbleTabBar;

import java.util.UUID;

/**
 * Created by wolfsoft4 on 22/1/18.
 */

public class KelasDetailActivity extends BaseActivity implements KelasDetailView{
    SessionManager session;
    BubbleTabBar bubbleTabBar;
    ProgressDialog progressDialog;
    FrameLayout frameLayout;
    ViewPager viewpager;
    Button buttonAtur, buttonAnggota;
    ImageView backToList;
    KelasDetailPresenter presenter;
    private TextView price, address, status;
    private ImageView imagev;
    private String  id, nama, nama_kelas, matkul_id, namaDosen;
    private Boolean statusExist, statusEnroll;
    private static final int REQUEST_UPDATE = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kelas_detail_dosen);
        session = new SessionManager(this);


        bubbleTabBar = findViewById(R.id.bubbleTabBar);
        price = (TextView) findViewById(R.id.tvprice);
        address = (TextView) findViewById(R.id.tvaddress);
        status = findViewById(R.id.statusTv);
        backToList = findViewById(R.id.backToList);
        buttonAtur = findViewById(R.id.btnAtur);
        buttonAnggota = findViewById(R.id.btnAnggota);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        presenter = new KelasDetailPresenter(this);


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
        buttonAtur.setVisibility(View.GONE);
        initDataIntent();
        setTextEditor();
        buttonAtur.setOnClickListener(v -> {
            showDialog(nama_kelas);
        });
        backToList.setOnClickListener(v -> {
            Intent intent = new Intent(KelasDetailActivity.this, MainMahasiswaActivity.class);
            startActivity(intent);
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
    }
    private void initDataIntent() {
        Intent intent= getIntent();
        id = intent.getStringExtra("id");
        matkul_id = intent.getStringExtra("matkul_id");
        nama_kelas = intent.getStringExtra("nama_kelas");
        namaDosen = intent.getStringExtra("nama_dosen");
        statusEnroll = intent.getBooleanExtra("status_enroll",false);
        statusExist = intent.getBooleanExtra("status_exist",false);
    }
    private void showDialog(String nama){
        SetujuiDialog dialog=new SetujuiDialog();
        dialog.setListener(respononse -> {
            if(respononse){
                UUID uuid = UUID.randomUUID();
                String randomUUIDString = uuid.toString();
                String pre = "KEL";
                String idNew = pre.concat(randomUUIDString);
                Enrollment enroll = new Enrollment();
                Mahasiswa mhs = new Mahasiswa();
                Kelas kls = new Kelas(idNew);
                mhs.setNim(session.getKeyId());
                kls.setId(id);
                enroll.setKelas(kls);
                enroll.setMahasiswa(mhs);
                presenter.saveEnroll(session.getKeyToken(),enroll);
                //DO SOMETHING IF YES
            }
        });

        dialog.deleteDialog(this,nama);
    }
    private void setTextEditor() {
        if (statusExist) {

            if (!statusEnroll) {
                Log.d("statusExist", "statusEnroll: "+statusEnroll);
                viewpager.setVisibility(View.GONE);
                status.setVisibility(View.VISIBLE);
                status.setText("Masih menunggu persetujuan bergabung dari "+"\n"+namaDosen);
            }
        } else {
            viewpager.setVisibility(View.GONE);
            status.setVisibility(View.VISIBLE);
            status.setText("Gabung dengan kelas ini untuk melihat materi dan mengerjakan soal");
            buttonAtur.setVisibility(View.VISIBLE);
            buttonAtur.setText("BERGABUNG DENGAN KELAS INI");
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
    public void statusSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void statusError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
