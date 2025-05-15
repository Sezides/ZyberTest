package com.example.zybertest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.zybertest.R;
import com.example.zybertest.NewsDetailActivity;
import com.example.zybertest.Models.NewsArticle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private final List<NewsArticle> articles;
    private final Context context;

    public NewsAdapter(List<NewsArticle> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {  // Изменили NewsViewHolder на ViewHolder
        NewsArticle article = articles.get(position);

        holder.title.setText(article.getTitle());
        // Добавляем отображение описания, если у вас есть соответствующее поле в layout
        if (holder.description != null) {
            holder.description.setText(article.getDescription());
        }

        // Форматированная дата
        holder.date.setText(formatDate(article.getDate()));

        // Загрузка изображения через Glide
        Glide.with(context)
                .load(article.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra("article", article);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    private String formatDate(String rawDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
            Date date = inputFormat.parse(rawDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return rawDate; // Возвращаем исходную дату при ошибке
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView image;
        final TextView title;
        final TextView date;
        final TextView description; // Добавляем, если есть в layout

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.news_image);
            title = itemView.findViewById(R.id.news_title);
            date = itemView.findViewById(R.id.news_date);
            // Если у вас есть TextView для описания в item_news.xml
            description = itemView.findViewById(R.id.news_description);
        }
    }
}