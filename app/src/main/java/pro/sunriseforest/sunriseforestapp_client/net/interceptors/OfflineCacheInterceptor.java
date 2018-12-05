package pro.sunriseforest.sunriseforestapp_client.net.interceptors;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.net.Utils;

public class OfflineCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (!Utils.isNetworkAvailable()) {
            CacheControl cacheControl = new CacheControl.Builder().maxStale(1, TimeUnit.DAYS).build();
            request = request.newBuilder().header("Cache-Control", cacheControl.toString()).build();

        }
        Log.i("Network", "response came from cache");
        return chain.proceed(request);
    }




    public static Cache cacheTasks() {
        Cache cache = null;
        try {
            File dir = SunriseForestApp.getAppContext().getCacheDir();
            cache = new Cache(new File(dir, "http-cache"), 10 * 1024 * 1024); // 10 MB
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cache;
    }
}