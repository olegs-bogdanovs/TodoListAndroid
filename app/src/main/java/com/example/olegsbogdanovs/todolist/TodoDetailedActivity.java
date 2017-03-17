package com.example.olegsbogdanovs.todolist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


public class TodoDetailedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_detailed_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.detailed_todo_fragment_container);

        if (fragment == null) {
            fragment = new TodoDetailedFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.detailed_todo_fragment_container, fragment)
                    .commit();
        }
    }


}
