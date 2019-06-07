package pro.sunriseforest.sunriseforestapp_client.notifications;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import pro.sunriseforest.sunriseforestapp_client.utils.TasksUtils;

import static pro.sunriseforest.sunriseforestapp_client.notifications.JobBuilder.DATE_KEY;


public class ReminderJobService extends JobService {


    private static final String TAG = "%%%/" + ReminderJobService.class.getSimpleName();


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: ");

        String startTaskDate = params.getExtras().getString(DATE_KEY);

        Date startTaskDateInMills = TasksUtils.getDateInMillsFromString(startTaskDate);



        Calendar now = Calendar.getInstance();
        Calendar startTask = Calendar.getInstance();
        startTask.setTime(startTaskDateInMills);

        if(startTask.before(now)){

            if(sleepTime()){
                reschedule(params, timeUntilWakeUp(startTaskDateInMills) + 5000);
                jobFinished(params, false);
            }else {
                NotificationHelper.getInstance(this).showReminderNotification();
            }
        }

        jobFinished(params, false);


        return false;
    }

    private boolean sleepTime(){
        Calendar now = Calendar.getInstance();
        int hourOfDay = now.get(Calendar.HOUR_OF_DAY);

        return hourOfDay >= 23 || hourOfDay <= 10;

    }

    private long timeUntilWakeUp(Date startTask){
        Calendar now = Calendar.getInstance();
        Calendar wakeUp = Calendar.getInstance();

        if(wakeUp.get(Calendar.HOUR_OF_DAY) >= 23) {
            wakeUp.add(Calendar.DAY_OF_YEAR , 1);
            if(wakeUp.before(now)) wakeUp.add(Calendar.YEAR ,1);
            wakeUp.set(Calendar.HOUR_OF_DAY, 10);
        }else if(wakeUp.get(Calendar.HOUR_OF_DAY) <= 10){
            wakeUp.set(Calendar.HOUR_OF_DAY, 10);
        } else return 0;

        return wakeUp.getTimeInMillis();
    }

    private void reschedule(JobParameters jobParameters, long minimumLatency){
        JobScheduler scheduler =  (JobScheduler) getApplicationContext()
                .getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName jobService = new ComponentName(getApplicationContext(), ReminderJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(jobParameters.getJobId(), jobService);
        builder.setMinimumLatency(minimumLatency)
                .setRequiresDeviceIdle(false)
                .setRequiresCharging(false)
                .setPersisted(true)
                .setOverrideDeadline(TimeUnit.HOURS.toMillis(5))
                .setExtras(jobParameters.getExtras());


        int result = scheduler.schedule(builder.build());
        Log.i(TAG, "reschedule: result = " + result);
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
