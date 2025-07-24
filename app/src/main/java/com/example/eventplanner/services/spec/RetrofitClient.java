package com.example.eventplanner.services.spec;

import android.content.Intent;

import com.example.eventplanner.activities.startup.LoginActivity;
import com.example.eventplanner.helpers.LocalDateAdapter;
import com.example.eventplanner.helpers.LocalDateTimeDeserializer;
import com.example.eventplanner.helpers.LocalTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.Request;
import okhttp3.Response;

public class RetrofitClient {

    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, type, context) -> LocalDate.parse(json.getAsString()))
            .create();

    private static OkHttpClient createClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest  = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + AuthService.getToken())
                                .build();
                        Response response = chain.proceed(newRequest);

                        if (response.code() == 401) {
                            AuthService.logout();
                            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                                android.widget.Toast.makeText(App.getInstance().getApplicationContext(),
                                        "Log in again", android.widget.Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(App.getInstance().getApplicationContext(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                App.getInstance().startActivity(intent);
                            });
                        }

                        return response;
                    }
                }).addInterceptor(logging)
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .writeTimeout(8, TimeUnit.SECONDS)
                .build();
    }

    public static Retrofit getClient(String url){
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(createClient())
                .build();
    }
}
