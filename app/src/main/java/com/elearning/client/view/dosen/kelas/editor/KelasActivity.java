package com.elearning.client.view.dosen.kelas.editor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.elearning.client.R;
import com.elearning.client.model.Dosen;
import com.elearning.client.model.Kelas;
import com.elearning.client.model.MataKuliah;
import com.elearning.client.network.response.MataKuliahResponse;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.view.BaseActivity;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class KelasActivity extends BaseActivity implements KelasView {

    KelasPresenter presenter;
    ProgressDialog progressDialog;
    SessionManager session;
    SpinnerMatkulAdapter adapter;

    String id, nama, nama_kelas, matkul_id, namaDosen, fakultas, fakultasId;

    @BindView(R.id.matkul)
    Spinner s_nama;
    @BindView(R.id.nama_kelas)
    EditText namaKelas;
    @BindView(R.id.content_simpan)
    LinearLayout content_simpan;
    @BindView(R.id.content_update)
    LinearLayout content_update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbarKelas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Simpan Kelas");
        session = new SessionManager(this);
        namaDosen = session.getKeyUsername();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        presenter = new KelasPresenter(this);

        presenter.getListMatkul(session.getKeyToken());

        initDataIntent();

        s_nama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nama = ((TextView) view.findViewById(R.id.nama_spinner)).getText().toString();
                matkul_id = ((TextView) view.findViewById(R.id.matkul_id_spinner)).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    @OnClick(R.id.simpan) void simpan() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String pre = "KEL";
        String idNew = pre.concat(randomUUIDString);
        Log.d("isi", "simpan: "+matkul_id);
        Kelas kelas = new Kelas(idNew);
        kelas.setNama(namaKelas.getText().toString());
        MataKuliah mataKuliah = new MataKuliah();
        mataKuliah.setId(matkul_id);
        kelas.setMataKuliah(mataKuliah);
        Dosen dosen = new Dosen();
        dosen.setNidn(session.getKeyId());
        kelas.setDosen(dosen);
        presenter.saveKelas(
                session.getKeyToken(),
                kelas

        );
    }

    @OnClick(R.id.update) void update() {
        Kelas kelas = new Kelas(id);
        kelas.setNama(namaKelas.getText().toString());
        MataKuliah mataKuliah = new MataKuliah();
        mataKuliah.setId(matkul_id);
        kelas.setMataKuliah(mataKuliah);
        presenter.updateKelas(
                session.getKeyToken(),
                id,
               kelas        );
    }

    @OnClick(R.id.hapus) void hapus() {
        presenter.deleteKelas(
                session.getKeyToken(),
                id
        );
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void statusError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setListMatkul(MataKuliahResponse mataKuliahResponse) {
        adapter = new SpinnerMatkulAdapter(this, R.layout.spinner_matkul,
                mataKuliahResponse.getMataKuliahList());
        s_nama.setAdapter(adapter);

        setTextEditor();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initDataIntent() {
        Intent intent= getIntent();
        id = intent.getStringExtra("id");
        matkul_id = intent.getStringExtra("matkul_id");
        nama_kelas = intent.getStringExtra("nama_kelas");

    }

    private void setTextEditor() {
        if (id != null) {
            getSupportActionBar().setTitle("Update kelas");

            namaKelas.setText(nama_kelas);
            s_nama.setSelection(adapter.getItemIndexById(matkul_id));
            Log.d("test intent", "matkulid: "+matkul_id);

            content_update.setVisibility(View.VISIBLE);
            content_simpan.setVisibility(View.GONE);
        } else {
            getSupportActionBar().setTitle("Simpan kelas");
        }
    }

}
