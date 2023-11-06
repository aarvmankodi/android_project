package com.example.tasks.Model;

public class ToDoModel {
    private String task;
    private int id;
    private int status;
    private String date;

    public String getDate() {
      return date;
    }

    public void setDate(String date) {
      this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
