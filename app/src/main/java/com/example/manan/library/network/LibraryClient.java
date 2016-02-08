package com.example.manan.library.network;


import com.example.manan.library.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LibraryClient {

    @GET("books")
    Call<List<Book>> getAllBooks();

    @GET("books/{id}")
    Call<Book> getBookById(@Path("id") String id);

    @POST("books")
    Call<Book> addBook(@Body Book book);

    @PUT("books/{id}")
    Call<Book> updateBook(@Path("id") String id, @Body Book book);

    @DELETE("books/{id}")
    Call<Book> deleteBook(@Path("id") String id);

    @DELETE("clean")
    void deleteAll();

}
