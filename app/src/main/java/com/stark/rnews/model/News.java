package com.stark.rnews.model;

import com.google.gson.annotations.SerializedName;

public class News {

    private String title;

    @SerializedName("description")
    private String desc;

    @SerializedName("urlToImage")
    private String imageUrl;

    @SerializedName("url")
    private String newsUrl;

    @SerializedName("content")
    private String bottomHead;

    private String publishedAt;

    private String author;

    private String tune;

    private SourceModel source;

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public SourceModel getSource() {
        return source;
    }

    public void setSource(SourceModel source) {
        this.source = source;
    }

    public News() {

    }

    public News(String title, String desc, String imageUrl, String newsUrl, String bottomHead, String tune) {
        this.title = title;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.newsUrl = newsUrl;
        this.bottomHead = bottomHead;
        this.tune = tune;
    }

    public String getBottomHead() {
        return bottomHead;
    }

    public void setBottomHead(String bottomHead) {
        this.bottomHead = bottomHead;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getTune() {
        return tune;
    }

    public void setTune(String tune) {
        this.tune = tune;
    }
}
