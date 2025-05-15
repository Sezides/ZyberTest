package com.example.zybertest.Models;

public class ActiveDiscount {
    private String discountPercentage;
    private String startDate;
    private String endDate;
    private Game game;

    public String getDiscountPercentage() { return discountPercentage; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public Game getGame() { return game; }

    public class Game {
        private String gameTitle;
        private String imageUrl;

        public String getGameTitle() { return gameTitle; }
        public String getImageUrl() { return imageUrl; }
    }
}
