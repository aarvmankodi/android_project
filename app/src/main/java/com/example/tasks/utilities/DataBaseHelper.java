package com.example.tasks.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tasks.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final String DataBase_Name = "ToDoApp_DataBase";
    private static final String Table_Name = "ToDoApp_Table";
    private static final String Col_1 = "ID";
    private static final String Col_2 = "TASK";
    private static final String Col_3 = "STATUS";



    public DataBaseHelper(@Nullable Context context) {
        super(context, DataBase_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Table_Name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }

    public void insertTask(ToDoModel model)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Col_2 , model.getTask());
        values.put(Col_3 , 0);

        db.insert(Table_Name , null , values);
    }

    public void updateTask(int id , String new_task)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Col_2 , new_task);
        db.update(Table_Name , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id , int status)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Col_3 , status);
        db.update(Table_Name , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void  deleteTask(int id)
    {
        db = this.getWritableDatabase();
        db.delete(Table_Name ,"ID=?" , new String[]{String.valueOf(id)});
    }

    public List<ToDoModel> getAllTasks()
    {
        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<ToDoModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try
        {
            cursor = db.query(Table_Name , null , null , null , null , null , null);
             if (cursor != null)
             {
                 if(cursor.moveToFirst())
                 {
                     do{
                        ToDoModel task = new ToDoModel();
                        task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Col_1)));
                         task.setTask(cursor.getString(cursor.getColumnIndexOrThrow(Col_2)));
                         task.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(Col_3)));
                         modelList.add(task);
                     }while (cursor.moveToNext());
                 }
             }
        }finally
        {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }
}
