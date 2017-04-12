package com.example.olegsbogdanovs.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class TodoListActivity extends AppCompatActivity {


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
        setContentView(R.layout.todo_fragment_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.todo_fragment_container);

        if (fragment == null){
            fragment = new TodoListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.todo_fragment_container, fragment)
                    .commit();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK){
            return;
        }

        if (requestCode == TodoListFragment.REQUEST_THEME_PREFERENCE_CHANGED){
            if (data == null){
                return;
            }
            startActivity(new Intent(this, TodoListActivity.class));
            finish();
        }


        super.onActivityResult(requestCode, resultCode, data);

    }
}
