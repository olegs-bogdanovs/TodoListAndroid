package com.example.olegsbogdanovs.todolist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;


public class TodoListActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}
