package com.example.taskmanager.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.model.Task;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TaskStateAdapter extends RecyclerView.Adapter<TaskStateAdapter.TaskStateHolder> {

    private List<Task> mTasks;
    private Context mContext;

    public List<Task> getTasks() {
        return mTasks;
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }

    public TaskStateAdapter(List<Task> tasks, Context context) {
        mTasks = tasks;
        mContext = context;
    }

    @NonNull
    @NotNull
    @Override
    public TaskStateHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.task_item_list, parent, false);
        return new TaskStateHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull @NotNull TaskStateAdapter.TaskStateHolder holder,
            int position) {
        Task task = mTasks.get(position);
        holder.bindTasks(task);

    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class TaskStateHolder extends RecyclerView.ViewHolder {

        private TextView mFirstWordTitle;
        private TextView mTitle;
        private TextView mDate;

        private Task mTask;

        public TaskStateHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mFirstWordTitle = itemView.findViewById(R.id.title_first_word);
            mTitle = itemView.findViewById(R.id.txt_view_title_item);
            mDate = itemView.findViewById(R.id.txt_view_date_item);
        }

        public void bindTasks(Task task){
            mTask = task;

            mFirstWordTitle.setText(Character.toString(task.getTitle().charAt(0)));
            mTitle.setText(task.getTitle());
            mDate.setText(task.getDate().toString());
        }
    }
}
