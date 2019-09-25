package com.elearning.client.network;

import android.content.Context;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.elearning.client.utils.Const;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String url = Const.URL + "api/v1/";

    private static Retrofit retrofit;

    public static Retrofit getClient(Context context) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
                return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = gsonBuilder.create();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);



        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        /* This line is where we add the interceptor to the retrofit service  */

        httpClient.addInterceptor(new NetworkInterceptor(context));

        httpClient.addInterceptor(logging);

        httpClient.connectTimeout(30, TimeUnit.SECONDS);

        httpClient.readTimeout(30, TimeUnit.SECONDS);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return retrofit;

    }

//    public static Retrofit getClient() {
//
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(url)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .build();
//        }
//
//        return retrofit;
//
//    }

}
