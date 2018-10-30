package com.example.android.todolist.serv;

import android.content.Context;

public class ReminderTask {

    public static final String INSERT_NEW_TASK = "new_task_was_added";

    public static void executeTask(Context context, String action){
        if(INSERT_NEW_TASK.equals(action)){
            letSendReminder(context);
        }

    }

    private static void letSendReminder(Context context){
        NotificationUtils.newTaskInsertedReminder(context);
    }
}
