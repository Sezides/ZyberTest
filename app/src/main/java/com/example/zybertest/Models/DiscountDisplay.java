package com.example.zybertest.Models;

public class DiscountDisplay {
    private String gameTitle;
    private String imageUrl;
    private String discountPercentage;
    private String startDate;
    private String endDate;

    public DiscountDisplay(String gameTitle, String imageUrl, String discountPercentage, String startDate, String endDate) {
        this.gameTitle = gameTitle;
        this.imageUrl = imageUrl;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getGameTitle() { return gameTitle; }
    public String getImageUrl() { return imageUrl; }
    public String getDiscountPercentage() { return discountPercentage; }
    public String getStartDate() { return startDate; } // ← добавь
    public String getEndDate() { return endDate; }
}
