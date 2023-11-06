package com.example.tasks.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasks.AddNewTask;
import com.example.tasks.MainActivity;
import com.example.tasks.Model.ToDoModel;
import com.example.tasks.R;
import com.example.tasks.utilities.DataBaseHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<ToDoModel> mlist;
    private MainActivity activity;
    private DataBaseHelper myDB;

    public ToDoAdapter(DataBaseHelper myDB , MainActivity activity)
    {
        this.activity = activity;
        this.myDB = myDB;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout , parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ToDoModel item = mlist.get(position);
        holder.mcheckbox.setText(item.getTask());
        holder.mcheckbox.setChecked(toBoolean(item.getStatus()));
        holder.mdatetxt.setText(item.getDate());

        holder.mcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    myDB.updateStatus(item.getId() , 1);
                }
                else {
                    myDB.updateStatus(item.getId() , 0);
                }
            }
        });
    }

    public boolean toBoolean(int i)
    {
        return i != 0;
    }

    public Context getContext()
    {
        return activity;
    }

    public void setTasks(List<ToDoModel> mlist)
    {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    public void deleteTask(int position)
    {
        ToDoModel item = mlist.get(position);
        myDB.deleteTask(item.getId());
        mlist.remove(position);
        notifyItemRemoved(position);
    }

    public void editTask(int position)
    {
        ToDoModel item = mlist.get(position);
        Bundle b = new Bundle();
        b.putInt("id" , item.getId());
        b.putString("task" , item.getTask());
        b.putString("date" , item.getDate());
        AddNewTask addNewTask = new AddNewTask();
        addNewTask.setArguments(b);
        addNewTask.show(activity.getSupportFragmentManager() , addNewTask.getTag());



    }



    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mcheckbox;
        EditText mdatetxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mcheckbox = itemView.findViewById(R.id.check_box);
            mdatetxt = itemView.findViewById(R.id.editTextDate);
        }
    }
}
