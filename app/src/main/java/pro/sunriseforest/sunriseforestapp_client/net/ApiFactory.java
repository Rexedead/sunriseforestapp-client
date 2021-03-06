package pro.sunriseforest.sunriseforestapp_client.net;

import androidx.annotation.NonNull;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pro.sunriseforest.sunriseforestapp_client.net.interceptors.NetworkCacheInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;



public class ApiFactory {

    private static OkHttpClient sClient;
    private static final String API_ENDPOINT = "http://77.222.54.80:3000/";
    private static final String LOCAL_HOST = "http://192.168.0.3:3000";

    private static volatile SunriseForestService sSunriseForestService;

    private ApiFactory() {
    }

    @NonNull
    public static SunriseForestService getSunriseForestService() {
        SunriseForestService service = sSunriseForestService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sSunriseForestService = buildRetrofit(
                        MoshiConverterFactory.create(),
                        getClient())
                        .create(SunriseForestService.class);

            }
        }
        return service;
    }

    private static Retrofit buildRetrofit(Converter.Factory converterFactory,
                                          OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(ApiFactory.API_ENDPOINT)
//                .baseUrl(LOCAL_HOST)
                .client(client)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        String TAG = "%%%/APIService";
        return new OkHttpClient.Builder()
//                .addInterceptor(new OfflineCacheInterceptor())
                .addInterceptor(new HttpLoggingInterceptor(message -> Log.d(TAG, message))
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new NetworkCacheInterceptor())
//                .cache(OfflineCacheInterceptor.cacheTasks())
                .build();
    }

}
