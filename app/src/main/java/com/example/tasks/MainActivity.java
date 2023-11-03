package com.example.tasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.tasks.Adapter.ToDoAdapter;
import com.example.tasks.Model.ToDoModel;
import com.example.tasks.utilities.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {
    private RecyclerView mrecyclerView;
    private FloatingActionButton fab;
    private DataBaseHelper myDB;
    private List<ToDoModel> list;
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mrecyclerView = findViewById(R.id.tasksrecycler);
        fab = findViewById(R.id.floatingActionButton);
        myDB = new DataBaseHelper(MainActivity.this);

        list = new ArrayList<>();
        adapter = new ToDoAdapter(myDB , MainActivity.this);

        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mrecyclerView.setAdapter(adapter);

        list = myDB.getAllTasks();
        Collections.reverse(list);
        adapter.setTasks(list);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager() , AddNewTask.TAG);
            }
        });
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
    list = myDB.getAllTasks();
        Collections.reverse(list);
        adapter.setTasks(list);
        adapter.notifyDataSetChanged();
    }
}