package com.example.olegsbogdanovs.todolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.olegsbogdanovs.todolist.database.TodoDbSchema.TodoTable;


public class TodoDatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "todolistBase.db";

    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TodoTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                TodoTable.Cols.UUID + ", " +
                TodoTable.Cols.TITLE + ", " +
                TodoTable.Cols.DESCRIPTION + ", " +
                TodoTable.Cols.COLOR + ", " +
                TodoTable.Cols.SOLVED + ", " +
                TodoTable.Cols.DATE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
