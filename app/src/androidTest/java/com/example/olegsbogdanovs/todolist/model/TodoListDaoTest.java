package com.example.olegsbogdanovs.todolist.model;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class TodoListDaoTest {
    private TodoListDao mTodoListDao;

    @Before
    public void setUp(){
        mTodoListDao = TodoListDao.get(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void testAddAndDeleteTodo(){
        Todo todo = new Todo();
        mTodoListDao.addTodo(todo);
        assertNotNull(mTodoListDao.getTodo(todo.getId()));

        mTodoListDao.removeTodo(todo);
        assertNull(mTodoListDao.getTodo(todo.getId()));
    }

    @Test
    public void testUpdateTodo(){
        Todo todo = new Todo();
        todo.setTitle("test");

        mTodoListDao.addTodo(todo);
        todo.setTitle("test2");
        mTodoListDao.updateTodo(todo);

        assertTrue(mTodoListDao.getTodo(todo.getId()).getTitle().equals("test2"));

    }

}
