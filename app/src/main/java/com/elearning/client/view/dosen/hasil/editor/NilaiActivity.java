package com.elearning.client.view.dosen.hasil.editor;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.elearning.client.R;
import com.elearning.client.model.Hasil;
import com.elearning.client.model.Kelas;
import com.elearning.client.model.Materi;
import com.elearning.client.network.response.UploadFileResponse;
import com.elearning.client.utils.SessionManager;
import com.google.android.material.card.MaterialCardView;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;


public class NilaiActivity extends AppCompatActivity implements PickiTCallbacks, NilaiView, EasyPermissions.PermissionCallbacks{

    private static final String TAG = "Nilai Activity";
    NilaiPresenter presenter;
    ProgressDialog progressDialog;
    SessionManager session;
    private static final int REQUEST_DIR_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    PickiT pickiT;
    String id, isi_jawaban , status_kumpul, nama_mahasiswa , tipesoal,nim_mahasiswa , id_soal , attachment_materi,namaDosen,nilai,komentar;
    MultipartBody.Part fileToUpload;
    Boolean ternilai;
    @BindView(R.id.deskripsiDinilaiEt)
    EditText descDinilai;
    @BindView(R.id.nilaiEt)
    EditText nilaiInput;
    @BindView(R.id.komentarEt)
    EditText komentarInput;
    @BindView(R.id.berkasLihatTv)
    TextView lihatBerkasDinilai;
    @BindView(R.id.berkasLihatCv)
    MaterialCardView berkasLihat;
    @BindView(R.id.berkasGantiCv)
    MaterialCardView berkasGanti;
    @BindView(R.id.berkasSimpanCv)
    MaterialCardView berkasSimpan;
    @BindView(R.id.identitasDinilai)
    TextView identitasDinilaiTv;
    @BindView(R.id.statusHasil)
    TextView statusHasilTv;
    @BindView(R.id.content_simpan_nilai)
    LinearLayout content_simpan;
    @BindView(R.id.content_update_nilai)
    LinearLayout content_update;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilai);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbarKelas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Simpan Nilai");
        session = new SessionManager(this);
        namaDosen = session.getKeyUsername();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        presenter = new NilaiPresenter(this);
        //container = findViewById(R.id.activityMainPdfView);
        pickiT = new PickiT(this, this);
        berkasLihat.setVisibility(View.VISIBLE);
        berkasGanti.setVisibility(View.GONE);
        berkasSimpan.setVisibility(View.GONE);
        initDataIntent();
        setTextEditor();
        nilaiInput.setFilters(new InputFilter[]{ new BatasNilai("1", "100")});
        lihatBerkasDinilai.setOnClickListener(v -> lihatMateriPDF());


    }

    void lihatMateriPDF(){
        Intent intent = new Intent(NilaiActivity.this, PDFActivity.class);
        intent.putExtra("attachment_materi", attachment_materi);
        startActivity(intent);
    }

    @OnClick(R.id.saveBtnNilai) void simpan() {
        Hasil materi = new Hasil();
        materi.setAttachment(attachment_materi);
        materi.setIsiJawaban(isi_jawaban);
        materi.setNilai(nilaiInput.getText().toString());
        materi.setKomentar(komentarInput.getText().toString());
        materi.setTernilai(true);
        presenter.simpanNilai(
                session.getKeyToken(),
                id,
                materi        );

    }

    @OnClick(R.id.updateNilai) void update() {
        Hasil materi = new Hasil();
        materi.setAttachment(attachment_materi);
        materi.setIsiJawaban(isi_jawaban);
        materi.setNilai(nilaiInput.getText().toString());
        materi.setKomentar(komentarInput.getText().toString());
        materi.setTernilai(true);
        presenter.simpanNilai(
                session.getKeyToken(),
                id,
                materi        );
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
    public void onDestroy() {
        super.onDestroy();
        pickiT.deleteTemporaryFile();
        presenter.detachView();

    }

    private void initDataIntent() {
        Intent intent= getIntent();
        id = intent.getStringExtra("id");
        isi_jawaban = intent.getStringExtra("isi_jawaban");
        status_kumpul = intent.getStringExtra("status_kumpul");
        nama_mahasiswa = intent.getStringExtra("nama_mahasiswa");
        nim_mahasiswa = intent.getStringExtra("nim_mahasiswa");
        tipesoal = intent.getStringExtra("tipesoal");
        id_soal = intent.getStringExtra("id_soal");
        ternilai = intent.getBooleanExtra("ternilai",false);
        attachment_materi = intent.getStringExtra("attachment_materi");
        nilai = intent.getStringExtra("nilai");
        komentar = intent.getStringExtra("komentar");


    }

    private void setTextEditor() {
        if (ternilai) {
            getSupportActionBar().setTitle("Update Nilai");
            nilaiInput.setText(nilai);
            komentarInput.setText(komentar);
            descDinilai.setText(isi_jawaban);
            identitasDinilaiTv.setText("Nama: "+nama_mahasiswa+"\n"+"NIM: "+nim_mahasiswa);
            statusHasilTv.setText("Tipe Soal: "+tipesoal+"\n"+"Status Pengumpulan: "+status_kumpul);
            //container.setVisibility(View.VISIBLE);
            content_update.setVisibility(View.VISIBLE);
            content_simpan.setVisibility(View.GONE);
        } else {
            descDinilai.setText(isi_jawaban);
            identitasDinilaiTv.setText("Nama: "+nama_mahasiswa+"\n"+"NIM: "+nim_mahasiswa);
            statusHasilTv.setText("Tipe Soal: "+tipesoal+"\n"+"Status Pengumpulan: "+status_kumpul);
            getSupportActionBar().setTitle("Simpan Nilai");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DIR_CODE && resultCode == Activity.RESULT_OK&& data != null && data.getData() != null) {
            uri = data.getData();
            if (EasyPermissions.hasPermissions(NilaiActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                progressDialog.show();
                //String filePath = getRealPathFromURIPath(uri, SoalActivity.this);
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
            } else {
                EasyPermissions.requestPermissions(NilaiActivity.this, "This app needs access to your file storage so that it can read photos.", READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

//    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
//        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
//        if (cursor == null) {
//            return contentURI.getPath();
//        } else {
//            cursor.moveToFirst();
//            int idx = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
//            return cursor.getString(idx);
//        }
//    }

        @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        if (uri != null) {
            progressDialog.show();
        }
            pickiT.getPath(uri, Build.VERSION.SDK_INT);

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }

    private void showLongToast(final String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }


    @Override
    public void PickiTonStartListener() {

    }

    @Override
    public void PickiTonProgressUpdate(int progress) {

    }

    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {
        //  Check if it was a Drive/local/unknown provider file and display a Toast
        if (wasDriveFile){
            showLongToast("Drive file was selected");
        }else if (wasUnknownProvider){
            showLongToast("File was selected from unknown provider");
        }else {
            showLongToast("Local file was selected");
        }

        //  Chick if it was successful
        if (wasSuccessful) {
            //  Set returned path to TextView
            File file = new File(path);
            Log.d(TAG, "filePath=" + path);
            //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            RequestBody mFile = RequestBody.create(MediaType.parse("application/pdf"), file);
            fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
            showLongToast(file.getName()+" terpilih");
            //RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


        }else {
            showLongToast("Error, please see the log..");
        }
    }
}
