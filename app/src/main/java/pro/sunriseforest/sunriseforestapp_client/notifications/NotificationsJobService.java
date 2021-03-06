package pro.sunriseforest.sunriseforestapp_client.notifications;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.models.SunriseNotification;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;
import rx.schedulers.Schedulers;


import static pro.sunriseforest.sunriseforestapp_client.notifications.NotificationReceiver.NOTIFICATIONS_EXTRA_TAG;


public class NotificationsJobService extends JobService {

    private final static String TAG = "%%%/" + NotificationsJobService.class.getSimpleName();
    private String mToken;

    private boolean isSheduled;


    @Override
    public void onCreate() {
        mToken = SharedPreferenceHelper.getInstance(this).getToken();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: ");
        isSheduled = false;
        int action = params.getExtras().getInt(JobBuilder.ACTION_KEY);



        if(action == JobBuilder.ACTION_START_JOB){

            ApiFactory.getSunriseForestService()
                    .getNotifications(mToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn( Schedulers.io())
                    .map(notifications -> {
                        cache(notifications);
                        return notifications;
                    })
                    .subscribe(
                            notifications -> {
                                Log.i(TAG, "onStartJob: onNext");
                                showNotifications(notifications);
                                notifyApp(notifications);
                                refreshIfNeeds();
                                jobFinished(params,false);
                            },
                            throwable ->{
                                Log.i(TAG, "onStartJob: onError  " + throwable.getMessage());
                                refreshIfNeeds();
                                jobFinished(params, false);
                            });
        }else{
            onDestroy();
        }

        return true;
    }

    private void notifyApp(List<SunriseNotification> notifications){
        Intent intent = new Intent(NotificationReceiver.NOTIFICATION_ACTION);
        intent.putParcelableArrayListExtra(NOTIFICATIONS_EXTRA_TAG, new ArrayList<>(notifications));
        sendBroadcast(intent);

    }

    private void cache(List<SunriseNotification> notifications ){
        Log.i(TAG, "cache: ");
        SunriseNotificationsProvider.getInstance(this).append(notifications);
    }

    private void showNotifications(List<SunriseNotification> notifications) {
        Log.i(TAG, "showNotifications: ");

        //todo костыль: нужно лист передавать
        for (int i = 0; i < notifications.size(); i++) {
            NotificationHelper.getInstance(this).showNotification(notifications.get(i));
        }

    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob: ");
        refreshIfNeeds();
        return false;
    }


    private void refreshIfNeeds(){
        //не нужно если sdk >= n , так как юзаем setPeriodic(time)
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return;

        if(SharedPreferenceHelper.getInstance(this).getSettings().isNotificationsAreWorks()){
            if(!isSheduled) refreshScheduler();

        }
    }

    private void refreshScheduler(){
        Log.i(TAG, "refreshScheduler: ");
        JobScheduler scheduler =  (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobBuilder(this)
                .getNotificationsJobBuilder(JobBuilder.ACTION_START_JOB);

        int result = scheduler.schedule(builder.build());
        isSheduled = true;

        Log.i(TAG, "refreshScheduler: result refresh = " + result);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");

    }


}
