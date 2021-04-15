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

    static String token="008111fded86fc249e6e2cbfc5aecb9960d85ef3";
    @SuppressLint("NewApi")

    private static String AUTH = "Basic " + Base64.encodeToString(("admin:admin@123").getBytes(),Base64.NO_WRAP);
    //public static String AUTH="Basic"+ Base64.getEncoder().encodeToString("srini:Srini@123").getBytes();

    public static Retrofit retrofit = null;
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    public static Retrofit getAPIClient() {

        if (retrofit == null) {

            OkHttpClient okHttpClient =   new OkHttpClient.Builder()
                    /*.addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {

                            Request original=chain.request();
                            Request.Builder requestBuilder=original.newBuilder()
                                    .addHeader("Authorization",AUTH)
                                   // .addHeader("Authorization","Basic " + token)
                                    .method(original.method(),original.body());

                            Request request=requestBuilder.build();

                            return chain.proceed(request);
                        }
                    })*/
                    .retryOnConnectionFailure(true)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();


            retrofit = new Retrofit.Builder().baseUrl(AppConstant.LOCAL_SERVER)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }


}


