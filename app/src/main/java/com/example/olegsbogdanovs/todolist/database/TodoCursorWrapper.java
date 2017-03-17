package com.example.olegsbogdanovs.todolist.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.olegsbogdanovs.todolist.database.TodoDbSchema.TodoTable;
import com.example.olegsbogdanovs.todolist.model.Todo;

import java.util.Date;
import java.util.UUID;


public class TodoCursorWrapper extends CursorWrapper {

    public TodoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Todo getTodo() {
        String id = getString(getColumnIndex(TodoTable.Cols.UUID));
        String title = getString(getColumnIndex(TodoTable.Cols.TITLE));
        String description = getString(getColumnIndex(TodoTable.Cols.DESCRIPTION));
        String color = getString(getColumnIndex(TodoTable.Cols.COLOR));
        int solved = getInt(getColumnIndex(TodoTable.Cols.SOLVED));
        long date = getLong(getColumnIndex(TodoTable.Cols.DATE));

        Todo todo = new Todo(UUID.fromString(id));
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setColor(color);
        todo.setSolved(solved != 0);
        todo.setDate(new Date(date));

        return todo;
    }
}
