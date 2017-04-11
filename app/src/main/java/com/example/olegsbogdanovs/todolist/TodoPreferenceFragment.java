package com.example.olegsbogdanovs.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;



public class TodoPreferenceFragment extends PreferenceFragment{
    public static final String RESPONSE_THEME_PREFERENCE_CHANGED = "pref_changed";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.todo_preferences);

        Preference themePreference = findPreference("themeType");
        themePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d("TEST", "Preference Changed");
                Intent intent = new Intent();
                intent.putExtra(RESPONSE_THEME_PREFERENCE_CHANGED, true);
                getActivity().setResult(Activity.RESULT_OK, intent);
                return true;
            }
        });
    }


}
