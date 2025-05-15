package com.example.zybertest.Models;

import com.google.gson.annotations.SerializedName;

public class Discount {
    @SerializedName("discountPercentage")
    private String discount_percentage;

    @SerializedName("startDate")
    private String start_date;

    @SerializedName("endDate")
    private String end_date;

    @SerializedName("Game") // ⚠️ ОБЯЗАТЕЛЬНО!
    private Game game;

    public String getDiscount_percentage() {
        return discount_percentage;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public Game getGame() {
        return game;
    }
}
