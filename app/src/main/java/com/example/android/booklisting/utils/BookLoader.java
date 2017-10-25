package com.example.android.booklisting.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.android.booklisting.model.Book;

/**
 * Created by sadin on 25-Oct-17.
 */

public class BookLoader extends AsyncTaskLoader<Book> {
    private static final String TAG = BookLoader.class.getSimpleName();
    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }


    @Override
    protected void onStartLoading() {
        Log.i(TAG, "onStartLoading");
        forceLoad();
    }

    @Override
    public Book loadInBackground() {
        Log.i(TAG, "loadInBackground");
        if (mUrl == null) {
            return null;
        }
        return HttpQuery.fetchBookData(mUrl);
    }
}
