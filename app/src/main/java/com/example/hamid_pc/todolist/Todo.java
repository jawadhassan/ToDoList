package com.example.hamid_pc.todolist;

/**
 * Created by Hamid-PC on 7/9/2017.
 */

public class Todo {
    private String mTitle;
    private String mDetail;
    private String mUUID;
    private Boolean mDone;

    public Todo(String mTitle, String mDetail, Boolean mDone) {
        this.mTitle = mTitle;
        this.mDetail = mDetail;
        this.mDone = mDone;
        this.mUUID = java.util.UUID.randomUUID().toString();
    }

    public Todo() {
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDetail() {
        return mDetail;
    }

    public void setmDetail(String mDetail) {
        this.mDetail = mDetail;
    }

    public Boolean getmDone() {
        return mDone;
    }

    public void setmDone(Boolean mDone) {
        this.mDone = mDone;
    }

    public String getmUUID() {
        return mUUID;
    }

}
