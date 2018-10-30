package com.example.android.todolist.serv;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class NotificationService extends IntentService {

    public NotificationService(){
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        ReminderTask.executeTask(this, action);

    }
}
