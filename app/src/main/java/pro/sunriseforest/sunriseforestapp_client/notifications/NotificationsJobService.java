package pro.sunriseforest.sunriseforestapp_client.notifications;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.models.SunriseNotification;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;
import rx.Observable;
import rx.schedulers.Schedulers;

import static pro.sunriseforest.sunriseforestapp_client.notifications.NotificationHelper.AMOUNT_OF_TASKS_TYPE;


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
//                .compose(new AsyncNetTransformer<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn( Schedulers.io())
                    .flatMap(Observable::from)
                    .filter(this::isNotEmptyNotification)
                    .toList()
                    .map(notifications -> {
                        cache(notifications);
                        return notifications;
                    })
                    .subscribe(
                            notifications -> {
                                Log.i(TAG, "onStartJob: onNext");
                                showNotifications(notifications);
                                notifyApp();
                                refreshIfWorks();
                                jobFinished(params,false);
                            },
                            throwable ->{
                                Log.i(TAG, "onStartJob: onError  " + throwable.getMessage());
                                refreshIfWorks();
                                jobFinished(params, false);
                            });
        }

        return true;
    }

    private void notifyApp(){
        Intent intent = new Intent(NotificationReceiver.NOTIFICATION_ACTION);
        sendBroadcast(intent);

    }
    private void cache(List<SunriseNotification> notifications ){
        Log.i(TAG, "cache: ");
        SunriseNotificationsProvider.getInstance(this).append(notifications);
    }
    // должен принмимать SunriseNotification
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
        refreshIfWorks();
        return false;
    }


    private void refreshIfWorks(){
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

    private boolean isNotEmptyNotification(SunriseNotification notification){
        return notification.getType() == AMOUNT_OF_TASKS_TYPE &&
                Integer.parseInt(String.valueOf(notification.getData().toString().charAt(0)))!= 0;
    }
}
