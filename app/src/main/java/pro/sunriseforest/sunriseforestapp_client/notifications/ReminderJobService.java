package pro.sunriseforest.sunriseforestapp_client.notifications;

import android.app.job.JobParameters;
import android.app.job.JobService;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static pro.sunriseforest.sunriseforestapp_client.notifications.JobBuilder.DATE_KEY;


public class ReminderJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {


        long startTaskDateInMills = params.getExtras().getLong(DATE_KEY);
        Date date = new Date(startTaskDateInMills);

        Calendar today = Calendar.getInstance();
        Calendar startTask = Calendar.getInstance();
        startTask.setTime(date);

        if(startTask.before(today)) NotificationHelper.getInstance(this).showReminderNotification();

        jobFinished(params, false);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
