package com.example.xyzreader.model;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.xyzreader.model.pojo.Article;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vijaykedia on 19/05/16.
 * Test model operation
 */
@RunWith(AndroidJUnit4.class)
public class DataProviderTest {

    public DataProviderTest(){}

    @Test
    public void getArticlesTest() {

        final DataProvider dataProvider = new DataProvider();
        final Observable<List<Article>> listObservable = dataProvider.getArticleObservable();
        listObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Article>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("DataProviderTest", "Failure -------", e);
            }

            @Override
            public void onNext(List<Article> articles) {
                Log.i("DataProviderTest", "articles size is " + articles.size());
            }
        });
    }
}
