package com.example.zybertest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zybertest.GameDetailActivity;
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

    class GameViewHolder extends RecyclerView.ViewHolder {
        ImageView gameCover;
        TextView gameTitle, gamePrice, gameGenre;

        GameViewHolder(@NonNull View itemView) {
            super(itemView);
            gameCover = itemView.findViewById(R.id.game_cover);
            gameTitle = itemView.findViewById(R.id.game_title);
            gamePrice = itemView.findViewById(R.id.game_price);
            gameGenre = itemView.findViewById(R.id.game_genre);

            // Удалено: инициализация RecyclerView для скриншотов

            // Обработка клика по карточке
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, GameDetailActivity.class);
                intent.putExtra("game", games.get(getAdapterPosition()));
                context.startActivity(intent);
            });
        }

        void bind(Game game) {
            gameTitle.setText(game.getTitle());

            // Цена с учетом скидки
            String displayPrice;
            try {
                double originalPrice = Double.parseDouble(game.getPrice());
                if (game.getDiscounts() != null && !game.getDiscounts().isEmpty()) {
                    Game.Discount discount = game.getDiscounts().get(0);
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

            // Удалено: обновление списка скриншотов
        }
    }
}