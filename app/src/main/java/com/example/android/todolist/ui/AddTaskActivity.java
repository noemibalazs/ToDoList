package com.example.android.todolist.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.android.todolist.R;
import com.example.android.todolist.database.AppDatabase;
import com.example.android.todolist.database.TaskEntity;
import com.example.android.todolist.serv.NotificationService;
import com.example.android.todolist.serv.ReminderTask;

import java.util.Date;


public class AddTaskActivity extends AppCompatActivity {

    private EditText editText;
    private RadioGroup radioGroup;
    private Button button;

    private static final int HIGH = 1;
    private static final int MEDIUM = 2;
    private static final int LOW = 3;

    private static final int DEFAULT_ID = -1;
    private int mTask = DEFAULT_ID;

    public static final String EXTRA_TASK_ID = "extrataskid";
    private static final String INSTANCE_TASK_ID = "instanceid";

    private AppDatabase mdb;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editText = findViewById(R.id.task_editor);
        radioGroup = findViewById(R.id.radio_group);
        button = findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSavedButtonClicked();
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)){
            mTask = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_ID);
        }

        mdb = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(AddTaskActivity.EXTRA_TASK_ID)){

            button.setText(R.string.update);
            setTitle(getString(R.string.edit_a_task));
            if (mTask == DEFAULT_ID) {

                mTask = intent.getIntExtra(AddTaskActivity.EXTRA_TASK_ID, DEFAULT_ID);

                AddTaskViewModelFactory factory = new AddTaskViewModelFactory(mdb, mTask);
                final AddTaskViewModel viewModel = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);
                viewModel.getTasks().observe(this, new Observer<TaskEntity>() {
                    @Override
                    public void onChanged(@Nullable TaskEntity taskEntity) {
                        viewModel.getTasks().removeObserver(this);
                        populateUI(taskEntity);
                    }
                });
            }
        } else{
            setTitle(getString(R.string.add_a_new_task));
        }
    }

    public void populateUI(TaskEntity taskEntity){
        if (taskEntity == null){
            return;
        }

        editText.setText(taskEntity.getDescription());
        setPriorityOnViews(taskEntity.getPriority());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTask);
        super.onSaveInstanceState(outState);
    }

    public void onSavedButtonClicked(){
        String text = editText.getText().toString().trim();
        Date date = new Date();
        int priority = getPriorityFromViews();

        final TaskEntity task = new TaskEntity(text, priority, date);
        AppExecutors.getInstance().diskIo().execute(new Runnable() {
            @Override
            public void run() {

                if(mTask == DEFAULT_ID){
                    mdb.taskDao().insertTask(task);

                    Intent intent = new Intent(AddTaskActivity.this, NotificationService.class);
                    intent.setAction(ReminderTask.INSERT_NEW_TASK);
                    startService(intent);

                } else {

                    task.setId(mTask);
                    mdb.taskDao().updateTask(task);
                }


                finish();
            }
        });
    }

    public int getPriorityFromViews(){
        int priority = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radio_group)).getCheckedRadioButtonId();
        switch (checkedId){
            case R.id.radioButton1:
                priority = HIGH;
                break;
            case  R.id.radioButton2:
                priority = MEDIUM;
                break;
            case R.id.radioButton3:
                priority = LOW;
                break;
        }

        return priority;
    }

    public void setPriorityOnViews(int priority){
        switch (priority){
            case HIGH:
                ((RadioGroup)findViewById(R.id.radio_group)).check(R.id.radioButton1);
                break;
            case MEDIUM:
                ((RadioGroup)findViewById(R.id.radio_group)).check(R.id.radioButton2);
                break;
            case LOW:
                ((RadioGroup)findViewById(R.id.radio_group)).check(R.id.radioButton3);
                break;
        }
    }
}
