package com.example.olegsbogdanovs.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;


public class TodoDetailedActivity extends AppCompatActivity {
    public static final String EXTRA_TODO_ID = "com.example.olegsbogdanovs.todolist.crime_id";

    public static Intent createIntent(Context context, UUID todoId){
        Intent intent = new Intent(context, TodoDetailedActivity.class);
        intent.putExtra(EXTRA_TODO_ID, todoId);
        return intent;
    }

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

        UUID todoId = (UUID)getIntent().getSerializableExtra(EXTRA_TODO_ID);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.todo_fragment_container);

        if (fragment == null) {
            fragment = new TodoDetailedFragment().createInstance(todoId);
            fragmentManager.beginTransaction()
                    .add(R.id.todo_fragment_container, fragment)
                    .commit();
        }
    }


}
