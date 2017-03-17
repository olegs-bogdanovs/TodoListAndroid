package com.example.olegsbogdanovs.todolist.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by olegs.bogdanovs on 3/17/2017.
 */

public class Todo {
    private UUID mId;
    private String mTitle;
    private String mDescription;
    private String mColor;
    private boolean mSolved;
    private Date mDate;

    public Todo(UUID id){
        mId = id;
        mDate = new Date();
    }

    public Todo(){
        this(UUID.randomUUID());
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
