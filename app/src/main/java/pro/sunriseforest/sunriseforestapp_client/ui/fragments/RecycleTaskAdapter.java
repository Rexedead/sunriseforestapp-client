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

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class RecycleTaskAdapter extends RecyclerView.Adapter<RecycleTaskAdapter.TaskViewHolder> {
    private List<Task> mTaskList;
    private Date currentTime = Calendar.getInstance().getTime();
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
        Task task_list = mTaskList.get(position);
        task_holder.mId.setText(String.valueOf("Задание #"+task_list.getId()));
        task_holder.mTextAd.setText(task_list.getTextTask());
        task_holder.mDate.setText(currentTime.toString());
//        task_holder.mIsBooked.setText(String.valueOf(task_list.isBooked()));
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView mId, mTextAd, mIsBooked, mDate;
        ImageView mTaskImage;
        TaskViewHolder(@NonNull View view) {
            super(view);
            mId = view.findViewById(R.id.task_id_recycle_textView);
            mTextAd = view.findViewById(R.id.task_data_textView);
            mDate = view.findViewById(R.id.task_date_textView);
            mTaskImage = view.findViewById(R.id.taskdesk_imageView);
        }

    }
}
