package com.example.zybertest;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.FrameLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zybertest.Adapters.GameAdapter;
import com.example.zybertest.Models.Game;
import com.example.zybertest.ViewModel.GameViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomSheetDialog bottomSheetDialog;
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private GameViewModel gameViewModel;
    private LinearLayout buttonBar;
    private String currentSearchQuery = "";
    private final Handler searchHandler = new Handler();
    private ProgressBar progressBar; // Добавлен ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализируем ProgressBar
        progressBar = findViewById(R.id.progressBar);

        TextInputEditText searchInput = findViewById(R.id.searchInput);

        searchInput.addTextChangedListener(new TextWatcher() {
            private Runnable searchRunnable;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }

                searchRunnable = () -> {
                    currentSearchQuery = s.toString();
                    filterGames(currentSearchQuery);
                };
                searchHandler.postDelayed(searchRunnable, 300); // задержка 300 мс
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        recyclerView = findViewById(R.id.recyclerView);
        buttonBar = findViewById(R.id.buttonBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Инициализируем адаптер с пустым списком
        gameAdapter = new GameAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(gameAdapter);

        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

        gameViewModel.getGames().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> games) {
                if (games != null) {
                    // Применим фильтр к новым данным
                    filterGames(currentSearchQuery);
                }
            }
        });
    }

    private void filterGames(String query) {
        // Показываем ProgressBar
        progressBar.setVisibility(View.VISIBLE);

        List<Game> allGames = gameViewModel.getGames().getValue();
        if (allGames == null) {
            progressBar.setVisibility(View.GONE); // Скрываем ProgressBar
            return;
        }

        List<Game> filtered = new ArrayList<>();
        for (Game game : allGames) {
            if (game.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(game);
            }
        }

        gameAdapter.setGames(filtered);

        // Скрываем ProgressBar после завершения фильтрации
        progressBar.setVisibility(View.GONE);
    }

    private boolean hasInternetConnection() {
        // Показываем ProgressBar при проверке соединения
        progressBar.setVisibility(View.VISIBLE);

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                progressBar.setVisibility(View.GONE); // Скрываем ProgressBar
                return capabilities != null &&
                        (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
            }
        }

        progressBar.setVisibility(View.GONE); // Скрываем ProgressBar
        return false;
    }

    public void openNews(View view) {
        try {
            Intent intent = new Intent(this, NewsActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка открытия: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    public void openCatalog(View view) {
        try {
            // Если вы уже в MainActivity, не нужно открывать ее снова
            if (!(this instanceof MainActivity)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка открытия: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void openSales(View view) {
        Intent intent = new Intent(this, SalesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right); // вправо
    }


    public void openInfo(View view) {
        try {
            // 1. Создаём BottomSheetDialog с кастомной темой
            BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);

            // 2. Надуваем разметку
            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_info, null);
            dialog.setContentView(dialogView);

            // 3. Настроим отступ сверху = высота кнопочной панели (56dp)
            FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);

                // Ждём, когда View будет готово
                bottomSheet.post(() -> {
                    // Вычисляем 56dp в пикселях
                    int marginTop = (int) (56 * getResources().getDisplayMetrics().density);

                    // Фиксируем начало появления Bottom Sheet
                    behavior.setExpandedOffset(marginTop);

                    // Принудительно раскрываем на всю высоту (минус 56dp)
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                });
            }

            // 4. Показываем диалог
            dialog.show();

        } catch (Exception e) {
            Toast.makeText(this, "Ошибка открытия", Toast.LENGTH_SHORT).show();
            Log.e("BottomSheet", "Error", e);
        }
    }
    // Для тестирования фильтрации
    public void filterGamesForTest(String query, List<Game> source, List<Game> result) {
        result.clear();
        for (Game game : source) {
            if (game.getTitle().toLowerCase().contains(query.toLowerCase())) {
                result.add(game);
            }
        }
    }
}
