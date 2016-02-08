package com.example.manan.library;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {
    private List<Book> books;

    public BookListAdapter(List<Book> books) {
        this.books = books;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book b = books.get(position);
        holder.itemTitle.setText(b.getTitle());
        holder.itemAuthor.setText(b.getAuthor());
    }

    @Override
    public int getItemCount() {
        return this.books.size();
    }

    public void setList(List<Book> list) {
        this.books = list;
        this.notifyDataSetChanged();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;
        TextView itemAuthor;

        public BookViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
            itemAuthor = (TextView) itemView.findViewById(R.id.itemAuthor);
        }
    }
}
