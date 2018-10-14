package pro.sunriseforest.sunriseforestapp_client;

import android.app.Application;
import android.content.Context;

public class SunriseForestApp extends Application {

    private static Context sAppContext;

    public static Context getAppContext() {
        return sAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
    }

}
