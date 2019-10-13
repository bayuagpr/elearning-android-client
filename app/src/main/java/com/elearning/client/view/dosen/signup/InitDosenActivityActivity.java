package com.elearning.client.view.dosen.signup;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elearning.client.R;
import com.elearning.client.model.Dosen;
import com.elearning.client.model.Role;
import com.elearning.client.model.User;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.view.BaseActivity;
import com.elearning.client.view.BaseLoginActivity;
import com.elearning.client.view.dosen.MainDosenActivity;
import com.elearning.client.view.dosen.login.LoginDosenActivityActivity;
import com.elearning.client.view.dosen.soal.editor.SoalActivity;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InitDosenActivityActivity extends BaseActivity implements Validator.ValidationListener, SignupView {

    @BindView(R.id.id_input)
    @NotEmpty(message = "Nama tidak boleh kosong")
    EditText idInput;
    @BindView(R.id.passTempat)
    @NotEmpty(message = "Tempat lahir tidak boleh kosong")
    EditText usernameInput;
    @BindView(R.id.passTanggal)
    TextView pass;
    @NotEmpty(message = "Alamat tidak boleh kosong")
    @BindView(R.id.passConfirm) EditText passConfirm;
    @BindView(R.id.signingUp) TextView signingUp;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    SignupPresenter presenter;
    Validator validator;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    Date tanggalLahir;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_dosen_activity);
        ButterKnife.bind(this);
        initDataIntent();
        dateFormatter = new SimpleDateFormat(" dd MMMM yyyy");
        pass.setOnClickListener(v -> {
            showDateDialog();
        });
        validator = new Validator(this);
        validator.setValidationListener(this);
        signingUp.setOnClickListener(v -> validator.validate());
        presenter = new SignupPresenter(this);
        sessionManager = new SessionManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
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
                pass.setText(dateFormatter.format(newDate.getTime()));
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
    private void userSignup() {


        long dateSer = tanggalLahir.getTime() / 1000L;
        Dosen user = new Dosen();
        user.setNidn(sessionManager.getKeyId());
        user.setNama(idInput.getText().toString());
        user.setTempat_lahir(usernameInput.getText().toString());
        user.setTanggal_lahir(dateSer);
        user.setAlamat(passConfirm.getText().toString());
        presenter.submitDosen(
                sessionManager.getKeyToken(),user
        );
    }
    @Override
    public void statusSuccess(String tokenResponse) {
        sessionManager.createLoginSession(
                "Bearer " + tokenResponse
        );
        Intent intent = new Intent(InitDosenActivityActivity.this, MainDosenActivity.class);
        startActivity(intent);

    }

    @Override
    public void statusError(String message) {
        Log.d("Error", "statusError: " + message);
    }

    @Override
    public void afterSubmitDosen() {

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


