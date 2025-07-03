package com.example.zybertest.Models;

import java.io.Serializable;
import java.util.List;

public class Game implements Serializable {
    private int game_id;
    private String title;
    private String description;
    private String age_rating;
    private String release_date;
    private String price;
    private int genre_id;
    private int developer_id;
    private int publisher_id;
    private String created_at;
    private String updated_at;
    private boolean is_active;
    private String about;
    private Genre Genre;
    private Developer Developer;
    private Publisher Publisher;
    private List<GameScreenshot> screenshots; // Use standalone GameScreenshot
    private List<Discount> Discounts;
    private List<Tag> Tags;
    private List<Platform> Platforms;

    // Getters and setters
    public int getGame_id() { return game_id; }
    public void setGame_id(int game_id) { this.game_id = game_id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAge_rating() { return age_rating; }
    public void setAge_rating(String age_rating) { this.age_rating = age_rating; }
    public String getRelease_date() { return release_date; }
    public void setRelease_date(String release_date) { this.release_date = release_date; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public int getGenre_id() { return genre_id; }
    public void setGenre_id(int genre_id) { this.genre_id = genre_id; }
    public int getDeveloper_id() { return developer_id; }
    public void setDeveloper_id(int developer_id) { this.developer_id = developer_id; }
    public int getPublisher_id() { return publisher_id; }
    public void setPublisher_id(int publisher_id) { this.publisher_id = publisher_id; }
    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }
    public String getUpdated_at() { return updated_at; }
    public void setUpdated_at(String updated_at) { this.updated_at = updated_at; }
    public boolean isIs_active() { return is_active; }
    public void setIs_active(boolean is_active) { this.is_active = is_active; }
    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }
    public Genre getGenre() { return Genre; }
    public void setGenre(Genre genre) { this.Genre = genre; }
    public Developer getDeveloper() { return Developer; }
    public void setDeveloper(Developer developer) { this.Developer = developer; }
    public Publisher getPublisher() { return Publisher; }
    public void setPublisher(Publisher publisher) { this.Publisher = publisher; }
    public List<GameScreenshot> getScreenshots() { return screenshots; }
    public void setScreenshots(List<GameScreenshot> screenshots) { this.screenshots = screenshots; }
    public List<Discount> getDiscounts() { return Discounts; }
    public void setDiscounts(List<Discount> discounts) { this.Discounts = discounts; }
    public List<Tag> getTags() { return Tags; }
    public void setTags(List<Tag> tags) { this.Tags = tags; }
    public List<Platform> getPlatforms() { return Platforms; }
    public void setPlatforms(List<Platform> platforms) { this.Platforms = platforms; }

    public static class Genre implements Serializable {
        private String genre_name;
        public String getGenre_name() { return genre_name; }
        public void setGenre_name(String genre_name) { this.genre_name = genre_name; }
    }

    public static class Developer implements Serializable {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public static class Publisher implements Serializable {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public static class Discount implements Serializable {
        private String discount_percentage;
        private String start_date;
        private String end_date;
        public String getDiscount_percentage() { return discount_percentage; }
        public void setDiscount_percentage(String discount_percentage) { this.discount_percentage = discount_percentage; }
        public String getStart_date() { return start_date; }
        public void setStart_date(String start_date) { this.start_date = start_date; }
        public String getEnd_date() { return end_date; }
        public void setEnd_date(String end_date) { this.end_date = end_date; }
    }

    public static class Tag implements Serializable {
        private String tag_name;
        public String getTag_name() { return tag_name; }
        public void setTag_name(String tag_name) { this.tag_name = tag_name; }
    }

    public static class Platform implements Serializable {
        private String platform_name;
        public String getPlatform_name() { return platform_name; }
        public void setPlatform_name(String platform_name) { this.platform_name = platform_name; }
    }
}