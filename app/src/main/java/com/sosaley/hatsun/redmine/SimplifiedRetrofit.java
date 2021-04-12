package com.sosaley.hatsun.redmine;

import android.util.Base64;

import com.sosaley.hatsun.retrofit.ApiInterface;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SimplifiedRetrofit {

    private static final String AUTH = "Basic " + Base64.encodeToString(("srini:Srini@123").getBytes(), Base64.NO_WRAP);

    private static final String BASE_URL = "http://redmine.sosaley.co.in:83/";
    private static SimplifiedRetrofit mInstance;
    private Retrofit retrofit;


    private SimplifiedRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();

                                Request.Builder requestBuilder = original.newBuilder()
                                        .addHeader("Authorization", AUTH)
                                        .method(original.method(), original.body());

                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }
                        }
                ).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static synchronized SimplifiedRetrofit getInstance() {
        if (mInstance == null) {
            mInstance = new SimplifiedRetrofit();
        }
        return mInstance;
    }

    public ApiInterface getApi() {
        return retrofit.create(ApiInterface.class);
    }

}
