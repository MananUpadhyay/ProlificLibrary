package com.example.manan.library;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manan.library.network.LibraryClient;
import com.example.manan.library.network.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements AddBookDialogFragment.AddBookDialogListener {
    public static List<Book> bookList;
    public LibraryClient libraryClient;
    public BookListAdapter bookListAdapter;
    ProgressDialog spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        libraryClient = ServiceGenerator.createService(LibraryClient.class);


        configureRecyclerView();
        retrieveBooks();

        // Modal for adding new books.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new AddBookDialogFragment();
                dialog.show(getFragmentManager(), "AddBookDialog");
            }
        });
    }


    @Override
    public void onAddBookDialogPositiveClick(DialogFragment dialog) {
        Dialog addBookDialog = dialog.getDialog();
        EditText author = (EditText) addBookDialog.findViewById(R.id.author);
        EditText title = (EditText) addBookDialog.findViewById(R.id.title);
        EditText pub = (EditText) addBookDialog.findViewById(R.id.pub);
        EditText cat = (EditText) addBookDialog.findViewById(R.id.cat);
        Book b = new BookBuilder()
                .withAuthor(author.getText().toString())
                .withTitle(title.getText().toString())
                .withPublisher(pub.getText().toString())
                .withCategories(cat.getText().toString())
                .build();

        Call<Book> bookCall = libraryClient.addBook(b);
        bookCall.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Response<Book> response) {
                if (response.isSuccess()) {
                    Book result = response.body();
                    bookList.add(result);
                    bookListAdapter.setList(bookList);
                    Snackbar.make(findViewById(R.id.rootLayout), "New Book Added", Snackbar.LENGTH_SHORT).show();
                } else {
                    Log.d("Error", String.valueOf(response.code()));
                    Snackbar.make(findViewById(R.id.rootLayout), "Error Adding New Book", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(R.id.rootLayout), "Error Adding New Book", Snackbar.LENGTH_LONG).show();
                Log.e("Error Failed", t.getMessage());
            }
        });
    }

    public void retrieveBooks() {
        // Retrieve existing books.
        spin = new ProgressDialog(MainActivity.this, ProgressDialog.STYLE_SPINNER);
        spin.setIndeterminate(true);
        spin.setMessage("Retrieving Books");
        spin.show();

        Call<List<Book>> allBooksCall = libraryClient.getAllBooks();
        allBooksCall.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Response<List<Book>> response) {
                if (response.isSuccess()) {
                    bookList = response.body();
                    bookListAdapter.setList(bookList);
                    spin.dismiss();
                    if (bookList.size() == 0) {
                        Toast.makeText(getApplicationContext(), "No Books Present", Toast.LENGTH_LONG).show();
                        Log.d(MainActivity.this.getLocalClassName(), String.valueOf(bookList.size()));
                    }
                } else {
                    spin.dismiss();
                    Snackbar.make(findViewById(R.id.rootLayout), "Error Adding New Book", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spin.dismiss();
                Snackbar.make(findViewById(R.id.rootLayout), "Error Adding New Book", Snackbar.LENGTH_LONG).show();
            }
        });


    }

    public void configureRecyclerView() {
        bookList = new ArrayList<Book>();

        final RecyclerView bookListView = (RecyclerView) findViewById(R.id.bookRecyclerView);
        bookListView.setHasFixedSize(true);
        bookListView.setItemAnimator(new DefaultItemAnimator());

        // configure and add LayoutManager.
        LinearLayoutManager llm = new LinearLayoutManager(getApplication());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        bookListView.setLayoutManager(llm);

        //configure and add adapter.
        bookListAdapter = new BookListAdapter(bookList);
        bookListView.setAdapter(bookListAdapter);

        //configure and add ItemClickListener.
        bookListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
