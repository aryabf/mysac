package com.finalproject.mysac.data.local.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.finalproject.mysac.data.model.User;

public class SharedPreferencesManager {

    private static final String PREF_NAME = "mysac_pref";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_LOGGED_USERNAME = "loggedUsername";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setLoggedUsername(String loggedUsername) {
        editor.putString(KEY_LOGGED_USERNAME, loggedUsername);
        editor.apply();
    }

    public String getLoggedUsername() {
        return sharedPreferences.getString(KEY_LOGGED_USERNAME, "blank");
    }

}
