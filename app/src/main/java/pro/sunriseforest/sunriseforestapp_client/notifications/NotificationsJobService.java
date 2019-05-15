package pro.sunriseforest.sunriseforestapp_client.notifications;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.util.Log;

import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.SunriseNotification;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;
import rx.Observable;
import rx.schedulers.Schedulers;


public class NotificationsJobService extends JobService {



    private final static String TAG = "%%%/" + NotificationsJobService.class.getSimpleName();
    private String mToken;


    @Override
    public void onCreate() {
        mToken = SharedPreferenceHelper.getInstance(this).getToken();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: ");
        int action = params.getExtras().getInt(JobBuilder.ACTION_KEY);

        if(action == JobBuilder.ACTION_START_JOB){

            ApiFactory.getSunriseForestService()
                    .getNotifications(mToken)
//                .compose(new AsyncNetTransformer<>())
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
                                refreshIfWorks();
                                jobFinished(params,false);
                            },
                            throwable ->{
                                Log.i(TAG, "onStartJob: onError  " + throwable.getMessage());
                                jobFinished(params, false);
                            });
        }

        return true;
    }

    private void cache(List<SunriseNotification> notifications ){
        Log.i(TAG, "cache: ");
        SunriseNotificationsProvider.getInstance(this).append(notifications);
    }
    // должен принмимать SunriseNotification
    private void showNotifications(List<SunriseNotification> notifications) {
        Log.i(TAG, "showNotifications: ");

        //todo костыль: нужно лист передавать
        NotificationHelper.getInstance(this).showNotification(notifications.get(0));
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob: ");
        refreshIfWorks();
        return false;
    }


    private void refreshIfWorks(){
        if(SharedPreferenceHelper.getInstance(this).getSettings().isNotificationsAreWorks()){
            refreshScheduler();
        }
    }

    private void refreshScheduler(){
        Log.i(TAG, "refreshScheduler: ");
        JobScheduler scheduler =  (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobBuilder(this)
                .getNotificationsJobBuilder(JobBuilder.ACTION_START_JOB);

        int result = scheduler.schedule(builder.build());

        Log.i(TAG, "refreshScheduler: result refresh = " + result);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");

    }




}
