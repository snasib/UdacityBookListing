package com.example.android.booklisting.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by sadin on 25-Oct-17.
 */

public class VolumeInfo implements Parcelable {
    public String title = "Not available";
    public List<String> authors = null;
    public String publisher = "N/A";
    public String publishedDate = "N/A";
    public String description = "Not available";

    protected VolumeInfo(Parcel in) {
        title = in.readString();
        authors = in.createStringArrayList();
        publisher = in.readString();
        publishedDate = in.readString();
        description = in.readString();
    }

    public static final Creator<VolumeInfo> CREATOR = new Creator<VolumeInfo>() {
        @Override
        public VolumeInfo createFromParcel(Parcel in) {
            return new VolumeInfo(in);
        }

        @Override
        public VolumeInfo[] newArray(int size) {
            return new VolumeInfo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        if (authors != null) {
            StringBuilder sb = new StringBuilder();
            for (String s : authors) {
                sb.append(s);
            }
            return sb.toString();
        }
        return "";
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeStringList(authors);
        dest.writeString(publisher);
        dest.writeString(publishedDate);
        dest.writeString(description);
    }
}
