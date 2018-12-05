package pro.sunriseforest.sunriseforestapp_client.net.interceptors;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import pro.sunriseforest.sunriseforestapp_client.net.Utils;

public class NetworkCacheInterceptor implements Interceptor {

    private static final int MAX_AGE = 120;
    private static final long MAX_STALE = 86400;

    @NonNull
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request request = originalRequest.newBuilder().build();
        Response response = chain.proceed(request);

        Log.i("Network", "response came from server");
        String cacheHeaderValue = Utils.isNetworkAvailable() ? "public, max-age=" +
                MAX_AGE : "public, only-if-cached, max-stale=" + MAX_STALE;
        String cacheControl = request.cacheControl().toString();
        String cacheMethod = TextUtils.isEmpty(request.cacheControl().toString()) ?
                cacheHeaderValue : cacheControl;
        return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", cacheMethod)
                .build();
    }

    public static HttpLoggingInterceptor loggingInterceptor(final String tag) {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Log.d(tag, message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.HEADERS);
    }
}