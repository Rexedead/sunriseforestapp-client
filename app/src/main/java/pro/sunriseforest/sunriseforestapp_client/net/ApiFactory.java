package pro.sunriseforest.sunriseforestapp_client.net;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static OkHttpClient sClient;

    private static final String API_ENDPOINT = "http://192.168.1.7:3000/";

    private static volatile SunriseForestService sSunriseForestService;

    private ApiFactory() {
    }

    @NonNull
    public static SunriseForestService getSunriseForestService(){
        SunriseForestService service = sSunriseForestService;
        if(service == null){
            synchronized (ApiFactory.class){
                service = sSunriseForestService = buildRetrofit(
                        GsonConverterFactory.create(),
                        API_ENDPOINT,
                        getClient())
                        .create(SunriseForestService.class);

            }
        }
        return service;
    }

    private static Retrofit buildRetrofit(Converter.Factory converterFactory,
                                          String baseUrl,
                                          OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
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

        return new OkHttpClient.Builder()
                .build();
    }

}
