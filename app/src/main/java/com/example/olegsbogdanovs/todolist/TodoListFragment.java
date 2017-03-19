package com.example.olegsbogdanovs.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.olegsbogdanovs.todolist.model.Todo;
import com.example.olegsbogdanovs.todolist.model.TodoListDao;

import java.util.List;


public class TodoListFragment extends Fragment {
    private RecyclerView mTodoRecyclerView;
    private TodoAdapter mTodoAdapter;
    private static final String RED = "red";
    private static final String GREEN = "green";
    private static final String YELLOW = "yellow";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        }
        return super.onOptionsItemSelected(item);
    }

    private class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitleTextView;
        private LinearLayout mLinearLayout;
        private Todo mtodo;

        TodoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.todo_list_item_title);
            mLinearLayout = (LinearLayout) itemView;

        }

        void bindTodo(Todo todo){
            mtodo = todo;
            mTitleTextView.setText(todo.getTitle());
        }

        @Override
        public void onClick(View v) {
            Intent intent = TodoDetailedActivity.createIntent(getActivity(), mtodo.getId());
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
