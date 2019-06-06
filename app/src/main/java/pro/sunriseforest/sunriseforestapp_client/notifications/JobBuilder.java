package pro.sunriseforest.sunriseforestapp_client.notifications;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;
import android.util.Log;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.utils.TasksUtils;

class JobBuilder {

    static final String ACTION_KEY = "pro.sunriseforest.sunriseforestapp_client.notifications_action_key";

    static final String DATE_KEY = "pro.sunriseforest.sunriseforestapp_client.notifications_date_key";

    private static final long MILLS_PER_DAY = 1000 * 60 * 60 * 24;

    static final int ACTION_START_JOB = 1001;
    private static final int REMIND_ME = 10002;


    static final int NOTIFICATION_JOB_ID = 10001;


    private Context mContext;

    static int getReminderJobId(int taskId) {
        return REMIND_ME + taskId;
    }


    JobBuilder(Context context) {

        mContext = context;

    }

    JobInfo.Builder getReminderNotificationJobBuilder(Task task){

        int taskId = Integer.parseInt(task.getTaskID());
        long dateStartTask = TasksUtils.getStartDateInMills(task);
        ComponentName jobService = new ComponentName(mContext, ReminderJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(getReminderJobId(taskId), jobService);


        long minimumLatency;
        Calendar today = Calendar.getInstance();
        long deltaDays = (dateStartTask - today.getTimeInMillis()) / (MILLS_PER_DAY);

        if(deltaDays < 0 ) return null;

        if(deltaDays == 0)   minimumLatency = 0;
        else{
            minimumLatency =  deltaDays * MILLS_PER_DAY + (MILLS_PER_DAY / 2) -  (today.get(Calendar.HOUR_OF_DAY) * 1000 * 60 * 60); // В 12:00 за день до старта
        }

        Log.i("%%%/JobBuilder", "getReminderNotificationJobBuilder: minimumLatency = " + minimumLatency);
        builder.setMinimumLatency(minimumLatency)
                .setRequiresDeviceIdle(false)
                .setRequiresCharging(false)
                .setPersisted(true);

        if(minimumLatency != 0) builder.setOverrideDeadline(TimeUnit.HOURS.toMillis(10));

        PersistableBundle bundle = new PersistableBundle();

        bundle.putLong(DATE_KEY, dateStartTask);

        return builder;
    }



    private JobInfo.Builder getNotificationsJobBuilder(){

        ComponentName jobService = new ComponentName(mContext  , NotificationsJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(NOTIFICATION_JOB_ID, jobService);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setMinimumLatency(6000);

        } else {
            builder.setPeriodic(6000);
        }
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setRequiresDeviceIdle(false);
        builder.setRequiresCharging(false);
        builder.setPersisted(true);
        return builder;
    }


    public JobInfo.Builder getNotificationsJobBuilder(int type){
        PersistableBundle bundle = new PersistableBundle();
        bundle.putInt(ACTION_KEY, type);

        return getNotificationsJobBuilder().setExtras(bundle);
    }

}
