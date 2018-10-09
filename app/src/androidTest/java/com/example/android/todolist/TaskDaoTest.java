package com.example.android.todolist;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.text.TextUtils;

import com.example.android.todolist.database.AppDatabase;
import com.example.android.todolist.database.TaskDao;
import com.example.android.todolist.database.TaskEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.Date;


@RunWith(JUnit4.class)
public class TaskDaoTest {

    private TaskDao mDao;
    private AppDatabase mDB;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDB = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mDao = mDB.taskDao();
    }

    @After
    public void closeDb() throws IOException{
        mDB.close();
    }

    @Test
    public void write() throws Exception{
        TaskEntity entity = new TaskEntity("", 1, new Date());
        entity.setDescription("Android");
        mDB.taskDao().insertTask(entity);


    }

}

