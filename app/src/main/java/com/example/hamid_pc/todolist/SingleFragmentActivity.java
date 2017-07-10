package com.example.hamid_pc.todolist;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;



public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment todoFragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if(todoFragment == null){
            todoFragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container,todoFragment)
                    .commit();
        }

    }
}
