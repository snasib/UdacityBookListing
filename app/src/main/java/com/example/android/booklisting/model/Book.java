package com.example.android.booklisting.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by sadin on 25-Oct-17.
 */

public class Book {
    public List<Item> items = null;

    public List<Item> getItems() {
        return items;
    }

}
