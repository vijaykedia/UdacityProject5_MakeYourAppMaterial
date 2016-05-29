package com.example.xyzreader.ui.screens.article_list_screen;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.xyzreader.event.ArticleListEvent;
import com.example.xyzreader.event.ErrorMessageEvent;
import com.example.xyzreader.model.DataProvider;
import com.example.xyzreader.model.pojo.Article;
import com.example.xyzreader.util.Util;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vijaykedia on 26/05/16.
 * This will act as mediator/interface for taking between {@link ArticleListActivity} and data
 */
public class ArticleListPresenter {

    private static final String LOG_TAG = ArticleListPresenter.class.getSimpleName();

    private DataProvider provider;
    private Realm realm = null;

    @Inject
    public ArticleListPresenter(@NonNull final DataProvider provider) {
        this.provider = provider;
    }

    public void onCreate() {
        realm = Realm.getDefaultInstance();
    }

    public void onStart(@NonNull final ArticleListScreen listScreen) {

        EventBus.getDefault().register(listScreen);

        final RealmResults<Article> results = Util.getArticles(realm);
        EventBus.getDefault().post(new ArticleListEvent(results));

        if (results.isEmpty()) {
            loadArticles(listScreen);
        }
    }

    public void onStop(@NonNull final ArticleListScreen listScreen) {
        EventBus.getDefault().unregister(listScreen);
    }

    public void onDestroy() {
        realm.close();
    }

    public void onNetworkChange(@NonNull final ArticleListScreen listScreen) {
        listScreen.onNetworkChange();
    }

    public void onRetryClick(@NonNull final ArticleListScreen listScreen) {
        listScreen.onRetryClick();
    }

    public void loadArticles(@NonNull final ArticleListScreen articleListScreen) {

        if (!realm.isEmpty()) {
            return;
        }

        articleListScreen.showProgressBar();

        if (!articleListScreen.isNetworkAvailable()) {
            EventBus.getDefault().post(new ErrorMessageEvent("No internet connection!"));
            return;
        }

        Util.DebugLog(LOG_TAG, "Fetching data from server.");

        final Observable<List<Article>> articleObservable = provider.getArticleObservable();

        articleObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Subscriber<List<Article>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(@NonNull final Throwable e) {
                        Log.e(LOG_TAG, "Failed to fetch data from server", e);
                        EventBus.getDefault().post(new ErrorMessageEvent("Failed to fetch data."));
                    }

                    @Override
                    public void onNext(@NonNull final List<Article> articles) {
                        Util.DebugLog(LOG_TAG, "Saving Data to realm");
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(@NonNull final Realm realm) {
                                realm.copyToRealmOrUpdate(articles);
                            }
                        });
                    }
                }
        );
    }
}
