package pro.sunriseforest.sunriseforestapp_client.notifications;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.util.Log;

import pro.sunriseforest.sunriseforestapp_client.models.SunriseNotification;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;
import rx.schedulers.Schedulers;


public class NotificationsJobService extends JobService {






    private final static String TAG = "%%%/" + NotificationsJobService.class.getSimpleName();
    private String mToken;

    private boolean mWillRefresh;

    @Override
    public void onCreate() {
        mToken = SharedPreferenceHelper.getInstance(this).getToken();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: ");
        int action = params.getExtras().getInt(JobBuilder.ACTION_KEY);

        if(action == JobBuilder.ACTION_START_JOB){
            mWillRefresh = true;
            ApiFactory.getSunriseForestService()
                    .getTasks(mToken)
//                .compose(new AsyncNetTransformer<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn( Schedulers.io())
                    .subscribe(
                            tasks -> {
                                showNotification(tasks.size());
                                jobFinished(params,false);
                            },
                            throwable -> jobFinished(params, false));

            if(mWillRefresh) refreshScheduler();

        }else if(action == JobBuilder.ACTION_CANCEL_JOB){
            mWillRefresh = false;
            jobFinished(params, false);
        }


        return true;
    }

    // должен принмимать SunriseNotification
    private void showNotification(int size) {
        Log.i(TAG, "showNotification: ");
        SunriseNotification notification = new SunriseNotification("Количество тасков",
                "Тасков на сервере: ",
                NotificationHelper.AMOUNT_OF_TASKS,
                size);

        NotificationHelper.getInstance(this).showNotification(notification);



    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob: ");
        return false;
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
