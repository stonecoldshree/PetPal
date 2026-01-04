package com.yourcompany.petcare;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class App extends Application {

    private static App instance;
    private static SharedPreferences prefs;

    private static final String PREF_NAME = "petcare_prefs";
    private static final String KEY_EMAIL = "current_user_email";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    // Save login persistently
    public static void setCurrentUserEmail(String email) {
        prefs.edit().putString(KEY_EMAIL, email).apply();
    }

    // Retrieve saved login
    public static String getCurrentUserEmail() {
        return prefs.getString(KEY_EMAIL, null);
    }

    // Optional: Logout function
    public static void logout() {
        prefs.edit().remove(KEY_EMAIL).apply();
    }
}
