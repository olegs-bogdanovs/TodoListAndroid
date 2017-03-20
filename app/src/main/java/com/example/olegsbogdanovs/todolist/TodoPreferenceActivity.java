package com.example.olegsbogdanovs.todolist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by olegs.bogdanovs on 3/20/2017.
 */

public class TodoPreferenceActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        switch (SP.getString("themeType", "1")){
            case "1":
                setTheme(R.style.AppThemeDark);
                break;
            case "2":
                setTheme(R.style.AppThemeBlue);
                break;
            case "3":
                setTheme(R.style.AppThemeRed);
                break;
        }
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new TodoPreferenceFragment())
                .commit();
    }
}
