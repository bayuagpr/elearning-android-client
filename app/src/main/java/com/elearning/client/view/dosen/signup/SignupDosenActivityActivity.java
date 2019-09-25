package com.elearning.client.view.dosen.signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.elearning.client.R;
import com.elearning.client.model.Role;
import com.elearning.client.model.User;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.view.BaseLoginActivity;
import com.elearning.client.view.MainActivity;
import com.elearning.client.view.dosen.login.LoginDosenActivityActivity;
import com.elearning.client.view.dosen.login.LoginPresenter;
import com.elearning.client.view.mahasiswa.login.LoginMahasiswaMahasiswaActivityActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupDosenActivityActivity extends BaseLoginActivity implements Validator.ValidationListener, SignupView {

    @BindView(R.id.id_input)
    @NotEmpty(message = "NIDN tidak boleh kosong")
    EditText idInput;
    @BindView(R.id.id_icon) ImageView idIcon;
    @BindView(R.id.username_input)
    @NotEmpty(message = "Email tidak boleh kosong")
    @Email(message = "Bukan alamat email")
    EditText usernameInput;
    @BindView(R.id.username_icon) ImageView usernameIcon;
    @BindView(R.id.pass)
    @NotEmpty(message = "Password tidak boleh kosong")
    @Password
    EditText pass;
    @BindView(R.id.icon) ImageView icon;
    @NotEmpty(message = "Konfirmasi Password tidak boleh kosong")
    @ConfirmPassword
    @BindView(R.id.passConfirm) EditText passConfirm;
    @BindView(R.id.iconConfirm) ImageView iconConfirm;
    @BindView(R.id.toSignIn) TextView toSignIn;
    @BindView(R.id.signingUp) TextView signingUp;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    SignupPresenter presenter;
    Validator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_dosen_activity);
        ButterKnife.bind(this);
        toSignIn.setOnClickListener(v -> {
            Intent intentDosen = new Intent(SignupDosenActivityActivity.this, LoginMahasiswaMahasiswaActivityActivity.class);
            startActivity(intentDosen);
        });
        signingUp.setOnClickListener(v -> validator.validate());
        validator = new Validator(this);
        validator.setValidationListener(this);
        presenter = new SignupPresenter(this);
        sessionManager = new SessionManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
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
    private void userSignup() {

        String nidn = idInput.getText().toString();
        String email = usernameInput.getText().toString();
        String password = passConfirm.getText().toString();

        User user = new User();
        user.setId(nidn);
        user.setEmail(email);
        user.setPassword(password);
        Role role = new Role();
        role.setId(2);
        user.setRole(role);
        presenter.regist(
                user
        );
    }
    @Override
    public void statusSuccess(String tokenResponse) {

        sessionManager.createLoginSession(
                "Bearer " + tokenResponse
        );
        finish();
        Intent intent = new Intent(SignupDosenActivityActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void statusError(String message) {
        Log.d("Error", "statusError: " + message);
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


