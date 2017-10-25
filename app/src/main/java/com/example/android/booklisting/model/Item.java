package com.example.android.booklisting.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sadin on 25-Oct-17.
 */

public class Item implements Parcelable {
    private VolumeInfo volumeInfo;

    protected Item(Parcel in) {
        volumeInfo = in.readParcelable(VolumeInfo.class.getClassLoader());
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(volumeInfo, flags);
    }
}
