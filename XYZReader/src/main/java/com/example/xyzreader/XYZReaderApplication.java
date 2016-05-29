package com.example.xyzreader;

import android.app.Application;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.BuildConfig;

import com.facebook.stetho.Stetho;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by vijaykedia on 19/05/16.
 *
 */
public class XYZReaderApplication extends Application {

    public static final String ROBOTO_LIGHT_ITALIC = "roboto_light_italic";
    public static final String ROBOTO_THIN_ITALIC = "roboto_thin_italic";
    public static final String ROBOTO_ITALIC = "roboto_italic";
    public static final String ROBOTO_NORMAL = "roboto_normal";

    private static XYZReaderApplication sInstance;

    public static boolean isDebug = false;

    private final Map<String, Typeface> fontCache = new HashMap<>();

    public static synchronized XYZReaderApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        isDebugOrReleaseMode();

        // Initialize Stetho
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

        // Initialize Realm config. The realm file will be located in Context.getFilesDir() with name "default.realm"
        RealmConfiguration config = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);

        createTypeFaceMap();
    }

    private void createTypeFaceMap() {
        fontCache.put(ROBOTO_LIGHT_ITALIC, Typeface.createFromAsset(getAssets(), "Roboto-LightItalic.ttf"));
        fontCache.put(ROBOTO_THIN_ITALIC, Typeface.createFromAsset(getAssets(), "Roboto-ThinItalic.ttf"));
        fontCache.put(ROBOTO_ITALIC, Typeface.createFromAsset(getAssets(), "Roboto-Italic.ttf"));
        fontCache.put(ROBOTO_NORMAL ,Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf"));
    }

    public Typeface getTypeFace(@NonNull final String type) {
        return fontCache.get(type);
    }

    private void isDebugOrReleaseMode() {
        final String packageName = getPackageName();
        isDebug = packageName.endsWith("debug");
    }
}
