package pro.sunriseforest.sunriseforestapp_client.notifications;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;

public class JobSchedulerHelper {

    private Context mContext;

    public JobSchedulerHelper(Context context) {
        mContext = context;
    }

    public static JobSchedulerHelper getInstance(Context context){
        return new JobSchedulerHelper(context);
    }

    public void startNotificationJob(){
        JobScheduler scheduler =  (JobScheduler) mContext
                .getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo.Builder builder = new JobBuilder(mContext)
                .getNotificationsJobBuilder(JobBuilder.ACTION_START_JOB);

        scheduler.schedule(builder.build());
    }

    public void cancelNotificationJob(){
        JobScheduler scheduler =  (JobScheduler) mContext
                .getSystemService(Context.JOB_SCHEDULER_SERVICE);

        scheduler.cancel(JobBuilder.NOTIFICATION_JOB_ID);
    }

}