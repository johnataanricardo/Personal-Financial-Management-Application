package info.seufinanceiro.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesService {

    private SharedPreferences preferences;

    public SharedPreferencesService(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void writeToken(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public String getToken() {
        return preferences.getString("token", "");
    }

}
