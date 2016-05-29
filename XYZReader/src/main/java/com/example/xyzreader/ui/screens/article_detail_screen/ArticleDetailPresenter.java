package com.example.xyzreader.ui.screens.article_detail_screen;

import android.support.annotation.NonNull;

import com.example.xyzreader.event.ArticleListEvent;
import com.example.xyzreader.model.pojo.Article;
import com.example.xyzreader.util.Util;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by vijaykedia on 26/05/16.
 * This will act as a mediator between dao and {@link ArticleDetailActivity}
 */
public class ArticleDetailPresenter {

    private Realm realm;

    public ArticleDetailPresenter() {
    }

    public void onCreate() {
        realm = Realm.getDefaultInstance();
    }

    public void onStart(@NonNull final ArticleDetailScreen.Header detailScreen) {
        EventBus.getDefault().register(detailScreen);

        final RealmResults<Article> articles = Util.getArticles(realm);
        EventBus.getDefault().post(new ArticleListEvent(articles));

        detailScreen.showArticle();
    }

    public void onStop(@NonNull final ArticleDetailScreen.Header detailScreen) {
        EventBus.getDefault().unregister(detailScreen);
    }

    public void onDestroy() {
        if (realm != null) {
            realm.close();
        }
    }

    public void onCreateView(@NonNull final ArticleDetailScreen.Body detailScreen) {
        detailScreen.displayData();
    }

    public void onShareButtonClick(@NonNull final ArticleDetailScreen.Body detailScreen) {
        detailScreen.onShareButtonClick();
    }

    public void onBackNavigation(@NonNull final ArticleDetailScreen.Body detailScreen) {
        detailScreen.onUpNavigationClick();
    }
}
