package pro.sunriseforest.sunriseforestapp_client.date;

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
import okhttp3.logging.HttpLoggingInterceptor;
import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.net.Utils;

public class DataBaseHelper {

    private static final int MAX_AGE = 120;
    private static final long MAX_STALE = 86400;

    private static DataBaseHelper sInstance;

    public static DataBaseHelper getInstance() {
        if (sInstance == null) {
            sInstance = new DataBaseHelper();
        }
        return sInstance;
    }

    public static class OfflineCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            if (!Utils.isNetworkAvailable()) {
                CacheControl cacheControl = new CacheControl.Builder().maxStale(1, TimeUnit.DAYS).build();
                request = request.newBuilder().header("Cache-Control", cacheControl.toString()).build();

            }
            Log.e("Network", "response came from cache");
            return chain.proceed(request);
        }
    }

    public static class NetworkCacheInterceptor implements Interceptor {
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request originalRequest = chain.request();
            String cacheHeaderValue = Utils.isNetworkAvailable() ? "public, max-age=" + MAX_AGE : "public, only-if-cached, max-stale=" + MAX_STALE;
            Request request = originalRequest.newBuilder().build();
            Response response = chain.proceed(request);

            Log.e("Network", "response came from server");
            return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", cacheHeaderValue).build();
        }
    }

    public static HttpLoggingInterceptor loggingInterceptor(final String tag) {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Log.d(tag, message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.HEADERS);
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