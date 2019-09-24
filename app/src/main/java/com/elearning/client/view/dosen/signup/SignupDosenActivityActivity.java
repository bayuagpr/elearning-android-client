package com.elearning.client.view.dosen.signup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;

import com.elearning.client.R;
import com.elearning.client.view.BaseLoginActivity;
import com.elearning.client.view.dosen.login.LoginDosenActivityActivity;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupDosenActivityActivity extends BaseLoginActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_dosen_activity);
        ButterKnife.bind(this);
        toSignIn.setOnClickListener(v -> {
            Intent intentDosen = new Intent(SignupDosenActivityActivity.this, LoginDosenActivityActivity.class);
            startActivity(intentDosen);
        });

    }

}
