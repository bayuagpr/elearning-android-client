package com.elearning.client.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.elearning.client.R;
import com.elearning.client.network.NetworkEvent;
import com.elearning.client.utils.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;


public class BaseActivity extends AppCompatActivity {
    SessionManager session;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_HIGH));
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        //Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        Log.d(TAG, "Subscribing to weather topic");
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d(TAG, msg);
                        //Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }
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

