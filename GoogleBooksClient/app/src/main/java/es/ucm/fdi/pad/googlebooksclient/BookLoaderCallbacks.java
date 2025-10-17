package es.ucm.fdi.pad.googlebooksclient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>> {

    public static final String EXTRA_QUERY = "QUERY";
    public static final String EXTRA_PRINT_TYPE = "PRINT_TYPE";
    private MainActivity mainActivity;

    public BookLoaderCallbacks(MainActivity activity) {
        this.mainActivity = activity;
    }

    @NonNull
    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, Bundle args) {
        String query = args.getString(EXTRA_QUERY, "");
        String printType = args.getString(EXTRA_PRINT_TYPE, "books");
        return new BookLoader(mainActivity, query, printType);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<BookInfo>> loader, List<BookInfo> data) {
        mainActivity.updateBooksResultList(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<BookInfo>> loader) {
       // mainActivity.updateBooksResultList(new ArrayList<BookInfo>());
    }
}
