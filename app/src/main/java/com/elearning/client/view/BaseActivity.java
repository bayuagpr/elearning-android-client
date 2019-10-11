package com.elearning.client.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.elearning.client.R;
import com.elearning.client.network.NetworkEvent;
import com.elearning.client.utils.SessionManager;


public class BaseActivity extends AppCompatActivity {
    SessionManager session;
    /*

     * Step 1: we register the BaseActivity as subscriber

     * and specify what needs to be done in case of

     * NO_INTERNET, NO_RESPONSE, UNAUTHORISED error responses

     */

    @Override

    protected void onResume() {
        super.onResume();
        session = new SessionManager(this);
        NetworkEvent.getInstance().register(this,

                networkState -> {

                    switch (networkState) {
                        case NO_INTERNET:
                            displayErrorDialog(getString(R.string.generic_no_internet_title),
                                    getString(R.string.generic_no_internet_desc));
                            break;

                        case NO_RESPONSE:
                            displayErrorDialog(getString(R.string.generic_http_error_title),
                                    getString(R.string.generic_http_error_desc));
                            break;

                        case UNAUTHORISED:
                            //redirect to login screen - if session expired
                            Toast.makeText(getApplicationContext(), R.string.error_login_expired, Toast.LENGTH_LONG).show();
                            session.logoutUser();
                            break;
                    }

                });

    }





    /*

     * Step 2: we unregister the activity once it is

     * finished.

     */

    @Override

    protected void onStop() {
        super.onStop();
        NetworkEvent.getInstance().unregister(this);
    }

    /*

     * Step 3: We are just displaying an error

     * dialog here! But you configure whatever

     * you want

     */
    public void displayErrorDialog(String title, String desc) {

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(desc)
                .setCancelable(false)
                .setPositiveButton("Ok",
                        (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })
                .show();

    }

}

