package com.example.olegsbogdanovs.todolist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.olegsbogdanovs.todolist.model.Todo;
import com.example.olegsbogdanovs.todolist.model.TodoListDao;

import java.util.UUID;


public class TodoDetailedFragment extends Fragment {

    private Todo mTodo;
    private EditText mTitleField;
    private EditText mDescriptionField;
    private RadioGroup mRadioGroup;
    private static final String TODO_ID = "todo_id";
    private static final String RED = "red";
    private static final String GREEN = "green";
    private static final String YELLOW = "yellow";


    public TodoDetailedFragment createInstance(UUID todoId){
        Bundle bundle = new Bundle();
        bundle.putSerializable(TODO_ID, todoId);
        TodoDetailedFragment fragment = new TodoDetailedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID todoId = (UUID)getArguments().getSerializable(TODO_ID);
        mTodo = TodoListDao.get(getActivity()).getTodo(todoId);
    }

    @Override
    public void onPause() {
        super.onPause();
        TodoListDao.get(getActivity()).updateTodo(mTodo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_todo, container, false);

        mTitleField = (EditText)view.findViewById(R.id.todo_title);
        mTitleField.setText(mTodo.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTodo.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        mDescriptionField = (EditText)view.findViewById(R.id.todo_description);
        mDescriptionField.setText(mTodo.getDescription());
        mDescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTodo.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mRadioGroup = (RadioGroup)view.findViewById(R.id.todo_radiogroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });


        return view;
    }
}
