package com.example.android.todolist.ui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.todolist.R;
import com.example.android.todolist.database.TaskEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private Context context;
    private List<TaskEntity> mList;
    final private ItemClickListener itemClickListener;

    private static final String DATE_FORMAT = "dd/MM/YYYY";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    public interface ItemClickListener{
        void clickListener(int id);
    }

    public TaskAdapter(Context mContext, ItemClickListener mClickListener){
        context = mContext;
        itemClickListener = mClickListener;
    }


    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {

        TaskEntity taskEntity = mList.get(position);

        String description = taskEntity.getDescription();
        int priority = taskEntity.getPriority();
        Date date = taskEntity.getDate();
        String formatedDate = dateFormat.format(date);

        holder.taskDescpition.setText(description);
        holder.taskDate.setText(formatedDate);

        String priorities = "" + priority;
        holder.taskPriority.setText(priorities);

        GradientDrawable drawable = (GradientDrawable) holder.taskPriority.getBackground();
        int color = getPriority(priority);
        drawable.setColor(color);

    }

    private int getPriority(int priority){

        int position = 0;
        switch (priority){
            case 1:
                position = ContextCompat.getColor(context, R.color.materialRed);
                break;
            case 2:
                position = ContextCompat.getColor(context, R.color.materialOrange);
                break;
            case 3:
                position = ContextCompat.getColor(context, R.color.materialYellow);
                break;
            default:
                break;

        }

        return position;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView taskDescpition;
        private TextView taskPriority;
        private TextView taskDate;

        public TaskHolder(View itemView) {
            super(itemView);
            taskDescpition = itemView.findViewById(R.id.task_description);
            taskPriority = itemView.findViewById(R.id.task_priority);
            taskDate = itemView.findViewById(R.id.task_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = mList.get(getAdapterPosition()).getId();
            itemClickListener.clickListener(position);

        }
    }

    public void setTask(List<TaskEntity> tasks){
        mList = tasks;
        notifyDataSetChanged();
    }

    public List<TaskEntity> getTask(){
        return mList;
    }
}
