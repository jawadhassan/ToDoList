package com.example.hamid_pc.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;



public class TodoItemActivity extends SingleFragmentActivity {

    static  String sTitle;
    static  String sDetail;
    static  Boolean sDone;
    static String sUUID;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, TodoItemActivity.class);
        return i;
    }

    public static Intent newIntent(Context packageContext,String UUID,String Title, String Detail, Boolean Done){

        sTitle = Title;
        sDetail = Detail;
        sDone = Done;
        sUUID = UUID;
        Intent i = new Intent(packageContext,TodoItemActivity.class);
        return i;
    }
    @Override
    protected Fragment createFragment() {
        if (sUUID != null && sTitle != null && sDetail != null && sDone != null){
            return TodoItemFragment.newInstance(sUUID,sTitle,sDetail,sDone);}
        else{
        return TodoItemFragment.NewInstance();}
    }
}
