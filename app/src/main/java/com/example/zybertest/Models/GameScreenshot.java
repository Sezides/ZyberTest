package com.example.zybertest.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GameScreenshot implements Serializable {
    @SerializedName("image_url")
    private String image_url;

    @SerializedName("iscover")
    private boolean iscover;

    @SerializedName("orientation")
    private String orientation;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public boolean iscover() {
        return iscover;
    }

    public void setIscover(boolean iscover) {
        this.iscover = iscover;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
}