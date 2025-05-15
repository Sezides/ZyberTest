package com.example.zybertest.API;

import com.example.zybertest.Models.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    @GET("v2/everything")
    Call<NewsResponse> getGameNews(
            @Query("q") String query,
            @Query("domains") String domains,
            @Query("language") String language,
            @Query("apiKey") String apiKey,
            @Query("pageSize") int pageSize,
            @Query("sortBy") String sortBy
    );
}
