package com.example.olegsbogdanovs.todolist;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by olegs.bogdanovs on 3/20/2017.
 */

public class TodoPreferenceFragment extends PreferenceFragment{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.todo_preferences);

        Preference themePreference = findPreference("themeType");
        themePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d("TEST", "Preference Changed");
                return true;
            }
        });
    }


}
