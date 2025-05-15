package com.example.zybertest.API;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL_1 = "https://myzybershop.loca.lt/";
    private static final String BASE_URL_2 = "https://api.rawg.io/api/";

    private static Retrofit retrofit1 = null;
    private static Retrofit retrofit2 = null;

    // Приватный конструктор
    private RetrofitClient() {}

    // Клиент для первого API (myzybershop)
    public static Retrofit getClient1() {
        if (retrofit1 == null) {
            retrofit1 = createRetrofitInstance(BASE_URL_1);
        }
        return retrofit1;
    }

    // Клиент для второго API (RAWG)
    public static Retrofit getClient2() {
        if (retrofit2 == null) {
            retrofit2 = createRetrofitInstance(BASE_URL_2);
        }
        return retrofit2;
    }

    // Общий метод для создания Retrofit-экземпляров
    private static Retrofit createRetrofitInstance(String baseUrl) {
        try {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize Retrofit for URL: " + baseUrl);
        }
    }

    // Удобные методы для получения API-сервисов
    public static GameApi getGameApi() {
        return getClient1().create(GameApi.class);
    }

    public static NewsApiService getGameNews() {
        return getClient2().create(NewsApiService.class);
    }
    public static DiscountApi getDiscountApi() {
        return getClient1().create(DiscountApi.class);
    }

}