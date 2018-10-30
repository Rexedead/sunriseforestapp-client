package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class RecycleTaskAdapter extends RecyclerView.Adapter<RecycleTaskAdapter.TaskViewHolder> {
    private List<Task> mTaskList;

    @NonNull
    @Override
    public RecycleTaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task_prewiew_single_row, viewGroup, false);

        return new TaskViewHolder(view);
    }

    RecycleTaskAdapter(List<Task> taskList) {
        this.mTaskList=taskList;

        notifyItemRangeChanged(0,taskList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder task_holder, int position) {
        Task task = mTaskList.get(position);
        task_holder.mName.setText(task.getName());
        task_holder.mReward.setText(String.valueOf(task.getReward()) + "руб.");
        task_holder.mDate.setText(task.getDate());
        task_holder.mIsBooked.setChecked(task.isBooked());

    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView mName, mReward, mDate;
        CheckBox mIsBooked;


        TaskViewHolder(@NonNull View view) {
            super(view);
            mName = view.findViewById(R.id.name_taskSingleRow_textView);
            mReward = view.findViewById(R.id.reward_taskSingleRow_textView);
            mDate = view.findViewById(R.id.date_taskSingleRow_checkBox);

            mIsBooked = view.findViewById(R.id.isbooked_taskSingleRow_checkBox);

        }

    }
}
