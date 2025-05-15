package com.example.zybertest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.example.zybertest.Models.DiscountDisplay;
import com.example.zybertest.Models.Game;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zybertest.Adapters.DiscountAdapter;
import com.example.zybertest.Models.DiscountDisplay;
import com.example.zybertest.ViewModel.GameViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class SalesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DiscountAdapter discountAdapter;
    private GameViewModel gameViewModel;
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        recyclerView = findViewById(R.id.recyclerViewSales);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        discountAdapter = new DiscountAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(discountAdapter);
        progressBar = findViewById(R.id.progressBar);

        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
        progressBar.setVisibility(View.VISIBLE);

        // Две переменные, чтобы дождаться обоих запросов
        final List<DiscountDisplay>[] discountsHolder = new List[]{null};
        final List<Game>[] gamesHolder = new List[]{null};

        // Когда оба получены — отображаем
        Runnable tryDisplay = () -> {
            if (discountsHolder[0] != null && gamesHolder[0] != null) {
                // Здесь можно объединять discounts и games по game_id, если нужно
                discountAdapter.setDiscounts(discountsHolder[0]);
                progressBar.setVisibility(View.GONE);
            }
        };

        // Наблюдение за активными скидками
        gameViewModel.getActiveDiscounts().observe(this, discounts -> {
            discountsHolder[0] = discounts;
            tryDisplay.run();
        });

        // Наблюдение за всеми играми
        gameViewModel.getGames().observe(this, games -> {
            gamesHolder[0] = games;
            tryDisplay.run();
        });
    }

    public void openInfo(View view) {
        try {
            BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_info, null);
            dialog.setContentView(dialogView);

            FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheet.post(() -> {
                    int marginTop = (int) (56 * getResources().getDisplayMetrics().density);
                    behavior.setExpandedOffset(marginTop);
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                });
            }
            dialog.show();
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка открытия", Toast.LENGTH_SHORT).show();
            Log.e("BottomSheet", "Error", e);
        }
    }

    private boolean hasInternetConnection() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkCapabilities capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
        }
        return false;
    }

    public void openCatalog(View view) {
        if (hasInternetConnection()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(this, "Нет интернет-соединения", Toast.LENGTH_SHORT).show();
        }
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    public void openNews(View view) {
        try {
            Intent intent = new Intent(this, NewsActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка открытия: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    public void openSales(View view) {
        Intent intent = getIntent();
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
