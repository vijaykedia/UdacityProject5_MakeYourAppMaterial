package com.example.xyzreader.dagger;

import com.example.xyzreader.dagger.component.AppComponent;
import com.example.xyzreader.dagger.component.DaggerAppComponent;
import com.example.xyzreader.dagger.module.AppModule;

/**
 * Created by vijaykedia on 27/05/16.
 * This is helper class to inject the dependencies
 */
public class Injector {

    private static AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
