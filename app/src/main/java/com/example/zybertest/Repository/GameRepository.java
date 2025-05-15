package com.example.zybertest.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.zybertest.API.DiscountApi;
import com.example.zybertest.API.GameApi;
import com.example.zybertest.API.RetrofitClient;
import com.example.zybertest.Models.Discount;
import com.example.zybertest.Models.DiscountDisplay;
import com.example.zybertest.Models.Game;
import com.example.zybertest.Models.GameScreenshot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameRepository {
    private final GameApi gameApi;

    public GameRepository() {
        gameApi = RetrofitClient.getGameApi(); // подключаем GameApi
    }

    // Загрузка всех игр
    public LiveData<List<Game>> getGames() {
        MutableLiveData<List<Game>> gamesLiveData = new MutableLiveData<>();

        gameApi.getGames().enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful()) {
                    Log.d("REPO", "✅ Загружено игр: " + response.body().size());
                    gamesLiveData.setValue(response.body());
                } else {
                    Log.e("REPO", "❌ Ошибка загрузки игр: response not successful");
                    gamesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e("REPO", "💥 Ошибка запроса игр", t);
                gamesLiveData.setValue(null);
            }
        });

        return gamesLiveData;
    }

    // Загрузка активных скидок
    public LiveData<List<DiscountDisplay>> getActiveDiscounts() {
        MutableLiveData<List<DiscountDisplay>> discountsLiveData = new MutableLiveData<>();

        RetrofitClient.getDiscountApi().getActiveDiscounts().enqueue(new Callback<List<Discount>>() {
            @Override
            public void onResponse(Call<List<Discount>> call, Response<List<Discount>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DiscountDisplay> result = new ArrayList<>();
                    Log.d("REPO", "✅ Пришло скидок: " + response.body().size());

                    for (Discount discount : response.body()) {
                        Game game = discount.getGame();
                        if (game != null) {
                            String imageUrl = null;
                            if (game.getScreenshots() != null) {
                                for (GameScreenshot s : game.getScreenshots()) {
                                    if ("horizontal".equalsIgnoreCase(s.getOrientation()) && s.iscover()) {
                                        imageUrl = s.getImage_url();
                                        break;
                                    }
                                }
                            }

                            Log.d("REPO", "✔ Добавляю скидку: " + game.getTitle());

                            result.add(new DiscountDisplay(
                                    game.getTitle(),
                                    imageUrl,
                                    discount.getDiscount_percentage(),
                                    discount.getStart_date(),
                                    discount.getEnd_date()
                            ));
                        } else {
                            Log.w("REPO", "⚠ Скидка без связанной игры пропущена");
                        }
                    }

                    discountsLiveData.setValue(result);
                } else {
                    Log.e("REPO", "❌ Пустой или неуспешный ответ от Discount API");
                    discountsLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Discount>> call, Throwable t) {
                Log.e("REPO", "💥 Ошибка запроса скидок", t);
                discountsLiveData.setValue(null);
            }
        });

        return discountsLiveData;
    }
}
