package com.example.olegsbogdanovs.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olegsbogdanovs.todolist.model.Todo;
import com.example.olegsbogdanovs.todolist.model.TodoListDao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.util.List;


public class TodoListFragment extends Fragment {
    private RecyclerView mTodoRecyclerView;
    private TodoAdapter mTodoAdapter;
    public static final String RED = "red";
    public static final String GREEN = "green";
    public static final String YELLOW = "yellow";
    public static final int REQUEST_THEME_PREFERENCE_CHANGED = 10;
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mAuth = FirebaseAuth.getInstance();

        if (getActivity() instanceof AppCompatActivity){
            AppCompatActivity activity = (AppCompatActivity)getActivity();
            FirebaseUser user = mAuth.getCurrentUser();
            if (activity.getSupportActionBar() != null && user != null){
                activity.getSupportActionBar().setSubtitle(user.getEmail());
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        mTodoRecyclerView = (RecyclerView) view.findViewById(R.id.todo_list_recycler);
        mTodoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<Todo> todoList = TodoListDao.get(getActivity()).getTodos();
        mTodoAdapter = new TodoAdapter(todoList);
        mTodoRecyclerView.setAdapter(mTodoAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTodoAdapter.setTodos(TodoListDao.get(getActivity()).getTodos());
        mTodoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_todo_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_add_todo:
                Todo todo = new Todo();
                TodoListDao.get(getActivity()).addTodo(todo);
                Intent intent = TodoDetailedActivity.createIntent(getActivity(), todo.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_settings:
                Intent prefIntent = new Intent(getActivity(), TodoPreferenceActivity.class);
                getActivity().startActivityForResult(prefIntent, REQUEST_THEME_PREFERENCE_CHANGED);
                return true;
            case R.id.menu_item_logout:
                mAuth.signOut();
                Intent authIntent = new Intent(getActivity(), AuthActivity.class);
                startActivity(authIntent);
                getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mPhotoImageView;
        private ImageView mColorLineView;
        private Todo mTodo;

        TodoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.todo_list_item_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.todo_list_item_date);
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.todo_list_item_image_view);
            mColorLineView = (ImageView) itemView.findViewById(R.id.todo_list_item_color_line);

        }

        void bindTodo(Todo todo){
            mTodo = todo;
            mTitleTextView.setText(todo.getTitle());
            mDateTextView.setText(todo.getDate().toString());
            File photoFile = TodoListDao.get(getActivity()).getPhotoFile(todo);

            switch (todo.getColor()){
                case RED:
                    int redColor = ContextCompat.getColor(getActivity(), R.color.red);
                    mColorLineView.setBackgroundColor(redColor);
                    break;
                case GREEN:
                    int greenColor = ContextCompat.getColor(getActivity(), R.color.green);
                    mColorLineView.setBackgroundColor(greenColor);
                    break;
                case YELLOW:
                    int yellowColor = ContextCompat.getColor(getActivity(), R.color.yellow);
                    mColorLineView.setBackgroundColor(yellowColor);
                    break;

            }

            Picasso.with(getActivity())
                    .load(photoFile)
                    .resize(500, 500)
                    .skipMemoryCache()
                    .centerCrop()
                    .into(mPhotoImageView);

        }

        @Override
        public void onClick(View v) {
            Intent intent = TodoDetailedActivity.createIntent(getActivity(), mTodo.getId());
            startActivity(intent);
        }
    }

    private class TodoAdapter extends RecyclerView.Adapter<TodoHolder> {

        private List<Todo> mTodos;

        TodoAdapter(List<Todo> todos) {
            mTodos = todos;
        }

        @Override
        public TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_todo_list_item, parent, false);
            return new TodoHolder(view);
        }

        @Override
        public void onBindViewHolder(TodoHolder holder, int position) {
            Todo todo = mTodos.get(position);
            holder.bindTodo(todo);
        }

        @Override
        public int getItemCount() {
            return mTodos.size();
        }

        public void setTodos(List<Todo> todos) {
            mTodos = todos;
        }

    }
}
