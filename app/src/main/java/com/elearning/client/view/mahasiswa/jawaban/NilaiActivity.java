package com.elearning.client.view.mahasiswa.jawaban;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
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
import com.elearning.client.model.Mahasiswa;
import com.elearning.client.model.Soal;
import com.elearning.client.network.response.UploadFileResponse;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.view.BaseActivity;
import com.google.android.material.card.MaterialCardView;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;


public class NilaiActivity extends BaseActivity implements PickiTCallbacks, NilaiView, EasyPermissions.PermissionCallbacks{

    private static final String TAG = "Nilai Activity";
    NilaiPresenter presenter;
    ProgressDialog progressDialog;
    SessionManager session;
    private static final int REQUEST_DIR_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    PickiT pickiT;
    String id, isi_jawaban , status_kumpul, nama_mahasiswa , tipesoal,nim_mahasiswa , id_soal , attachment_materi,namaDosen,nilai,komentar;
    MultipartBody.Part fileToUpload;
    Boolean ternilai,status_exist;
    @BindView(R.id.deskripsiDinilaiEt)
    EditText descDinilai;
    @BindView(R.id.nilaiEt)
    EditText nilaiInput;
    @BindView(R.id.komentarEt)
    EditText komentarInput;
    @BindView(R.id.berkasLihatTv)
    TextView lihatBerkasDinilai;
    @BindView(R.id.berkasSimpanTv)
    TextView simpanBerkas;
    @BindView(R.id.berkasGantiTv)
    TextView gantiBerkas;
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
    @BindView(R.id.fileSimpan)
    TextView fileUploaded;
    @BindView(R.id.fileUpdate)
    TextView fileUpdate;
    private Uri uri;
    Date dueDate,last_modified;
    private SimpleDateFormat mFormatter;

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
        mFormatter = new SimpleDateFormat(" dd MMMM yyyy hh:mm aa");
        initDataIntent();
        setTextEditor();
        lihatBerkasDinilai.setOnClickListener(v -> lihatMateriPDF());
        simpanBerkas.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

