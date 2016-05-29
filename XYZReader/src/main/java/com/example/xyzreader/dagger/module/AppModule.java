package com.example.xyzreader.dagger.module;

import com.example.xyzreader.model.DataProvider;
import com.example.xyzreader.ui.screens.article_detail_screen.ArticleDetailPresenter;
import com.example.xyzreader.ui.screens.article_list_screen.ArticleListPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vijaykedia on 27/05/16.
 * This will define the dagger module for dependency injection
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    public DataProvider provideDataProvider() {
        return new DataProvider();
    }

    @Provides
    @Singleton
    public ArticleListPresenter provideArticleListPresenter(final DataProvider dataProvider) {
        return new ArticleListPresenter(dataProvider);
    }

    @Provides
    @Singleton
    public ArticleDetailPresenter provideDetailPresenter() {
        return new ArticleDetailPresenter();
    }
}
