package com.sosaley.hatsun.retrofit;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sosaley.hatsun.utils.AppConstant;

import android.util.Base64;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    @SuppressLint("NewApi")

    private static final String AUTH = "Basic " + Base64.encodeToString(("srini:Srini@123").getBytes(),Base64.NO_WRAP);
    //public static String AUTH="Basic"+ Base64.getEncoder().encodeToString("srini:Srini@123").getBytes();

    public static Retrofit retrofit = null;
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    public static Retrofit getAPIClient() {

        if (retrofit == null) {

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {

                            Request original=chain.request();
                            Request.Builder requestBuilder=original.newBuilder()
                                    .addHeader("Authorization",AUTH)
                                    .method(original.method(),original.body());

                            Request request=requestBuilder.build();

                            return chain.proceed(request);
                        }
                    })
                    .retryOnConnectionFailure(true)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();


            retrofit = new Retrofit.Builder().baseUrl(AppConstant.REDMINE_DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }


}


