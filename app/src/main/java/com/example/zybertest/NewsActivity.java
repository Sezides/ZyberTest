package com.example.zybertest;

import android.net.Uri;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.zybertest.Adapters.NewsAdapter;
import com.example.zybertest.API.NewsApiService;
import com.example.zybertest.Models.NewsArticle;
import com.example.zybertest.Models.NewsResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class NewsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = findViewById(R.id.news_recycler);
        progressBar = findViewById(R.id.news_progress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadGameNews();
    }

    private void loadGameNews() {
        progressBar.setVisibility(View.VISIBLE);

        String query = "игры OR гейминг OR киберспорт";
        String domains = "igromania.ru,stopgame.ru,kanobu.ru,dtf.ru";
        String language = "ru";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsApiService service = retrofit.create(NewsApiService.class);

        service.getGameNews(
                query,
                domains,
                language,
                BuildConfig.NEWS_API_KEY, // Замените на ваш API-ключ
                50,
                "publishedAt"
        ).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<NewsArticle> articles = response.body().getArticles();
                    if (articles.isEmpty()) {
                        showNoResults();
                    } else {
                        NewsAdapter adapter = new NewsAdapter(articles, NewsActivity.this);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    showError("Ошибка: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showError("Ошибка сети: " + t.getMessage());
            }
        });
    }
    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private boolean hasInternetConnection() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    public void openNews(View view) {
        if (hasInternetConnection()) {
            // Просто обновляем данные
            refreshNewsData();
        } else {
            Toast.makeText(this, "Нет интернет-соединения", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshNewsData() {
        progressBar.setVisibility(View.VISIBLE); // Показать перед загрузкой
        loadNewsData(); // Повторно загрузить
        recyclerView.scrollToPosition(0); // Прокрутка вверх
    }
    private void loadNewsData() {
        loadGameNews(); // Заново загружаем новости
    }

    public void openCatalog(View view){
        if (hasInternetConnection()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(this, "Нет интернет-соединения", Toast.LENGTH_SHORT).show();
        }
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    public void openSales(View view) {
        Intent intent = new Intent(this, SalesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    public void openInfo(View view) {
        try {
            // 1. Создаём BottomSheetDialog с кастомной темой
            BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);

            // 2. Надуваем разметку
            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_info, null);
            dialog.setContentView(dialogView);

            // 3. Настраиваем отступ сверху = высота кнопочной панели (56dp)
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

    private void showNoResults() {
        Toast.makeText(this, "Новости не найдены", Toast.LENGTH_SHORT).show();
    }
}
