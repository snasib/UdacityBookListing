package com.example.android.booklisting.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.android.booklisting.model.Book;
import com.example.android.booklisting.model.VolumeInfo;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import static android.widget.Toast.*;

/**
 * Created by sadin on 25-Oct-17.
 */

public final class HttpQuery {
    private static final String TAG = HttpQuery.class.getSimpleName();
    private static URL mUrl = null;
    private static String mJsonResponse = "";

    public static Book fetchBookData(String targetUrl) {
        Log.i(TAG, "fetchBookData");
        buildUrl(targetUrl);
        try {
            mJsonResponse = makeHttpRequest(mUrl);
        } catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request.", e);
            e.printStackTrace();
        }
        return extractFeatureFromJson(mJsonResponse);
    }

    @Nullable
    private static Book extractFeatureFromJson(String jsonResponse) {
        Log.i(TAG, "extractFeatureFromJson");
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        Book book = new Book();

        try {
            Gson gson = new Gson();
            book = gson.fromJson(jsonResponse, Book.class);
        } catch (JsonParseException e) {
            Log.e(TAG, "Error in parsing Json" + e.getMessage());
        }

        return book;
    }

    private static URL buildUrl(String url) {
        Log.i(TAG, "buildUrl");
        try {
            mUrl = new URL(url);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Problem with URL" + e.getMessage());
            e.printStackTrace();
        }
        return mUrl;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        Log.i(TAG, "makeHttpRequest");
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    @NonNull
    private static String readFromStream(InputStream inputStream) throws IOException {
        Log.i(TAG, "readFromStream");
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
