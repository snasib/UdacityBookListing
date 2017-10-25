package com.example.android.booklisting.adapter;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.booklisting.R;
import com.example.android.booklisting.model.Book;
import com.example.android.booklisting.model.Item;
import com.example.android.booklisting.model.VolumeInfo;

import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sadin on 25-Oct-17.
 */

public class CustomListAdapter extends ArrayAdapter<Item> {
    private static final String TAG = CustomListAdapter.class.getSimpleName();
    private View mListView;

    public CustomListAdapter(@NonNull Context context, @NonNull List<Item> objects) {
        super(context, 0, objects);
    }

    public CustomListAdapter(@NonNull Context context) {
        super(context, 0);
    }


    public ArrayList<Item> getListItems() {
        ArrayList<Item> objects = new ArrayList<>(getCount());
        for (int i = 0; i < getCount(); i++) {
            objects.add(getItem(i));
        }
        return objects;
    }

    public View getListView() {
        return mListView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.i(TAG, "getViewOnCustomAdapter");

        mListView = convertView;
        if (mListView == null) {
            mListView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_items,
                    parent,
                    false
            );
        }

        Item item = getItem(position);

        TextView title = (TextView) mListView.findViewById(R.id.book_title);
        title.setText(String.format("%s: %s", mListView.getResources().getString(R.string.title), item.getVolumeInfo().getTitle()));

        TextView authors = (TextView) mListView.findViewById(R.id.authors);
        authors.setText(String.format("%s: %s", mListView.getResources().getString(R.string.authors), item.getVolumeInfo().getAuthors()));

        TextView publisher = (TextView) mListView.findViewById(R.id.publisher);
        publisher.setText(String.format("%s: %s", mListView.getResources().getString(R.string.publisher), item.getVolumeInfo().getPublisher()));

        TextView publishedDate = (TextView) mListView.findViewById(R.id.publishedDate);
        publishedDate.setText(String.format("%s: %s", mListView.getResources().getString(R.string.date), item.getVolumeInfo().getPublishedDate()));

        return mListView;
    }
}
