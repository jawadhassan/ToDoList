package com.example.hamid_pc.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class TodoListFragment extends Fragment {

    private RecyclerView mRecyclerView;
   private FirebaseRecyclerAdapter<Todo, TodoViewHolder> mAdapter;
    private FirebaseDatabase mFireDatabase;
    private DatabaseReference mDatabaseReference;
    private FloatingActionButton mFLoatingButton;
    private  String UUID;

    public static  TodoListFragment NewInstance(){
        TodoListFragment todoListFragment = new TodoListFragment();
        return todoListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFireDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireDatabase.getReference("todo");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list,container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.todo_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UpdateUI();


        mFLoatingButton = (FloatingActionButton) view.findViewById(R.id.course_floating_button);
        mFLoatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TodoItemActivity.newIntent(getActivity(),null,null,null,null);
                startActivity(intent);
            }
        });
        return view;
    }

    public void UpdateUI(){
        mAdapter = new FirebaseRecyclerAdapter<Todo,TodoViewHolder>(
                Todo.class,
                R.layout.list_item_todo,
                TodoViewHolder.class,
                mDatabaseReference
        ){
            @Override
            protected void populateViewHolder(final TodoViewHolder viewHolder, final Todo model, int position) {
                   viewHolder.textView.setText(model.getmTitle());
                   viewHolder.checkBox.setChecked(model.getmDone());





//
//                    //                }
//            });


            //viewHolder.DeleteButton.setOnClickListener();
            Todo todo = getItem(position);
            viewHolder.bindTodo(todo);
            }
        };

        mRecyclerView.setAdapter(mAdapter);



    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView;
        Todo mTodo;
        CheckBox checkBox;



        @Override
        public void onClick(View v) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
            if (appCompatActivity instanceof TodoListActivity){
                TodoListActivity todoListActivity = (TodoListActivity) appCompatActivity;

                Intent intent = TodoItemActivity.newIntent(todoListActivity,mTodo.getmUUID(),mTodo.getmTitle(),mTodo.getmDetail(),mTodo.getmDone());
                todoListActivity.startActivity(intent);
            }




        }

        public TodoViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            textView = (TextView) itemView.findViewById(R.id.list_item_todo_title_text_view);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        }



        public void bindTodo(Todo todo) {
            mTodo = todo;

        }







    }






}

