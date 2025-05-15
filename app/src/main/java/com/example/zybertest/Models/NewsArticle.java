package com.example.zybertest.Models;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class NewsArticle implements Serializable {
    private String title;
    private String description;
    private String content;

    @SerializedName("urlToImage")
    private String imageUrl;

    @SerializedName("publishedAt")
    private String date;

    private String url; // Оставляем, но не используем для прямого открытия

    // Геттеры
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
    public String getDate() { return date; }
    public String getUrl() { return url; }

    // Parcelable реализация для передачи между активити
    protected NewsArticle(Parcel in) {
        title = in.readString();
        description = in.readString();
        content = in.readString();
        imageUrl = in.readString();
        date = in.readString();
        url = in.readString();
    }

    public static final Parcelable.Creator<NewsArticle> CREATOR = new Parcelable.Creator<NewsArticle>() {
        @Override
        public NewsArticle createFromParcel(Parcel in) {
            return new NewsArticle(in);
        }

        @Override
        public NewsArticle[] newArray(int size) {
            return new NewsArticle[size];
        }
    };
}