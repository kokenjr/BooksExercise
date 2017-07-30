package com.possible.booksexercise.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by korji on 7/29/17.
 */

public class Book {
    private String title;
    private String author;
    @SerializedName("imageURL")
    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
