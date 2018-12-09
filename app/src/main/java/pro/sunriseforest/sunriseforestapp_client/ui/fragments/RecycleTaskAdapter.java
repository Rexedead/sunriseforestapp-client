package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        this.mTaskList = taskList;

        notifyItemRangeChanged(0, taskList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder task_holder, int position) {
        Task task = mTaskList.get(position);
        String reward = task.getReward()+" \u20BD";
        task_holder.mReward.setText(reward);
        task_holder.mCreationDate.setText(task.getCreationDate());
        task_holder.mStartDate.setText(task.getStartDate());
        task_holder.mEndDate.setText(task.getDeadlineDate());
        switch (task.getStatus()) {
            case 101:
                task_holder.mStatus.setImageResource(R.drawable.status_101);
                break;
            case 102:
                task_holder.mStatus.setImageResource(R.drawable.status_102);
                break;
            case 103:
                task_holder.mStatus.setImageResource(R.drawable.status_103);
                break;
        }

        String taskDescription = task.getTaskDescription();
        task_holder.mDescription.setText(taskDescription);
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView mDescription, mReward, mStartDate, mEndDate, mCreationDate;
        ImageView mStatus;


        TaskViewHolder(@NonNull View view) {
            super(view);
            mDescription = view.findViewById(R.id.taskSingleRow_description_textView);
            mDescription.setSingleLine(true);
            mReward = view.findViewById(R.id.taskSingleRow_reward_textView);
            mCreationDate = view.findViewById(R.id.taskSingleRow_creationDate_get_textView);
            mStartDate = view.findViewById(R.id.taskSingleRow_startDate_get_textView);
            mEndDate = view.findViewById(R.id.taskSingleRow_endDate_get_textView);
            mStatus = view.findViewById(R.id.taskSingleRow_status_imageView);

        }

    }
}
