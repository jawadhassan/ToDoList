package com.example.hamid_pc.todolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class TodoItemFragment extends Fragment{


    private EditText TodoItemTitle;
    private EditText TodoItemText;
    private CheckBox TodoItemDone;
    private Boolean TodoStatus;
    private String TodoTitleTextString;
    private String TodoItemTextString;
    private Todo todo;
    private Button submitButton;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private Boolean edited =false;

    String mTitle;
    String mDetail;
    Boolean mDone;
    String mUUID;

    public static  TodoItemFragment NewInstance(){
        TodoItemFragment todoItemFragment = new TodoItemFragment();
        return todoItemFragment;
    }

    public static TodoItemFragment newInstance(String UUID,String Title, String Detail, Boolean Done) {
        TodoItemFragment todoItemFragment = new TodoItemFragment();
        Bundle args = new Bundle();
        args.putString("title", Title);
        args.putString("detail", Detail);
        args.putBoolean("done", Done);
        args.putString("uuid",UUID);
        todoItemFragment.setArguments(args);
        return todoItemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           if(getArguments() !=null) {
               mTitle = getArguments().getString("title");
               mDetail = getArguments().getString("detail");
               mDone = getArguments().getBoolean("done");
               mUUID = getArguments().getString("uuid");
           }

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("todo");
    }






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_item,container,false);

        TodoItemTitle = (EditText) view.findViewById(R.id.editTitle);
        TodoItemText = (EditText) view.findViewById(R.id.editText);
        TodoItemDone = (CheckBox) view.findViewById(R.id.checkBox);
        submitButton = (Button) view.findViewById(R.id.button_submit);
        if(mUUID != null && mTitle != null && mDetail != null && mDone != null){
              TodoItemTitle.setText(mTitle);
              TodoItemText.setText(mDetail);
              TodoItemDone.setChecked(mDone);
              TodoItemDone.setEnabled(false);
              submitButton.setEnabled(false);
              setHasOptionsMenu(true);
        }




        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!edited){
                    TodoTitleTextString = TodoItemTitle.getText().toString();
                    TodoItemTextString = TodoItemText.getText().toString();
                    TodoStatus = TodoItemDone.isChecked();
                    Log.d("Check",""+TodoTitleTextString+TodoItemTextString);
                    if(TodoItemTextString.length() > 0 && TodoTitleTextString.length() >0){


                    todo = new Todo(TodoTitleTextString, TodoItemTextString, TodoStatus);
                    mDatabaseReference.push().setValue(todo);
                        getActivity().finish();
                    }else{
                        Toast.makeText(getContext(),"Fill all text fields",Toast.LENGTH_LONG).show();
                    }
                }else {
                    editData();

                }


            }
        });




             TodoItemText.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     TodoItemText.setText("");
                 }
             });

           TodoItemText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
               @Override
               public void onFocusChange(View v, boolean hasFocus) {
                   TodoItemText.setHint("Enter The Title");
               }
           });


        TodoItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoItemTitle.setText("");
            }
        });

        TodoItemTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                TodoItemTitle.setHint("Enter TheText");
            }
        });



        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Edit:
                edited = true;

                TodoItemDone.setEnabled(true);
                submitButton.setEnabled(true);
                return true;

            case R.id.Delete:
                deleteData();

                getActivity().finish();
                return true;

        }

        return true;
    }

    public void deleteData(){
        mDatabaseReference.orderByChild("mUUID").equalTo(mUUID).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String pushKey = dataSnapshot.getKey();



                            mDatabaseReference = FirebaseDatabase.getInstance().getReference("todo/"+pushKey);
                            mDatabaseReference.removeValue();



                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                       public void onCancelled(DatabaseError databaseError) {

                        }
                    });

        }

    public void editData(){

        mDatabaseReference.orderByChild("mUUID").equalTo(mUUID).addChildEventListener(new ChildEventListener() {
                          @Override
                          public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                              String pushKey = dataSnapshot.getKey();
                              Todo todo = dataSnapshot.getValue(Todo.class);

                              todo.setmTitle(TodoItemTitle.getText().toString());
                              todo.setmDetail(TodoItemText.getText().toString());
                              todo.setmDone(TodoItemDone.isChecked());


                              if (TodoItemTitle.getText().toString().length() > 0 && TodoItemText.getText().toString().length() > 0) {

                                  mDatabaseReference = FirebaseDatabase.getInstance().getReference("todo/" + pushKey);
                                  mDatabaseReference.setValue(todo);

                              getActivity().finish();
                          }else{
                                  Toast.makeText(getContext(),"Fill all text fields",Toast.LENGTH_LONG).show();
                          }

                            }
                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater inflater1 = getActivity().getMenuInflater();
        inflater1.inflate(R.menu.mainmenu, menu);

    }



}
