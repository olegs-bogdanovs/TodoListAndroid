package com.example.olegsbogdanovs.todolist.database;

/**
 * Created by olegs.bogdanovs on 3/17/2017.
 */


public class TodoDbSchema {

    public static final class TodoTable{
        public static final String NAME = "todo";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String COLOR = "color";
            public static final String SOLVED = "solved";
            public static final String DATE = "date";
        }
    }
}
