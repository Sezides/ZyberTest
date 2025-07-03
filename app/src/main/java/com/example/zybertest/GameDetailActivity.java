package com.example.zybertest;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zybertest.Adapters.ScreenshotAdapter;
import com.example.zybertest.Models.Game;
import com.example.zybertest.Models.GameScreenshot;
import com.example.zybertest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameDetailActivity extends AppCompatActivity {

    private ImageView gameCover;
    private TextView gameTitle, gamePrice, gameGenre, gameDescription, gameAgeRating, gameReleaseDate, gameDeveloper, gamePublisher, gameTags, gamePlatforms;
    private RecyclerView screenshotsRecycler;
    private ScreenshotAdapter screenshotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_detail_activity);

        // Инициализация Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Инициализация view
        gameCover = findViewById(R.id.game_cover);
        gameTitle = findViewById(R.id.game_title);
        gamePrice = findViewById(R.id.game_price);
        gameGenre = findViewById(R.id.game_genre);
        gameDescription = findViewById(R.id.game_description);
        gameAgeRating = findViewById(R.id.game_age_rating);
        gameReleaseDate = findViewById(R.id.game_release_date);
        gameDeveloper = findViewById(R.id.game_developer);
        gamePublisher = findViewById(R.id.game_publisher);
        gameTags = findViewById(R.id.game_tags);
        gamePlatforms = findViewById(R.id.game_platforms);
        screenshotsRecycler = findViewById(R.id.screenshots_recycler);

        // Получение объекта Game из Intent
        Game game = (Game) getIntent().getSerializableExtra("game");

        if (game != null) {
            // Установка данных
            gameTitle.setText(game.getTitle());

            // Цена с учетом скидки
            String displayPrice;
            try {
                double originalPrice = Double.parseDouble(game.getPrice());
                if (game.getDiscounts() != null && !game.getDiscounts().isEmpty()) {
                    Game.Discount discount = game.getDiscounts().get(0); // Берем первую скидку
                    double discountPercentage = Double.parseDouble(discount.getDiscount_percentage());
                    double discountedPrice = originalPrice * (1 - discountPercentage / 100);
                    displayPrice = String.format("%.2f ₽ (Скидка: %s%%)", discountedPrice, discount.getDiscount_percentage());
                } else {
                    displayPrice = String.format("%.2f ₽", originalPrice);
                }
            } catch (NumberFormatException e) {
                displayPrice = game.getPrice() + " ₽";
            }
            gamePrice.setText(displayPrice);

            // Жанр
            if (game.getGenre() != null) {
                gameGenre.setText(game.getGenre().getGenre_name());
            }

            // Возрастной рейтинг
            gameAgeRating.setText("Возрастной рейтинг: " + (game.getAge_rating() != null ? game.getAge_rating() : "Не указан"));

            // Дата релиза
            gameReleaseDate.setText("Дата релиза: " + (game.getRelease_date() != null ? game.getRelease_date() : "Не указана"));

            // Разработчик
            gameDeveloper.setText("Разработчик: " + (game.getDeveloper() != null ? game.getDeveloper().getName() : "Не указан"));

            // Издатель
            gamePublisher.setText("Издатель: " + (game.getPublisher() != null ? game.getPublisher().getName() : "Не указан"));

            // Теги
            if (game.getTags() != null && !game.getTags().isEmpty()) {
                String tagsText = game.getTags().stream().map(Game.Tag::getTag_name).collect(Collectors.joining(", "));
                gameTags.setText("Теги: " + tagsText);
            } else {
                gameTags.setText("Теги: Не указаны");
            }

            // Платформы
            if (game.getPlatforms() != null && !game.getPlatforms().isEmpty()) {
                String platformsText = game.getPlatforms().stream().map(Game.Platform::getPlatform_name).collect(Collectors.joining(", "));
                gamePlatforms.setText("Платформы: " + platformsText);
            } else {
                gamePlatforms.setText("Платформы: Не указаны");
            }

            // Описание (HTML)
            if (game.getAbout() != null) {
                gameDescription.setText(Html.fromHtml(game.getAbout(), Html.FROM_HTML_MODE_LEGACY));
            } else if (game.getDescription() != null) {
                gameDescription.setText(game.getDescription());
            } else {
                gameDescription.setText("Описание отсутствует");
            }

            // Обложка
            if (game.getScreenshots() != null && !game.getScreenshots().isEmpty()) {
                String imageUrl = null;
                for (GameScreenshot screenshot : game.getScreenshots()) {
                    if (screenshot.iscover() && "horizontal".equalsIgnoreCase(screenshot.getOrientation())) {
                        imageUrl = screenshot.getImage_url();
                        break;
                    }
                }
                if (imageUrl != null) {
                    Picasso.get()
                            .load(imageUrl)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(gameCover);
                } else {
                    gameCover.setImageResource(R.drawable.ic_launcher_background);
                }
            }

            // Скриншоты
            if (game.getScreenshots() != null) {
                screenshotsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                screenshotAdapter = new ScreenshotAdapter(this, game.getScreenshots());
                screenshotsRecycler.setAdapter(screenshotAdapter);
            }
        }

        // Обработка кнопки "Назад"
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}