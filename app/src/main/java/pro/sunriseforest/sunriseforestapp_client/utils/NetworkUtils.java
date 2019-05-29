package pro.sunriseforest.sunriseforestapp_client.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;

public class NetworkUtils {

    public static boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) SunriseForestApp
                .getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null) && (activeNetworkInfo.isConnected());
    }
}