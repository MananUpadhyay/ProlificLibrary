package com.example.manan.library;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {
    Context mContext;
    private List<Book> books;
    private ItemClickListener itemClickListener;

    public BookListAdapter(Context ctx, List<Book> books) {
        this.books = books;
        this.mContext = ctx;
    }

    public void setItemClickListener(final ItemClickListener listener) {
        this.itemClickListener = listener;
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
        holder.id.setText(b.getId());
    }

    @Override
    public int getItemCount() {
        return this.books.size();
    }

    public void setList(List<Book> list) {
        this.books = list;
        this.notifyDataSetChanged();
    }


    public interface ItemClickListener {
        void onItemClick(View v);
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;
        TextView itemAuthor;
        TextView id;

        public BookViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
            itemAuthor = (TextView) itemView.findViewById(R.id.itemAuthor);
            id = (TextView) itemView.findViewById(R.id.hiddenId);
            itemView.setClickable(true);
            if (itemClickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemClickListener.onItemClick(view);
                    }
                });
            }
        }
    }

}
