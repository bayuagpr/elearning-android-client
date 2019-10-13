package com.elearning.client.view.dosen.materi.editor;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.elearning.client.R;
import com.elearning.client.model.Kelas;
import com.elearning.client.model.Materi;
import com.elearning.client.network.response.UploadFileResponse;
import com.elearning.client.utils.SessionManager;

import com.elearning.client.view.BaseActivity;
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


public class MateriActivity extends BaseActivity implements PickiTCallbacks, MateriView, EasyPermissions.PermissionCallbacks{

    private static final String TAG = "Materi Activity";
    MateriPresenter presenter;
    ProgressDialog progressDialog;
    SessionManager session;
    private static final int REQUEST_DIR_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    PickiT pickiT;
    String id, idKelas, judul_materi, deskripsi_materi, namaDosen, attachmentMateri;
    MultipartBody.Part fileToUpload;

    @BindView(R.id.judulMateri)
    EditText judulMateri;
    @BindView(R.id.deskripsiMateri)
    EditText deskripsiMateri;
    @BindView(R.id.berkasDinilaiTv)
    TextView uploadMateri;
    @BindView(R.id.activityMainPdfViewTv)
    TextView lihatMateri;
    @BindView(R.id.activityMainPdfViewCv)
    MaterialCardView lihatMateriLayout;
    @BindView(R.id.berkasDinilaiCv)
    MaterialCardView uploadCard;
    @BindView(R.id.content_simpan_materi)
    LinearLayout content_simpan;
    @BindView(R.id.content_update_materi)
    LinearLayout content_update;
    @BindView(R.id.fileUploaded)
    TextView fileUploaded;
    @BindView(R.id.fileUpdate)
    TextView fileUpdate;
    @BindView(R.id.changePdfCv)
    MaterialCardView contentGantiMateri;
    @BindView(R.id.changePdfTv)
    TextView gantiMateri;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi_dosen);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbarKelas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Simpan Soal");
        session = new SessionManager(this);
        namaDosen = session.getKeyUsername();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        presenter = new MateriPresenter(this);
        //container = findViewById(R.id.activityMainPdfView);
        pickiT = new PickiT(this, this);
        initDataIntent();
        setTextEditor();
        uploadMateri.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

            try {
                startActivityForResult(Intent.createChooser(intent, "Select Your .pdf File"), REQUEST_DIR_CODE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(MateriActivity.this, "Please Install a File Manager",Toast.LENGTH_SHORT).show();
            }
        });
        lihatMateri.setOnClickListener(v -> lihatMateriPDF());
        gantiMateri.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

            try {
                startActivityForResult(Intent.createChooser(intent, "Select Your .pdf File"), REQUEST_DIR_CODE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(MateriActivity.this, "Please Install a File Manager",Toast.LENGTH_SHORT).show();
            }
        });

    }

    void lihatMateriPDF(){
        Intent intent = new Intent(MateriActivity.this, PDFActivity.class);
        intent.putExtra("attachment_materi", attachmentMateri);
        startActivity(intent);
    }

    @OnClick(R.id.saveBtnMateri) void simpan() {
        Materi materi = new Materi();
        materi.setJudul(judulMateri.getText().toString());
        materi.setDeskripsi(deskripsiMateri.getText().toString());
        materi.setAttachment(attachmentMateri);
        Kelas kelas =  new Kelas(idKelas);
        kelas.setId(idKelas);
        materi.setKelas(kelas);
        presenter.saveMateri(
                session.getKeyToken(),
                materi

        );

    }

    @OnClick(R.id.updateMateri) void update() {
        Materi materi = new Materi();
        materi.setJudul(judulMateri.getText().toString());
        materi.setDeskripsi(deskripsiMateri.getText().toString());
        materi.setAttachment(attachmentMateri);
        Kelas kelas =  new Kelas(idKelas);
        kelas.setId(idKelas);
        materi.setKelas(kelas);
        presenter.updateMateri(
                session.getKeyToken(),
                id,
                materi        );
    }

    @OnClick(R.id.hapusMateri) void hapus() {
        presenter.deleteMateri(
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
    public void handleFileUpload(UploadFileResponse uploadFileResponse) {
        attachmentMateri= uploadFileResponse.getFileDownloadUri();
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
        id = intent.getStringExtra("id");
        judul_materi = intent.getStringExtra("judul_materi");
        deskripsi_materi = intent.getStringExtra("deskripsi_materi");
        idKelas = intent.getStringExtra("id_kelas");
        attachmentMateri = intent.getStringExtra("attachment_materi");

    }

    private void setTextEditor() {
        if (id != null) {
            getSupportActionBar().setTitle("Update materi");

            judulMateri.setText(judul_materi);
            deskripsiMateri.setText(deskripsi_materi);

            //container.setVisibility(View.VISIBLE);
            lihatMateriLayout.setVisibility(View.VISIBLE);
            contentGantiMateri.setVisibility(View.VISIBLE);
            fileUpdate.setVisibility(View.VISIBLE);
            uploadCard.setVisibility(View.GONE);
            fileUploaded.setVisibility(View.GONE);
            content_update.setVisibility(View.VISIBLE);
            content_simpan.setVisibility(View.GONE);
        } else {
            getSupportActionBar().setTitle("Simpan materi");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DIR_CODE && resultCode == Activity.RESULT_OK&& data != null && data.getData() != null) {
            uri = data.getData();
            if (EasyPermissions.hasPermissions(MateriActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                progressDialog.show();
                //String filePath = getRealPathFromURIPath(uri, SoalActivity.this);
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
            } else {
                EasyPermissions.requestPermissions(MateriActivity.this, "This app needs access to your file storage so that it can read photos.", READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
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

            presenter.uploadMateri(session.getKeyToken(),fileToUpload);
        }else {
            showLongToast("Error, please see the log..");
        }
    }
}
