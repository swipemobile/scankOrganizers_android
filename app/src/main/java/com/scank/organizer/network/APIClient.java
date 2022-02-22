package com.scank.organizer.network;

import com.scank.organizer.BuildConfig;
import com.scank.organizer.Utility.ApplicationClass;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static ApiService API_SERVICE;

    //retrofit client with API_BASE_URL for different api calling
    public static ApiService getApiService() {
        if (API_SERVICE == null) {

            OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
            okhttpClientBuilder.callTimeout(2, TimeUnit.MINUTES);
            okhttpClientBuilder.connectTimeout(1, TimeUnit.SECONDS);
            okhttpClientBuilder.readTimeout(1, TimeUnit.MINUTES);
            okhttpClientBuilder.writeTimeout(1, TimeUnit.MINUTES);
            okhttpClientBuilder.cache(ApplicationClass.getInstance().getCache());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttpClientBuilder.build())
                    .build();

            API_SERVICE = retrofit.create(ApiService.class);
        }
        return API_SERVICE;
    }
}
