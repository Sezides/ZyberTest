package com.example.zybertest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zybertest.Models.Game;
import com.example.zybertest.Models.GameScreenshot;
import com.example.zybertest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private List<Game> games;
    private Context context;

    public GameAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = games.get(position);
        holder.bind(game);
    }

    @Override
    public int getItemCount() {
        return games != null ? games.size() : 0;
    }

    public void setGames(List<Game> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    // Вложенный класс для скриншотов
    private static class ScreenshotAdapter extends RecyclerView.Adapter<ScreenshotAdapter.ScreenshotViewHolder> {
        private List<GameScreenshot> screenshots;
        private Context context;

        public ScreenshotAdapter(Context context, List<GameScreenshot> screenshots) {
            this.context = context;
            this.screenshots = screenshots;
        }

        @NonNull
        @Override
        public ScreenshotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_screenshot, parent, false);
            return new ScreenshotViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ScreenshotViewHolder holder, int position) {
            GameScreenshot screenshot = screenshots.get(position);
            Picasso.get()
                    .load(screenshot.getImage_url())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.screenshotImage);
        }

        @Override
        public int getItemCount() {
            return screenshots != null ? screenshots.size() : 0;
        }

        public void setScreenshots(List<GameScreenshot> screenshots) {
            this.screenshots = screenshots;
            notifyDataSetChanged();
        }

        static class ScreenshotViewHolder extends RecyclerView.ViewHolder {
            ImageView screenshotImage;

            ScreenshotViewHolder(@NonNull View itemView) {
                super(itemView);
                screenshotImage = itemView.findViewById(R.id.screenshot_image);
            }
        }
    }

    class GameViewHolder extends RecyclerView.ViewHolder {
        ImageView gameCover;
        TextView gameTitle, gamePrice, gameGenre;
        RecyclerView screenshotsRecycler;
        ScreenshotAdapter screenshotAdapter;

        GameViewHolder(@NonNull View itemView) {
            super(itemView);
            gameCover = itemView.findViewById(R.id.game_cover);
            gameTitle = itemView.findViewById(R.id.game_title);
            gamePrice = itemView.findViewById(R.id.game_price);
            gameGenre = itemView.findViewById(R.id.game_genre);
            screenshotsRecycler = itemView.findViewById(R.id.screenshots_recycler);

            // Настройка адаптера для скриншотов
            screenshotAdapter = new ScreenshotAdapter(context, null);
            screenshotsRecycler.setLayoutManager(new LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
            ));
            screenshotsRecycler.setAdapter(screenshotAdapter);
        }

        void bind(Game game) {
            gameTitle.setText(game.getTitle());
            gamePrice.setText(game.getPrice() + " руб.");

            if (game.getGenre() != null) {
                gameGenre.setText(game.getGenre().getGenre_name());
            }

            // Обложка: ищем только вертикальную и iscover = true
            if (game.getScreenshots() != null && !game.getScreenshots().isEmpty()) {
                String imageUrl = null;
                for (GameScreenshot screenshot : game.getScreenshots()) {
                    if (screenshot.iscover() && "Vertical".equalsIgnoreCase(screenshot.getOrientation())) {
                        imageUrl = screenshot.getImage_url();
                        break;
                    }
                }

                // Если подходящей обложки нет, ставим заглушку
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

            // Скриншоты: без iscover и без вертикальных
            if (game.getScreenshots() != null) {
                List<GameScreenshot> filtered = new java.util.ArrayList<>();
                for (GameScreenshot s : game.getScreenshots()) {
                    if (!s.iscover() && !"Vertical".equalsIgnoreCase(s.getOrientation())) {
                        filtered.add(s);
                    }
                }
                screenshotAdapter.setScreenshots(filtered);
            }
        }

    }

}
