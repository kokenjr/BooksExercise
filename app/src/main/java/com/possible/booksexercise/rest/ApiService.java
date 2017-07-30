package com.possible.booksexercise.rest;

import com.possible.booksexercise.domain.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by korji on 7/29/17.
 */

public interface ApiService {
    @GET("books.json")
    Call<List<Book>> getBooks();
}
