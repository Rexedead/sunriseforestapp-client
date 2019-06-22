package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Task;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class
RecycleTaskAdapter extends RecyclerView.Adapter<RecycleTaskAdapter.TaskViewHolder> {
    private List<Task> mTaskList;
    private TaskClickListener mListener;



    public RecycleTaskAdapter(TaskClickListener listener) {
        this.mTaskList = new ArrayList<>();
        mListener = listener;
    }


    @NonNull
    @Override
    public RecycleTaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task_prewiew_single_row, viewGroup, false);

        return new TaskViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder taskHolder, int position) {
        Task task = mTaskList.get(position);
        String reward = task.getReward()+" \u20BD";
        taskHolder.mReward.setText(reward);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault());
        try {
            Date date = df.parse(task.getCreationDate());
            String dateString = DateFormat.format("dd.MM.yyyy", date).toString();
            taskHolder.mCreationDate.setText(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        taskHolder.mStartDate.setText(task.getStartDate());
        taskHolder.mEndDate.setText(task.getDeadlineDate());
        switch (task.getStatus()) {
            case 101:
                taskHolder.mStatus.setImageResource(R.drawable.status_101);
                taskHolder.setDefaultBackground();
                break;
            case 102:
                taskHolder.mStatus.setImageResource(R.drawable.status_102);
                taskHolder.setDefaultBackground();
                break;
            case 103:
                taskHolder.mStatus.setImageResource(R.drawable.status_103);
                taskHolder.setComplectedBackground();

//                бага: неверно отображаетсяя стиль из за того что холдер не пересоздается а используется заново со страрыми изменениями
//
//                taskHolder.mDescription.setTypeface(null, Typeface.NORMAL);
//                taskHolder.mReward.setTypeface(null, Typeface.NORMAL);
//
//                taskHolder.mCreationDate.setTypeface(null, Typeface.NORMAL);
//                taskHolder.mStartDate.setTypeface(null, Typeface.NORMAL);
//                taskHolder.mEndDate.setTypeface(null, Typeface.NORMAL);
//                taskHolder.mCreationDateText.setTypeface(null, Typeface.NORMAL);
//                taskHolder.mStartDateText.setTypeface(null, Typeface.NORMAL);
//                taskHolder.mEndDateText.setTypeface(null, Typeface.NORMAL);
//                taskHolder.mDescription.setTextColor(ContextCompat.getColor(SunriseForestApp.getAppContext(),
//                        R.color.secondaryText));
//                taskHolder.mReward.setTextColor(ContextCompat.getColor(SunriseForestApp.getAppContext(),
//                        R.color.secondaryText));
//                taskHolder.mCreationDate.setTextColor(ContextCompat.getColor(SunriseForestApp.getAppContext(),
//                        R.color.secondaryText));
//                taskHolder.mEndDate.setTextColor(ContextCompat.getColor(SunriseForestApp.getAppContext(),
//                        R.color.secondaryText));
//                taskHolder.mStartDate.setTextColor(ContextCompat.getColor(SunriseForestApp.getAppContext(),
//                        R.color.secondaryText));
//                taskHolder.mCreationDateText.setTextColor(ContextCompat.getColor(SunriseForestApp.getAppContext(),
//                        R.color.secondaryText));
//                taskHolder.mStartDateTextext.setTextColor(ContextCompat.getColor(SunriseForestApp.getAppContext(),
//                        R.color.secondaryText));
//                taskHolder.mEndDateText.setTextColor(ContextCompat.getColor(SunriseForestApp.getAppContext(),
//                        R.color.secondaryText));
                break;
        }




        String taskDescription = task.getTaskDescription();
        taskHolder.mDescription.setText(taskDescription);
        taskHolder.setPosition(position);
        taskHolder.setListener(mListener);

    }


    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public void addTasks(List<Task> tasks, boolean refresh){

        if(refresh){
            mTaskList.clear();
            mTaskList.addAll(tasks);
            notifyDataSetChanged();
            return;
        }

        int position = mTaskList.size();
        mTaskList.addAll(tasks);
        notifyItemChanged(position);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView mDescription, mReward, mStartDate, mEndDate, mCreationDate, mCreationDateText,
                mStartDateText, mEndDateText;
        ImageView mStatus;



        private int mPosition;

        private void setPosition(int position){

            mPosition = position;
        }
        private void setListener(TaskClickListener listener) {

            itemView.setOnClickListener( v-> listener.onClick(mPosition));
        }

        TaskViewHolder(@NonNull View view) {
            super(view);

            mDescription = view.findViewById(R.id.taskSingleRow_description_textView);
            mDescription.setSingleLine(true);
            mReward = view.findViewById(R.id.taskSingleRow_reward_textView);
            mCreationDate = view.findViewById(R.id.taskSingleRow_creationDate_get_textView);
            mStartDate = view.findViewById(R.id.taskSingleRow_startDate_get_textView);
            mEndDate = view.findViewById(R.id.taskSingleRow_endDate_get_textView);
            mStatus = view.findViewById(R.id.taskSingleRow_status_imageView);
            mCreationDateText = view.findViewById(R.id.taskSingleRow_creationDate_textView);
            mStartDateText = view.findViewById(R.id.taskSingleRow_startDate_textView);
            mEndDateText = view.findViewById(R.id.taskSingleRow_endDate_textView);

        }

        public void setComplectedBackground() {
            itemView.setBackgroundColor(ContextCompat.getColor(SunriseForestApp.getAppContext(),
                        R.color.field_background));
        }

        public void setDefaultBackground() {
            itemView.setBackgroundColor( Color.WHITE);

        }
    }

    public interface TaskClickListener{
        void onClick(int id);
    }
}
