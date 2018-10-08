package com.example.android.todolist.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task")
public class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private int priority;
    @ColumnInfo(name = "update_at")
    private Date date;

    public TaskEntity(int id, String description, int priority, Date date){
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.date = date;
    }

    @Ignore
    public TaskEntity(String description, int priority, Date date){
        this.description = description;
        this.priority = priority;
        this.date = date;
    }


    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getPriority(){
        return this.priority;
    }

    public Date getDate(){
        return this.date;
    }

}
