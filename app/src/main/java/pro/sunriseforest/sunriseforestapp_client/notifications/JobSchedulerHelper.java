package pro.sunriseforest.sunriseforestapp_client.notifications;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.util.Log;

import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.utils.TasksUtils;

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

        int result = scheduler.schedule(builder.build());
        Log.i("JobSchedulerHelper", "startNotificationJob: result=" + result);
    }

    public void cancelNotificationJob(){
        JobScheduler scheduler =  (JobScheduler) mContext
                .getSystemService(Context.JOB_SCHEDULER_SERVICE);

        scheduler.cancel(JobBuilder.NOTIFICATION_JOB_ID);
    }

    public void startReminderJob(Task task){
        JobScheduler scheduler =  (JobScheduler) mContext
                .getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo.Builder builder = new JobBuilder(mContext)
                .getReminderNotificationJobBuilder(task);

        int result = scheduler.schedule(builder.build());
        Log.i("JobSchedulerHelper", "startNotificationJob: result=" + result);


    }

    public void cancelReminderJob(Task task){
        JobScheduler scheduler =  (JobScheduler) mContext
                .getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int taskId = TasksUtils.getTaskId(task);
        int jobId = JobBuilder.getReminderJobId(taskId);
        scheduler.cancel(jobId);
    }

}
