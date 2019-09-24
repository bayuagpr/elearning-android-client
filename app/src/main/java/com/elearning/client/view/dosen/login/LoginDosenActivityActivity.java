package com.elearning.client.view.dosen.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.elearning.client.R;
import com.elearning.client.view.BaseLoginActivity;
import com.elearning.client.view.auth.AuthStartActivityActivity;
import com.elearning.client.view.dosen.signup.SignupDosenActivityActivity;
import com.elearning.client.view.mahasiswa.login.LoginMahasiswaActivityActivity;
import com.elearning.client.view.main.MainActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginDosenActivityActivity extends BaseLoginActivity implements  Validator.ValidationListener {

    @BindView(R.id.username_input)
    @NotEmpty(message = "Email tidak boleh kosong")
    @Email(message = "Bukan alamat email")
    EditText usernameInput;
    @BindView(R.id.username_icon) ImageView usernameIcon;

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
    }

    @Override
    public void onBackPressed(){
        Intent intentDosen = new Intent(LoginDosenActivityActivity.this, AuthStartActivityActivity.class);
        startActivity(intentDosen);
    }

    @Override
    public void onValidationSucceeded() {
        Intent intentDosen = new Intent(LoginDosenActivityActivity.this, MainActivity.class);
        startActivity(intentDosen);
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
