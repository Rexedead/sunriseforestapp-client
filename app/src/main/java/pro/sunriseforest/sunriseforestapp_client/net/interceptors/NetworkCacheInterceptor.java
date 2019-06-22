package pro.sunriseforest.sunriseforestapp_client.net.interceptors;

import androidx.annotation.NonNull;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkCacheInterceptor implements Interceptor {

    private static final int MAX_AGE = 5;
    private static final long MAX_STALE = 86400;

    @NonNull
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();

//        try {
//            TimeUnit.SECONDS.sleep(4);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Request request = originalRequest.newBuilder().build();
        Response response = chain.proceed(request);

//        Log.i("Network", "response came from server");
//        String cacheHeaderValue = NetworkUtils.isNetworkAvailable() ? "public, max-age=" +
//                MAX_AGE : "public, only-if-cached, max-stale=" + MAX_STALE;
//        String cacheControl = request.cacheControl().toString();
//        String cacheMethod = TextUtils.isEmpty(request.cacheControl().toString()) ?
//                cacheHeaderValue : cacheControl;
//        return response.newBuilder()
//                .removeHeader("Pragma")
//                .removeHeader("Cache-Control")
//                .header("Cache-Control", cacheMethod)
//                .build();

        return response;
    }
}