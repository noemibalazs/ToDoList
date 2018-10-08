package com.example.android.todolist.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.todolist.database.AppDatabase;
import com.example.android.todolist.database.TaskEntity;

class AddTaskViewModel extends ViewModel {

    private LiveData<TaskEntity> tasks;

    public AddTaskViewModel(AppDatabase db, int id){
        tasks = db.taskDao().loadTaskById(id);
    }

    public LiveData<TaskEntity> getTasks(){
        return tasks;
    }


}
