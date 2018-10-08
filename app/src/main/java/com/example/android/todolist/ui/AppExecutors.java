package com.example.android.todolist.ui;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor diskIo;
    private final Executor networkIo;
    private final Executor mainThread;

    private AppExecutors(Executor diskIo, Executor networkIo, Executor mainThread){
        this.diskIo = diskIo;
        this.networkIo = networkIo;
        this.mainThread = mainThread;
    }

    public static AppExecutors getInstance(){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3), new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor diskIo(){
        return diskIo;
    }

    public Executor networkIo(){
        return networkIo;
    }

    public Executor mainThread(){
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {

        private Handler mainHandler = new Handler(Looper.getMainLooper());


        @Override
        public void execute(@NonNull Runnable command) {
            mainHandler.post(command);

        }
    }

}
