package com.example.olegsbogdanovs.todolist.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.olegsbogdanovs.todolist.database.TodoCursorWrapper;
import com.example.olegsbogdanovs.todolist.database.TodoDatabaseHelper;
import com.example.olegsbogdanovs.todolist.database.TodoDbSchema;
import com.example.olegsbogdanovs.todolist.database.TodoDbSchema.TodoTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TodoListDao {
    private static TodoListDao sTodoListDao;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private List<Todo> mTodoList;


    private TodoListDao(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TodoDatabaseHelper(mContext).getWritableDatabase();
    }

    public static TodoListDao get(Context context) {
        if (sTodoListDao == null) {
            return new TodoListDao(context);
        }
        return sTodoListDao;
    }

    public void addTodo(Todo todo) {
        ContentValues contentValues = getContentValues(todo);
        mDatabase.insert(TodoTable.NAME, null, contentValues);
    }

    public void updateTodo(Todo todo) {
        String id = todo.getId().toString();
        ContentValues contentValues = getContentValues(todo);
        mDatabase.update(
                TodoTable.NAME,
                contentValues,
                TodoTable.Cols.UUID + " = ?",
                new String[]{id}
        );
    }

    public void removeTodo(Todo todo) {
        String id = todo.getId().toString();
        mDatabase.delete(
                TodoTable.NAME,
                TodoTable.Cols.UUID + " = ?",
                new String[]{id}
        );
    }

    public List<Todo> getTodos(){
        List<Todo> todos = new ArrayList<>();


        TodoCursorWrapper cursorWrapper = queryCrimes(null, null);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                todos.add(cursorWrapper.getTodo());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return todos;
    }

    public Todo getTodo(UUID id){
        TodoCursorWrapper cursorWrapper = queryCrimes(
                TodoTable.Cols.UUID + " = ? ",
                new String[] {id.toString()}
        );

        try {
            if (cursorWrapper.getCount() == 0) {
                return null;
            }

            cursorWrapper.moveToFirst();
            return cursorWrapper.getTodo();
        } finally {
            cursorWrapper.close();
        }
    }

    private static ContentValues getContentValues(Todo todo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoTable.Cols.UUID, todo.getId().toString());
        contentValues.put(TodoTable.Cols.TITLE, todo.getTitle());
        contentValues.put(TodoTable.Cols.DESCRIPTION, todo.getDescription());
        contentValues.put(TodoTable.Cols.COLOR, todo.getColor());
        contentValues.put(TodoTable.Cols.DATE, todo.getDate().getTime());
        contentValues.put(TodoTable.Cols.SOLVED, todo.isSolved() ? 1 : 0);
        return contentValues;
    }

    private TodoCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TodoTable.NAME,
                null, // columns
                whereClause, // where
                whereArgs, // where args
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new TodoCursorWrapper(cursor);
    }

    public File getPhotoFile(Todo todo){
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null){
            return null;
        }

        return new File(externalFilesDir, todo.getPhotoFilename());
    }
}
