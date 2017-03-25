package com.example.olegsbogdanovs.todolist;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.olegsbogdanovs.todolist.model.Todo;
import com.example.olegsbogdanovs.todolist.model.TodoListDao;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.UUID;


public class TodoDetailedFragment extends Fragment {

    private Todo mTodo;
    private EditText mTitleField;
    private EditText mDescriptionField;
    private RadioGroup mRadioGroup;
    private ImageView mPhotoView;
    private Button mPhotoButton;
    private File mPhotoFile;
    private static final int REQUEST_PHOTO = 0;
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
        mPhotoFile = TodoListDao.get(getActivity()).getPhotoFile(mTodo);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        TodoListDao.get(getActivity()).updateTodo(mTodo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_todo, container, false);

        PackageManager packageManager = getActivity().getPackageManager();

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
        switch (mTodo.getColor()){
            case GREEN:
                mRadioGroup.check(R.id.todo_radioButton_green);
                break;
            case RED:
                mRadioGroup.check(R.id.todo_radioButton_red);
                break;
            case YELLOW:
                mRadioGroup.check(R.id.todo_radioButton_yellow);
                break;
            default:
                mRadioGroup.check(R.id.todo_radioButton_yellow);
        }

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.todo_radioButton_green:
                        mTodo.setColor(GREEN);
                        break;
                    case R.id.todo_radioButton_red:
                        mTodo.setColor(RED);
                        break;
                    case R.id.todo_radioButton_yellow:
                        mTodo.setColor(YELLOW);
                        break;
                    default:
                        mTodo.setColor(YELLOW);
                }
            }
        });

        mPhotoButton = (Button) view.findViewById(R.id.todo_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePicture = mPhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePicture);

        if (canTakePicture) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) view.findViewById(R.id.todo_photo);
        updatePhotoView();

        return view;
    }

    private void updatePhotoView() {
        Picasso.with(getActivity()).load(mPhotoFile).resize(200,200).skipMemoryCache().centerCrop().into(mPhotoView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_PHOTO){
            updatePhotoView();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_todo_detailed_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_delete_todo:
                TodoListDao.get(getActivity()).removeTodo(mTodo);
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
