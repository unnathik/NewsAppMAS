package com.example.newsapp;

import com.google.gson.annotations.SerializedName;

public class NewsArticle {
    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("urlToImage")
    private String imageUrl;

    @SerializedName("url")
    private String url;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

