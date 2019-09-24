package com.elearning.client.view;


import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.elearning.client.R;
import com.elearning.client.network.NetworkEvent;
import com.elearning.client.view.auth.AuthStartActivityActivity;


public class BaseLoginActivity extends AppCompatActivity {
    /*

     * Step 1: we register the BaseActivity as subscriber

     * and specify what needs to be done in case of

     * NO_INTERNET, NO_RESPONSE, UNAUTHORISED error responses

     */

    @Override

    protected void onResume() {
        super.onResume();
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
                            displayErrorDialog(getString(R.string.login_gagal_title),
                                    getString(R.string.login_gagal_desc));
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

