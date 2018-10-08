package com.example.android.todolist.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.example.android.todolist.R;
import com.example.android.todolist.database.AppDatabase;
import com.example.android.todolist.database.TaskEntity;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.ItemClickListener {

    private FloatingActionButton fButton;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;

    private AppDatabase mdb;

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.my_recycle);
        fButton = findViewById(R.id.floating_button);

        adapter = new TaskAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        recyclerView.addItemDecoration(decoration);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().diskIo().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<TaskEntity> list = adapter.getTask();
                        mdb.taskDao().deleteTask(list.get(position));
                    }
                });

            }
        }).attachToRecyclerView(recyclerView);



        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);

                }
        });

        mdb = AppDatabase.getInstance(getApplicationContext());
        setUpViewModel();
    }


    private void setUpViewModel() {
        
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<TaskEntity>>() {
            @Override
            public void onChanged(@Nullable List<TaskEntity> taskEntities) {
                Log.v(TAG, "Updating list of tasks from LiveData in ViewModel");
                adapter.setTask(taskEntities);
            }
        });
    }

    @Override
    public void clickListener(int id) {
        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        intent.putExtra(AddTaskActivity.EXTRA_TASK_ID, id);
        startActivity(intent);

    }
}
