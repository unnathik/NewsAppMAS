package com.example.newsapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllNews {
    @SerializedName("status")
    private String status;

    @SerializedName("totalResults")
    private int totalResults;

    @SerializedName("articles")
    private List<NewsArticle> newsList;

    public List<NewsArticle> getNewsList() {
        return newsList;
    }
}