            try {
                startActivityForResult(Intent.createChooser(intent, "Select Your .pdf File"), REQUEST_DIR_CODE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(NilaiActivity.this, "Please Install a File Manager",Toast.LENGTH_SHORT).show();
            }
        });
        gantiBerkas.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

            try {
                startActivityForResult(Intent.createChooser(intent, "Select Your .pdf File"), REQUEST_DIR_CODE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(NilaiActivity.this, "Please Install a File Manager",Toast.LENGTH_SHORT).show();
            }
        });

    }

    void lihatMateriPDF(){
        Intent intent = new Intent(NilaiActivity.this, PDFActivity.class);
        intent.putExtra("attachment_materi", attachment_materi);
        startActivity(intent);
    }

    @OnClick(R.id.saveBtnNilai) void simpan() {
        Hasil materi = new Hasil();
        materi.setAttachment(attachment_materi);
        materi.setIsiJawaban(descDinilai.getText().toString());
        Mahasiswa mhs = new Mahasiswa();
        mhs.setNim(session.getKeyId());
        Soal soal = new Soal();
        soal.setId(id_soal);
        materi.setMahasiswa(mhs);
        materi.setSoal(soal);
        Date tanggalSekarang = new Date();

        if(tanggalSekarang.before(dueDate) || tanggalSekarang.compareTo(dueDate) == 0){
            materi.setStatus("Early");
        } else {
            materi.setStatus("Late");
        }
        //long dateSer = tanggalSekarang.getTime() / 1000L;
        materi.setLastModified(tanggalSekarang);
        presenter.simpanJawaban(
                session.getKeyToken(),
                materi        );

    }

    @OnClick(R.id.updateNilai) void update() {
        Hasil materi = new Hasil();
        materi.setAttachment(attachment_materi);
        materi.setIsiJawaban(descDinilai.getText().toString());
        Date tanggalSekarang = new Date();
        Log.d(TAG, "update: "+dueDate+" "+tanggalSekarang);
        if(tanggalSekarang.before(dueDate) || tanggalSekarang.compareTo(dueDate) == 0){
            materi.setStatus("Early");
            Log.d(TAG, "early: "+dueDate+" "+tanggalSekarang);
        } else {
            materi.setStatus("Late");
            Log.d(TAG, "late: "+dueDate+" "+tanggalSekarang);
        }
        //long dateSer = tanggalSekarang.getTime() / 1000L;
        materi.setLastModified(tanggalSekarang);
        presenter.updateJawaban(
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
    public void handleFileUpload(UploadFileResponse uploadFileResponse) {
        attachment_materi= uploadFileResponse.getFileDownloadUri();
        fileUploaded.setText(uploadFileResponse.getFileName());
        fileUpdate.setText(uploadFileResponse.getFileName());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        pickiT.deleteTemporaryFile();
        presenter.detachView();

    }

    private void initDataIntent() {
        Intent intent= getIntent();
        id = intent.getStringExtra("idHasil");
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
        status_exist = intent.getBooleanExtra("status_exist",false);
        dueDate = (Date)intent.getSerializableExtra("due_date");
        last_modified = (Date)intent.getSerializableExtra("last_modified");
    }

    private void setTextEditor() {
        if(status_exist){
            if (ternilai) {
                getSupportActionBar().setTitle("Nilai "+tipesoal);
                nilaiInput.setText(nilai);
                komentarInput.setText(komentar);
                nilaiInput.setEnabled(false);
                komentarInput.setEnabled(false);
                descDinilai.setText(isi_jawaban);
                identitasDinilaiTv.setText("Nama: "+session.getKeyNama()+"\n"+"NIM: "+session.getKeyId());
                statusHasilTv.setText("Tipe Soal: "+tipesoal+"\n"+"Dikumpul tanggal: "+"\n"+mFormatter.format(last_modified)+"\n"+"Status Pengumpulan: "+status_kumpul);
                //container.setVisibility(View.VISIBLE);
                berkasLihat.setVisibility(View.VISIBLE);
                content_simpan.setVisibility(View.GONE);
                berkasGanti.setVisibility(View.GONE);
                berkasSimpan.setVisibility(View.GONE);
            } else {
                nilaiInput.setVisibility(View.GONE);
                komentarInput.setVisibility(View.GONE);
                descDinilai.setText(isi_jawaban);
                descDinilai.setEnabled(true);
                identitasDinilaiTv.setText("Nama: "+session.getKeyNama()+"\n"+"NIM: "+session.getKeyId());
                statusHasilTv.setText("Tipe Soal: "+tipesoal+"\n"+"Dikumpul tanggal: "+"\n"+mFormatter.format(last_modified)+"\n"+"Status Pengumpulan: "+status_kumpul);
                getSupportActionBar().setTitle("Update Jawaban");
                content_update.setVisibility(View.VISIBLE);
                fileUpdate.setVisibility(View.VISIBLE);
                content_simpan.setVisibility(View.GONE);
                berkasLihat.setVisibility(View.VISIBLE);
                berkasGanti.setVisibility(View.VISIBLE);
                berkasSimpan.setVisibility(View.GONE);
            }
        }else{
            nilaiInput.setVisibility(View.GONE);
            komentarInput.setVisibility(View.GONE);
            descDinilai.setEnabled(true);
            identitasDinilaiTv.setText("Nama: "+session.getKeyNama()+"\n"+"NIM: "+session.getKeyId());
            statusHasilTv.setText("Tipe Soal: "+tipesoal);
            getSupportActionBar().setTitle("Simpan Jawaban");
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
            presenter.uploadHasil(session.getKeyToken(),fileToUpload);

        }else {
            showLongToast("Error, please see the log..");
        }
    }
}
