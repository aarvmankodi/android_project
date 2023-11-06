package com.example.tasks;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tasks.Model.ToDoModel;
import com.example.tasks.utilities.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask";

    //widgets
    private EditText mEdit;
    private Button SaveButton;
    private EditText mDate;
    private DataBaseHelper myDB;

    public static AddNewTask newInstance()
    {
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_new_task, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mEdit = view.findViewById(R.id.edittext);
        SaveButton = view.findViewById(R.id.savebutton);
        mDate = view.findViewById(R.id.editTextDate2);

        myDB = new DataBaseHelper(getActivity());


        boolean update = false;
        Bundle b = getArguments();

        if (b != null) {
            update = true;
            String task = b.getString("task", "New Task");
            String date = b.getString("date" , "Enter date");
            mEdit.setText(task);
            mDate.setText(date);

            if (task.length() > 0) {
                SaveButton.setEnabled(false);
            }
        }
        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(""))
                {
                    SaveButton.setEnabled(false);
                    SaveButton.setBackgroundColor(Color.GRAY);
                }else
                {
                    SaveButton.setEnabled(true);
                    SaveButton.setBackgroundColor(getResources().getColor(R.color.background));
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(""))
                {
                    SaveButton.setEnabled(false);
                    SaveButton.setBackgroundColor(Color.GRAY);
                }else
                {
                    SaveButton.setEnabled(true);
                    SaveButton.setBackgroundColor(getResources().getColor(R.color.background));
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        boolean finalUpdate = update;
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEdit.getText().toString();
                String date = mDate.getText().toString();

                if(finalUpdate)
                {
                    myDB.updateTask(b.getInt("id") , text , date);
                }
                else
                {
                    ToDoModel item = new ToDoModel();
                    item.setTask(text);
                    item.setStatus(0);
                    item.setDate(date);
                    myDB.insertTask(item);

                }

                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();

        if (activity instanceof OnDialogCloseListener)
        {
            ((OnDialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}