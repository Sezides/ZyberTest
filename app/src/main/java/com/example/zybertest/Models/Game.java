package com.example.zybertest.Models;

import java.util.List;

public class Game {
    private int game_id;
    private String title;
    private String description;
    private String age_rating;
    private String release_date;
    private String price;
    private Genre Genre;
    private Developer Developer;
    private Publisher Publisher;
    private List<GameScreenshot> screenshots;
    private List<Discount> Discounts;
    private List<Tag> Tags;
    private List<Platform> Platforms;

    // Геттеры и сеттеры
    public int getGame_id() { return game_id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getAge_rating() { return age_rating; }
    public String getRelease_date() { return release_date; }
    public String getPrice() { return price; }
    public Genre getGenre() { return Genre; }
    public Developer getDeveloper() { return Developer; }
    public Publisher getPublisher() { return Publisher; }
    public List<GameScreenshot> getScreenshots() { return screenshots; }
    public List<Discount> getDiscounts() { return Discounts; }
    public List<Tag> getTags() { return Tags; }
    public List<Platform> getPlatforms() { return Platforms; }

}
