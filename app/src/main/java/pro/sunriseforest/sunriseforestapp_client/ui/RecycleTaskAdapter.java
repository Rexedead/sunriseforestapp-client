package pro.sunriseforest.sunriseforestapp_client.ui;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Ad;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class RecycleTaskAdapter extends RecyclerView.Adapter<RecycleTaskAdapter.TaskViewHolder> {

    private List<Ad> mTaskList;
    private Context context;

    RecycleTaskAdapter(List<Ad> taskList, Context context) {
        this.mTaskList = taskList;
        this.context = context;
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

        Ad miniTask = mTaskList.get(position);
        holder.mId.setText(String.valueOf(miniTask.getId()));
        holder.mTextAd.setText(miniTask.getTextAd());
        holder.mIsBooked.setText(String.valueOf(miniTask.isBooked()));

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context, "yes "+position, Toast.LENGTH_SHORT).show();
                System.out.println(position);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment fragment = new TaskFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();

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
            mId = view.findViewById(R.id.task_id);
            mTextAd = view.findViewById(R.id.task_data);
            mIsBooked = view.findViewById(R.id.task_is_booked);
            itemView.setOnClickListener(this);
        }

        void setItemClickListener(ItemClickListener itemClickListener){
            this.mItemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onClick(view, getAdapterPosition());
        }
    }

}
