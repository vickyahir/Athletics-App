package com.example.athletics.Retrofit;

import android.content.Context;

import com.example.athletics.Utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //    public static String BASE_URL = "http://192.168.0.10:10/api/";
//    public static String BASE_URL = "https://athletics54.herokuapp.com/api/";
    public static String BASE_URL = "https://54athletics.com/console/public/api/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

          /*  OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();*/

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder requestBuilder = chain.request().newBuilder();
                    requestBuilder.header("Accept", "application/json");
                    requestBuilder.header("Authorization", "Bearer " + new SessionManager(context).getApiToken());
//                    if (!new ExtraPrefrence(context).getLanguage().equalsIgnoreCase("English")) {
//                        requestBuilder.header("App-Language", "sa");
//                    }

//                    if (!new SessionManager(context).getUserID().equalsIgnoreCase("")) {
//                        if (new SessionManager(context).getApiToken().equalsIgnoreCase("")) {
//                            context.startActivity(new Intent(context, LoginActivity.class));
//                            Functions.animNext(context);
//                        }
//                    }

                    return chain.proceed(requestBuilder.build());
                }
            });

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();


            OkHttpClient client = httpClient
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
