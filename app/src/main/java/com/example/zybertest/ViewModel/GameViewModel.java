package com.example.zybertest.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.zybertest.Models.DiscountDisplay;
import com.example.zybertest.Models.Discount;
import com.example.zybertest.Models.Game;
import com.example.zybertest.Repository.GameRepository;

import java.util.List;

public class GameViewModel extends ViewModel {
    private final GameRepository repository;
    private final LiveData<List<Game>> games;

    public GameViewModel() {
        repository = new GameRepository();
        games = repository.getGames();
    }

    public LiveData<List<Game>> getGames() {
        return games;
    }
    public LiveData<List<DiscountDisplay>> getActiveDiscounts() {
        return repository.getActiveDiscounts(); // теперь типы совпадают ✅
    }
}
