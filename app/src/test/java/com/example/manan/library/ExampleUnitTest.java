package com.example.manan.library;

import android.util.Log;

import com.example.manan.library.network.LibraryClient;
import com.example.manan.library.network.ServiceGenerator;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

public class ExampleUnitTest {
    @Test
    public void testRetrofit() throws IOException {
//
//        LibraryClient lib = ServiceGenerator.createService(LibraryClient.class);
//        Call<List<Book>> bookCall = lib.getAllBooks();
//        bookCall.enqueue(new Callback<List<Book>>() {
//            @Override
//            public void onResponse(Response<List<Book>> response) {
//                System.out.println(response.code());
//                System.out.println(response.body().size());
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.e("Error", t.getMessage());
//            }
//        });
//        List<Book> list = bookCall.execute().body();
//        System.out.print(list.get(0).toString());
        assertTrue(true);
    }

//    @Test
//    public void testAddBook() {
//        Book b = new BookBuilder().withAuthor("Manan").withTitle("Hello").createBook();
//        LibraryClient lib = ServiceGenerator.createService(LibraryClient.class);
//        Call<Book> res = lib.addBook(b);
//        res.enqueue(new Callback<Book>() {
//            @Override
//            public void onResponse(Response<Book> response) {
//                if (response.isSuccess()) {
//                    System.out.println(response.toString());
//                } else {
//                    Log.d("Error", String.valueOf(response.code()));
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.e("Error", t.getMessage());
//            }
//        });
//        assertTrue(true);
//    }
}