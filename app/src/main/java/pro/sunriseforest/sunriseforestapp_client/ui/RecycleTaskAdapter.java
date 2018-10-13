package pro.sunriseforest.sunriseforestapp_client.ui;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class RecycleTaskAdapter extends RecyclerView.Adapter<RecycleTaskAdapter.TaskViewHolder> {

    private List<Task> mTaskList;

    RecycleTaskAdapter(List<Task> taskList) {
        this.mTaskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_prewiew_single_row, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        Task miniTask = mTaskList.get(position);
        holder.mId.setText(String.valueOf(miniTask.getId()));
        holder.mTextAd.setText(miniTask.getTextTask());
        holder.mIsBooked.setText(String.valueOf(miniTask.isBooked()));

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mId, mTextAd, mIsBooked;
        private ItemClickListener mItemClickListener;

        TaskViewHolder(View view) {
            super(view);
            mId = view.findViewById(R.id.task_id_recycle_textView);
            mTextAd = view.findViewById(R.id.task_data_recycle_textView);
            mIsBooked = view.findViewById(R.id.task_is_booked_recycle_textView);
            itemView.setOnClickListener(this);
        }

        void setItemClickListener(ItemClickListener itemClickListener) {
            this.mItemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onClick(view, getAdapterPosition());
        }
    }

}
