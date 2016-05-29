package com.example.xyzreader.model;

import android.support.annotation.NonNull;

import com.example.xyzreader.model.pojo.Article;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by vijaykedia on 18/05/16.
 * This class will define the api to get the list of articles
 */
public class DataProvider {

    private interface ArticleService {

        @GET("u/231329/xyzreader_data/data.json")
        Observable<List<Article>> getData();
    }

    private final Observable<List<Article>> observable = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://dl.dropboxusercontent.com")
            .build().create(ArticleService.class).getData();

    @NonNull
    public Observable<List<Article>> getArticleObservable() {
        return observable;
    }
}
