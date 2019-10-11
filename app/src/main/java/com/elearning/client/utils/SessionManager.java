package com.elearning.client.utils;

import android.content.SharedPreferences;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.elearning.client.view.auth.AuthStartActivityActivity;


public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidRetrofitLogin";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_USERNAME = "username";

    // Id(make variable public to access from outside)
    public static final String KEY_ID = "id";

    public static final String KEY_TOKEN = "token";

    public static final String KEY_NAMA = "nama";

    public static final String KEY_ROLE = "role";


    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String token) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_TOKEN, token);
        String tokenReal = token.substring(7);
        JWT jwt = new JWT(tokenReal);
        String subject = jwt.getSubject();
        String idUser = jwt.getClaim("id").asString();
        String namaUser = jwt.getClaim("nama").asString();
        String role = jwt.getClaim("role").asString();
        editor.putString(KEY_USERNAME, subject);
        editor.putString(KEY_ID, idUser);
        editor.putString(KEY_NAMA, namaUser);
        editor.putString(KEY_ROLE, role);
        // commit changes
        editor.commit();
    }



    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, AuthStartActivityActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }


    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, AuthStartActivityActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isDosen() {
        return pref.getString(KEY_ROLE, null).contains("Dosen");
    }

    public boolean isMahasiswa() {
        return pref.getString(KEY_ROLE, null).contains("Mahasiswa");
    }

    public String getKeyUsername() {
        return pref.getString(KEY_USERNAME, null);
    }

    public String getKeyNama() {
        return pref.getString(KEY_NAMA, null);
    }

    public String getKeyId() {
        return pref.getString(KEY_ID, null);
    }

    public String getKeyToken() {
        return pref.getString(KEY_TOKEN, null);
    }

}

