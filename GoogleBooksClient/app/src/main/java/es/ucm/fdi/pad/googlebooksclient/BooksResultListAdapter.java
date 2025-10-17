package es.ucm.fdi.pad.googlebooksclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BooksResultListAdapter extends RecyclerView.Adapter<BooksResultListAdapter.ViewHolder> {
    private List<BookInfo> mBooksData = new ArrayList<>();
    private final LayoutInflater mInflater;

    public BooksResultListAdapter(List<BookInfo> bookInfoArrayList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mBooksData = bookInfoArrayList;
    }

    public void setBooksData(List<BookInfo> books) {
        this.mBooksData = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.book_list_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookInfo bookInfo = mBooksData.get(position);
        holder.title.setText(bookInfo.getTitle());

        List<String> authors = bookInfo.getAuthors();
        holder.authors.setText(authors != null ? String.join(", ", authors) : "Autor desconocido");

        String numPagesString = holder.itemView.getContext().getString(R.string.numero_paginas_string);
        String pages = numPagesString + " " + bookInfo.getPages();
        holder.pages.setText(pages);

        String s = bookInfo.getThumbnail();
        if (s != null && !s.isEmpty()) {
            Picasso.get().load(s)
                    .placeholder(R.drawable.placeholder_book)
                    .error(R.drawable.menu_book)
                    .into(holder.bookImg);
        } else {
            holder.bookImg.setImageResource(R.drawable.placeholder_book);
        }

        holder.itemView.setOnClickListener(view -> {
            Log.i("BookLink", String.valueOf(bookInfo.getInfoLink()));
            Uri bookUri = Uri.parse(String.valueOf(bookInfo.getInfoLink()));
            Intent intent = new Intent(Intent.ACTION_VIEW, bookUri);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mBooksData != null ? mBooksData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImg;
        TextView title, authors, pages;

        public ViewHolder(View view) {
            super(view);
            bookImg = view.findViewById(R.id.book_thumbnail);
            title = view.findViewById(R.id.book_title);
            authors = view.findViewById(R.id.book_authors);
            pages = view.findViewById(R.id.book_publisher);
        }
    }
}
