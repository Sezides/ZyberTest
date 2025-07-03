package com.example.zybertest.API;

import com.example.zybertest.Models.Game;
import com.example.zybertest.Models.Discount;
import com.example.zybertest.Models.DiscountDisplay;
import com.example.zybertest.Models.ActiveDiscount;
import java.util.List;
import retrofit2.http.Path;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GameApi {
    @GET("api/discounts/active")
    Call<List<Discount>> getActiveDiscounts();
    @GET("api/games")
    Call<List<Game>> getGames();  // Запрос без параметров
    @GET("api/games/{id}")
    Call<Game> getGameById(@Path("id") int gameId);
}
