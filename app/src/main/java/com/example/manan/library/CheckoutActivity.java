package com.example.manan.library;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manan.library.network.LibraryClient;
import com.example.manan.library.network.ServiceGenerator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    public static String bookID;
    public static Book bookResult;
    LibraryClient libraryClient;
    ProgressDialog spin;
    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent idIntent = getIntent();
        bookID = idIntent.getStringExtra("bookID");

        libraryClient = ServiceGenerator.createService(LibraryClient.class);

        getBook();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkout_menu, menu);
        MenuItem item = menu.findItem(R.id.share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                shareBook();
                return true;
            case R.id.deleteThis:
                deleteCurrentBook();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getBook() {
        spin = new ProgressDialog(CheckoutActivity.this, ProgressDialog.STYLE_SPINNER);
        spin.setIndeterminate(true);
        spin.setMessage("Retrieving Book");
        spin.show();

        Call<Book> bookDetailsCall = libraryClient.getBookById(bookID);
        bookDetailsCall.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Response<Book> response) {
                if (response.isSuccess()) {
                    spin.dismiss();
                    Book resp = response.body();
                    bookResult = resp;
                    bind(resp);
                } else {
                    spin.dismiss();
                    Toast.makeText(getApplicationContext(), "Error getting Book 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spin.dismiss();
                Toast.makeText(getApplicationContext(), "Error getting Book", Toast.LENGTH_SHORT).show();
                Log.e("CheckoutActivity", t.getMessage());
            }
        });

    }

    public void checkoutBook(View v) {
        if (bookResult == null) {
            Toast.makeText(getApplicationContext(), "Cannot Checkout now", Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
        Date today = Calendar.getInstance().getTime();
        String curDate = sdf.format(today);
        Book checkBook = new BookBuilder()
                .withAuthor(bookResult.getAuthor())
                .withId(bookResult.getId())
                .withCategories(bookResult.getCategories())
                .withPublisher(bookResult.getPublisher())
                .withTitle(bookResult.getTitle())
                .withUrl(bookResult.getUrl())
                .withLastCheckedOut(curDate)
                .withLastCheckedOutBy("Iron Man").build();

        Call<Book> chBookCall = libraryClient.updateBook(bookID, checkBook);
        chBookCall.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Response<Book> response) {
                if (response.isSuccess()) {
                    Book rs = response.body();
                    bookResult = rs;
                    bind(rs);
                    Toast.makeText(getApplicationContext(), "Edited Book", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error Editing Book 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Editing Book", Toast.LENGTH_SHORT).show();
                Log.e("CheckoutActivity", t.getMessage());
            }
        });

    }

    public void shareBook() {
        if (bookResult == null) {
            Toast.makeText(getApplicationContext(), "Cant Share now", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        if (shareActionProvider != null) {
            if (bookResult != null) {
                shareIntent.putExtra("Book", bookResult.toString());
            }
            shareActionProvider.setShareIntent(shareIntent);
        }
    }

    public void deleteCurrentBook() {
        Call<Void> delCurBookCall = libraryClient.deleteBook(bookID);
        delCurBookCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response) {
                if (response.isSuccess()) {
                    Toast.makeText(getApplicationContext(), "Deleted book", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error deleting book", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Error deleting book", Toast.LENGTH_SHORT).show();
                Log.e("CheckoutActivity", t.getMessage());
            }
        });

    }

    public void bind(Book b) {
        TextView titl = (TextView) findViewById(R.id.chTitle);
        titl.setText(b.getTitle());
        TextView auth = (TextView) findViewById(R.id.chAuthor);
        auth.setText(b.getAuthor());
        TextView cat = (TextView) findViewById(R.id.chCat);
        cat.setText(b.getCategories());
        TextView pub = (TextView) findViewById(R.id.chpub);
        pub.setText(b.getPublisher());
        TextView curl = (TextView) findViewById(R.id.churl);
        curl.setText(b.getUrl());
        TextView lastChOut = (TextView) findViewById(R.id.lastCheckout);
        String txt = b.getLastCheckedOutBy() + " @ " + b.getLastCheckedOut();
        lastChOut.setText(txt);
        return;
    }

}
