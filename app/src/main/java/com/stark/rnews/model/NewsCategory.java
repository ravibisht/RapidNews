package com.stark.rnews.model;

public class NewsCategory {

    private String imageUrl;
    private String categoryName;

    public NewsCategory() {

    }

    public NewsCategory(String imageUrl, String categoryName) {
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
