package com.elearning.client.view.mahasiswa.signup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;

import com.elearning.client.R;
import com.elearning.client.view.BaseLoginActivity;
import com.elearning.client.view.mahasiswa.login.LoginMahasiswaActivityActivity;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupMahasiswaActivityActivity extends BaseLoginActivity {

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
    @BindView(R.id.passConfirm)
    @NotEmpty(message = "Konfirmasi Password tidak boleh kosong")
    @ConfirmPassword
    EditText passConfirm;
    @BindView(R.id.iconConfirm) ImageView iconConfirm;
    @BindView(R.id.toSignIn) TextView toSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_mahasiswa_activity);
        ButterKnife.bind(this);
        toSignIn.setOnClickListener(v -> {
            Intent intentDosen = new Intent(SignupMahasiswaActivityActivity.this, LoginMahasiswaActivityActivity.class);
            startActivity(intentDosen);
        });
    }

}
