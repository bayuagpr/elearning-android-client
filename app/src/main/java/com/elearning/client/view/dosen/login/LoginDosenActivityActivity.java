package com.elearning.client.view.dosen.login;

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
import com.elearning.client.model.User;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.view.BaseLoginActivity;
import com.elearning.client.view.dosen.MainDosenActivity;
import com.elearning.client.view.auth.AuthStartActivityActivity;
import com.elearning.client.view.dosen.signup.SignupDosenActivityActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginDosenActivityActivity extends BaseLoginActivity implements  Validator.ValidationListener, LoginView {

    @BindView(R.id.username_input)
    @NotEmpty(message = "Email tidak boleh kosong")
    @Email(message = "Bukan alamat email")
    EditText usernameInput;
    @BindView(R.id.username_icon) ImageView usernameIcon;
    LoginPresenter presenter;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    @BindView(R.id.pass)
    @NotEmpty(message = "Sandi tidak boleh kosong")
    EditText pass;
    @BindView(R.id.icon) ImageView icon;
    @BindView(R.id.toSignUp) TextView toSignUp;
    @BindView(R.id.signingIn) TextView signingIn;
    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_dosen_activity);
        ButterKnife.bind(this);
        toSignUp.setOnClickListener(v -> {
            Intent intentDosen = new Intent(LoginDosenActivityActivity.this, SignupDosenActivityActivity.class);
            startActivity(intentDosen);
        });
        signingIn.setOnClickListener(v -> {
        validator.validate();
        });
        validator = new Validator(this);
        validator.setValidationListener(this);
        presenter = new LoginPresenter(this);
        sessionManager = new SessionManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
    }

    @Override
    public void onBackPressed(){
        Intent intentDosen = new Intent(LoginDosenActivityActivity.this, AuthStartActivityActivity.class);
        startActivity(intentDosen);
    }
    private void userLogin() {

        String email = usernameInput.getText().toString();
        String password = pass.getText().toString();

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        presenter.loginAuth(
               user
        );


    }
    @Override
    public void onValidationSucceeded() {
        userLogin();
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
    public void statusSuccess(String tokenResponse) {

        sessionManager.createLoginSession(
                "Bearer " + tokenResponse
        );
        finish();
        Intent intent = new Intent(LoginDosenActivityActivity.this, MainDosenActivity.class);
        startActivity(intent);
    }

    @Override
    public void statusError(String message) {
        Log.d("Error", "statusError: " + message);
    }
}
