package com.example.android.booklistingapp;

/**
 * Created by Rafael on 17/2/2018.
 */

public class Book {

    String mTitle;
    String mPublishedDate;
    String mDescription;
    String mPageCount;
    String mThumbnail;
    String mAuthors;

    public Book(String title, String publishedDate, String description, String pageCount, String thumbnail, String authors) {
        mTitle = title;
        mPublishedDate = publishedDate;
        mDescription = description;
        mPageCount = pageCount;
        mThumbnail = thumbnail;
        mAuthors = authors;
    }
}
