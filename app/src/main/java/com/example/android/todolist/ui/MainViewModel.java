package com.example.android.todolist.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.todolist.database.AppDatabase;
import com.example.android.todolist.database.TaskEntity;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<TaskEntity>> tasks;

    public MainViewModel(@NonNull Application application) {
        super(application);
        Log.v(TAG, "Receiving Livedata from Database");
        AppDatabase db = AppDatabase.getInstance(getApplication());
        tasks = db.taskDao().loadTasks();
    }

    public LiveData<List<TaskEntity>> getTasks(){
        return tasks;
    }
}
