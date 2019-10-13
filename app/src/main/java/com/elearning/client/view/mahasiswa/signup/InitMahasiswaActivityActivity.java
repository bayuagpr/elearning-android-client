package com.elearning.client.view.mahasiswa.signup;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.elearning.client.R;
import com.elearning.client.model.Dosen;
import com.elearning.client.model.Jurusan;
import com.elearning.client.model.Mahasiswa;
import com.elearning.client.model.User;
import com.elearning.client.network.response.JurusanResponse;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.view.BaseActivity;
import com.elearning.client.view.BaseLoginActivity;
import com.elearning.client.view.dosen.MainDosenActivity;
import com.elearning.client.view.dosen.signup.SignupPresenter;
import com.elearning.client.view.dosen.signup.SignupView;
import com.elearning.client.view.mahasiswa.MainMahasiswaActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InitMahasiswaActivityActivity extends BaseActivity implements Validator.ValidationListener, SignupMahasiswaView {

    @BindView(R.id.id_input)
    @NotEmpty(message = "Nama tidak boleh kosong")
    EditText nama;
    @BindView(R.id.passTempat)
    @NotEmpty(message = "Tempat lahir tidak boleh kosong")
    EditText tempatLahir;
    @BindView(R.id.passTanggal)
    TextView tanggaLahir;
    @NotEmpty(message = "Fakultas tidak boleh kosong")
    @BindView(R.id.pass) TextView fakultas;
    @NotEmpty(message = "Alamat tidak boleh kosong")
    @BindView(R.id.passConfirm) EditText alamat;
    @BindView(R.id.signingUp) TextView signingUp;
    @BindView(R.id.matkul)
    Spinner s_nama;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    SignupMahasiswaPresenter presenter;
    Validator validator;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    Date tanggalLahir;
    SpinnerJurusanAdapter adapter;
    String email,password, namaJurusan, jurusanId, fakultasNama, fakultasId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_mahasiswa_activity);
        ButterKnife.bind(this);
        initDataIntent();
        dateFormatter = new SimpleDateFormat(" dd MMMM yyyy");
        tanggaLahir.setOnClickListener(v -> {
            showDateDialog();
        });
        validator = new Validator(this);
        validator.setValidationListener(this);
        signingUp.setOnClickListener(v -> validator.validate());
        presenter = new SignupMahasiswaPresenter(this);
        sessionManager = new SessionManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        presenter.getListJurusan(sessionManager.getKeyToken());
        s_nama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                namaJurusan = ((TextView) view.findViewById(R.id.nama_spinner)).getText().toString();
                jurusanId = ((TextView) view.findViewById(R.id.matkul_id_spinner)).getText().toString();
                fakultasNama = ((TextView) view.findViewById(R.id.fakultas_spinner)).getText().toString();
                fakultas.setText(fakultasNama);

                Log.d("jurusan", "onCreate: "+namaJurusan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tanggalLahir = newDate.getTime();
                tanggaLahir.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
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
    public void setListJurusan(JurusanResponse mataKuliahResponse) {
        adapter = new SpinnerJurusanAdapter(this, R.layout.spinner_matkul,
                mataKuliahResponse.getJurusanList());
        s_nama.setAdapter(adapter);


    }

    private void userSignup() {


        long dateSer = tanggalLahir.getTime() / 1000L;
        Mahasiswa user = new Mahasiswa();
        user.setNim(sessionManager.getKeyId());
        user.setNama(nama.getText().toString());
        user.setTempat_lahir(tempatLahir.getText().toString());
        user.setTanggal_lahir(dateSer);
        user.setAlamat(alamat.getText().toString());
        Jurusan jurusan = new Jurusan();
        jurusan.setId(jurusanId);
        user.setJurusan(jurusan);
        presenter.submitMahasiswa(
                sessionManager.getKeyToken(),user
        );
    }
    @Override
    public void statusSuccess(String tokenResponse) {
        sessionManager.createLoginSession(
                "Bearer " + tokenResponse
        );
        Intent intent = new Intent(InitMahasiswaActivityActivity.this, MainMahasiswaActivity.class);
        startActivity(intent);

    }

    @Override
    public void statusError(String message) {
        Log.d("Error", "statusError: " + message);
    }

    @Override
    public void afterSubmitMahasiswa() {

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        presenter.loginAuth(
                user
        );
    }

    private void initDataIntent() {

        Intent intent= getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

    }
    @Override
    public void onValidationSucceeded() {
        userSignup();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}


