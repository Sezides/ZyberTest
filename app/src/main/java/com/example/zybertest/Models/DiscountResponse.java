package com.example.zybertest.Models;

import com.google.gson.annotations.SerializedName;

public class DiscountResponse {
    @SerializedName("discountPercentage")
    private int discountPercentage;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("Game")
    private Game game;

    public int getDiscountPercentage() { return discountPercentage; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public Game getGame() { return game; }
}
