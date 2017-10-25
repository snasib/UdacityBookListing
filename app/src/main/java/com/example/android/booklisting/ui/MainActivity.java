package com.example.android.booklisting.ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.android.booklisting.R;
import com.example.android.booklisting.adapter.CustomListAdapter;
import com.example.android.booklisting.model.Book;
import com.example.android.booklisting.model.Item;
import com.example.android.booklisting.utils.BookLoader;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Book> {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int BOOK_LOADER_ID = 35;
    private static final String GOOGLE_BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String BOOK_RESULT_COUNT = "&maxResults=10";
    private String mBookUrl = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=10";

    private CustomListAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private ListView mListView;

    private LoaderManager mLoaderManager;
    private static final String LIST_STATE = "listState";
    private Parcelable mListState = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mEmptyStateTextView.setVisibility(GONE);

        mLoaderManager = getLoaderManager();

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(GONE);

        mAdapter = new CustomListAdapter(this);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(mAdapter);
        if (savedInstanceState != null
                && savedInstanceState.containsKey(LIST_STATE)) {
            mAdapter.addAll(savedInstanceState.<Item>getParcelableArrayList(LIST_STATE));
        }



        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(GOOGLE_BOOK_BASE_URL);
                    sb.append(query);
                    sb.append(BOOK_RESULT_COUNT);
                    mBookUrl = sb.toString();
                    if (isNetworkAvailable()) {
                        requestUpdate();
                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE, mAdapter.getListItems());
    }

    public void requestUpdate() {
        if (mLoaderManager == null) {
            mLoaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            mLoaderManager.restartLoader(BOOK_LOADER_ID, null, this);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        View loadingIndicator = findViewById(R.id.loading_indicator);
        if (networkInfo != null && networkInfo.isConnected()) {
            loadingIndicator.setVisibility(View.VISIBLE);
            return true;
        } else {
            loadingIndicator.setVisibility(GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
            return false;
        }
    }

    @Override
    public Loader<Book> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "onCreateLoader");
        return new BookLoader(this, mBookUrl);
    }


    @Override
    public void onLoadFinished(Loader<Book> loader, Book data) {
        Log.i(TAG, "onLoadFinished");
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mAdapter.clear();
        if (data != null) {
            mEmptyStateTextView.setVisibility(View.GONE);
            mAdapter.addAll(data.getItems());
        } else {
            mEmptyStateTextView.setText(R.string.no_related_book_found);
        }
    }

    @Override
    public void onLoaderReset(Loader<Book> loader) {
        Log.i(TAG, "onLoaderReset");
        mAdapter.clear();
    }
}
