package pro.sunriseforest.sunriseforestapp_client;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class SunriseForestApp extends Application {

    private static Context sAppContext;

    public static Context getAppContext() {
        return sAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("%%%/SunriseForestApp", "onCreate()");
        sAppContext = getApplicationContext();
    }


}
