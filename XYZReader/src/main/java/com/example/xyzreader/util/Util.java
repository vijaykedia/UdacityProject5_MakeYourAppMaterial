package com.example.xyzreader.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.util.Log;

import com.example.xyzreader.XYZReaderApplication;
import com.example.xyzreader.model.pojo.Article;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by vijaykedia on 22/05/16.
 * Collection of reusable functions
 */
public class Util {

    private static final String LOG_TAG = Util.class.getSimpleName();

    public static String getSubTitle(@NonNull final String publishedDate, @NonNull final String author) {

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

        Date date;
        try {
            date = dateFormat.parse(publishedDate);
        } catch (final ParseException e) {
            Log.e(LOG_TAG, "Failed to parse date", e);
            date = new Date();
        }
        return DateUtils.getRelativeTimeSpanString(date.getTime(), System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString() + " by " + author;
    }

    public static boolean isNetworkAvailable(@NonNull final Context context) {

        final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        return !(networkInfo == null || !networkInfo.isConnected());
    }

    public static void DebugLog(@NonNull final String tag, @NonNull final String message) {
        if (XYZReaderApplication.isDebug) {
            Log.d(tag, message);
        }
    }

    public static RealmResults<Article> getArticles(@NonNull final Realm realm) {
        return realm.where(Article.class).findAllSorted("id");
    }
}
