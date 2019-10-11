package com.elearning.client.view.dosen.soal.editor;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.elearning.client.R;
import com.elearning.client.model.Kelas;
import com.elearning.client.model.Soal;
import com.elearning.client.network.response.UploadFileResponse;
import com.elearning.client.utils.SessionManager;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.material.card.MaterialCardView;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;


public class SoalActivity extends AppCompatActivity implements PickiTCallbacks, SoalVIew, EasyPermissions.PermissionCallbacks{

    private static final String TAG = "Soal Activity";
    SoalPresenter presenter;
    ProgressDialog progressDialog;
    SessionManager session;
    private static final int REQUEST_DIR_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    PickiT pickiT;
    private String  id, judul_soal, deskripsi_soal, idKelas, attachmentSoal,tipeSoal,namaDosen,sDate1 ;
    MultipartBody.Part fileToUpload;
    Date dueDate;
    private SlideDateTimeListener listener;
    @BindView(R.id.judulSoal)
    EditText judulSoal;
    @BindView(R.id.descSoal)
    EditText descSoal;
    @BindView(R.id.uploadSoalTv)
    TextView uploadSoal;
    @BindView(R.id.lihatSoalTv)
    TextView lihatSoal;
    @BindView(R.id.lihatSoalCv)
    MaterialCardView lihatSoalLayout;
    @BindView(R.id.dateTv)
    TextView dateSoal;
    @BindView(R.id.dateCv)
    MaterialCardView dateSimpanLayout;
    @BindView(R.id.dateGantiTv)
    TextView dateGantiSoal;
    @BindView(R.id.dateGantiCv)
    MaterialCardView dateGantiLayout;
    @BindView(R.id.uploadSoalCv)
    MaterialCardView uploadCard;
    @BindView(R.id.content_simpan_soal)
    LinearLayout content_simpan;
    @BindView(R.id.content_update_soal)
    LinearLayout content_update;
    @BindView(R.id.fileUploadedSoal)
    TextView fileUploaded;
    @BindView(R.id.fileUpdateSoal)
    TextView fileUpdate;
    @BindView(R.id.changePdfSoalCv)
    MaterialCardView contentGantiSoal;
    @BindView(R.id.changePdfSoalTv)
    TextView gantiSoal;
    @BindView(R.id.datePickedSoal)
    TextView tanggalTampil;
    @BindView(R.id.tipeSoalSpinner)
    Spinner s_tipe;
    private Uri uri;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private SimpleDateFormat mFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soal);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbarSoal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Simpan Soal");
        session = new SessionManager(this);
        namaDosen = session.getKeyUsername();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        presenter = new SoalPresenter(this);
        //container = findViewById(R.id.activityMainPdfView);
        pickiT = new PickiT(this, this);
        mFormatter = new SimpleDateFormat(" dd MMMM yyyy hh:mm aa");
        initDataIntent();
        setSpinnerUkuran();
        setTextEditor();
        listener = new SlideDateTimeListener() {

            @Override
            public void onDateTimeSet(Date date)
            {
                dueDate = date;
                tanggalTampil.setText("Paling lambat dikumpulkan pada:"+ "\n" +mFormatter.format(date));
            }

            // Optional cancel listener
            @Override
            public void onDateTimeCancel()
            {
                Toast.makeText(SoalActivity.this,
                        "Canceled", Toast.LENGTH_SHORT).show();
            }
        };
        dateSoal.setOnClickListener(v -> {
            new SlideDateTimePicker.Builder(getSupportFragmentManager())
                    .setListener(listener)
                    .setInitialDate(new Date())
                    .setMinDate(new Date())
                    //.setMaxDate(maxDate)
                    .setIs24HourTime(true)
                    //.setTheme(SlideDateTimePicker.HOLO_DARK)
                    //.setIndicatorColor(Color.parseColor("#990000"))
                    .build()
                    .show();
        });
        dateGantiSoal.setOnClickListener(v -> {
            new SlideDateTimePicker.Builder(getSupportFragmentManager())
                    .setListener(listener)
                    .setInitialDate(new Date())
                    .setMinDate(new Date())
                    //.setMaxDate(maxDate)
                    .setIs24HourTime(true)
                    //.setTheme(SlideDateTimePicker.HOLO_DARK)
                    //.setIndicatorColor(Color.parseColor("#990000"))
                    .build()
                    .show();
        });
        uploadSoal.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

            try {
                startActivityForResult(Intent.createChooser(intent, "Select Your .pdf File"), REQUEST_DIR_CODE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(SoalActivity.this, "Please Install a File Manager",Toast.LENGTH_SHORT).show();
            }
        });
        lihatSoal.setOnClickListener(v -> lihatMateriPDF());
        gantiSoal.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

            try {
                startActivityForResult(Intent.createChooser(intent, "Select Your .pdf File"), REQUEST_DIR_CODE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(SoalActivity.this, "Please Install a File Manager",Toast.LENGTH_SHORT).show();
            }
        });

    }

    void lihatMateriPDF(){
        Intent intent = new Intent(SoalActivity.this, PDFActivity.class);
        intent.putExtra("attachment_soal", attachmentSoal);
        startActivity(intent);
    }

    @OnClick(R.id.saveBtnSoal) void simpan() {
        long dateSer = dueDate.getTime() / 1000L;
        Soal soal = new Soal();
        soal.setJudul(judulSoal.getText().toString());
        soal.setDeskripsi(descSoal.getText().toString());
        soal.setAttachment(attachmentSoal);
        soal.setDueDate(dateSer);
        soal.setTipe(s_tipe.getSelectedItem().toString());
        Kelas kelas =  new Kelas(idKelas);
        kelas.setId(idKelas);
        soal.setKelas(kelas);
        presenter.saveSoal(
                session.getKeyToken(),
                soal

        );

    }

    @OnClick(R.id.updateSoal) void update() {
        long dateSer = dueDate.getTime() / 1000L;
        Soal soal = new Soal();
        soal.setJudul(judulSoal.getText().toString());
        soal.setDeskripsi(descSoal.getText().toString());
        soal.setAttachment(attachmentSoal);
        soal.setDueDate(dateSer);
        soal.setTipe(s_tipe.getSelectedItem().toString());
        Kelas kelas =  new Kelas(idKelas);
        kelas.setId(idKelas);
        soal.setKelas(kelas);
        presenter.updateSoal(
                session.getKeyToken(),
                id,
                soal        );
    }

    @OnClick(R.id.hapusSoal) void hapus() {
        presenter.deleteSoal(
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
        attachmentSoal= uploadFileResponse.getFileDownloadUri();
        fileUploaded.setText(uploadFileResponse.getFileName());
        fileUpdate.setText(uploadFileResponse.getFileName());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        pickiT.deleteTemporaryFile();
        presenter.detachView();

    }
    private void setSpinnerUkuran() {
        ArrayAdapter<String> spinnerArray = new ArrayAdapter<String>(this, android.R.layout
                .simple_spinner_dropdown_item, getResources().getStringArray(R.array
                .tipe_soal_arrays));
        s_tipe.setAdapter(spinnerArray);
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

    private void setTextEditor() {
        if (id != null) {
            getSupportActionBar().setTitle("Update Soal");

            judulSoal.setText(judul_soal);
            descSoal.setText(deskripsi_soal);
            s_tipe.setSelection(getIndex(s_tipe, tipeSoal));
            //Log.d(TAG, "tanggal: "+mFormatter.format(dueDate));
            tanggalTampil.setText("Paling lambat dikumpulkan pada:"+ "\n" +mFormatter.format(dueDate));
            //container.setVisibility(View.VISIBLE);
            dateGantiLayout.setVisibility(View.VISIBLE);
            lihatSoalLayout.setVisibility(View.VISIBLE);
            contentGantiSoal.setVisibility(View.VISIBLE);
            fileUpdate.setVisibility(View.VISIBLE);
            lihatSoalLayout.setVisibility(View.VISIBLE);
            dateSimpanLayout.setVisibility(View.GONE);
            uploadCard.setVisibility(View.GONE);
            fileUploaded.setVisibility(View.GONE);
            content_update.setVisibility(View.VISIBLE);
            content_simpan.setVisibility(View.GONE);
        } else {
            getSupportActionBar().setTitle("Simpan Soal");
        }
    }
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DIR_CODE && resultCode == Activity.RESULT_OK&& data != null && data.getData() != null) {
            uri = data.getData();
            if (EasyPermissions.hasPermissions(SoalActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                progressDialog.show();
                //String filePath = getRealPathFromURIPath(uri, SoalActivity.this);
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
            } else {
                EasyPermissions.requestPermissions(SoalActivity.this, "This app needs access to your file storage so that it can read photos.", READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
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

            presenter.uploadSoal(session.getKeyToken(),fileToUpload);
        }else {
            showLongToast("Error, please see the log..");
        }
    }
}
