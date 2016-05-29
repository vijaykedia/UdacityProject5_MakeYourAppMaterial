package com.example.xyzreader.event;

import android.support.annotation.NonNull;

import com.example.xyzreader.model.pojo.Article;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by vijaykedia on 26/05/16.
 * This will be used to pass {@link List<Article>}
 */
public class ArticleListEvent {

    private final RealmResults<Article> articles;

    public ArticleListEvent(@NonNull final RealmResults<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    public RealmResults<Article> getArticles() {
        return articles;
    }
}
