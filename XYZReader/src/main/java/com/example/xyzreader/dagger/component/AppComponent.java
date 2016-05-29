package com.example.xyzreader.dagger.component;

import com.example.xyzreader.dagger.module.AppModule;
import com.example.xyzreader.ui.screens.article_detail_screen.ArticleDetailActivity;
import com.example.xyzreader.ui.screens.article_detail_screen.ArticleDetailFragment;
import com.example.xyzreader.ui.screens.article_list_screen.ArticleListActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vijaykedia on 27/05/16.
 * This will define the dagger component for dependency injection
 */
@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {

    void inject(final ArticleListActivity listActivity);

    void inject(final ArticleDetailActivity detailActivity);

    void inject(final ArticleDetailFragment fragment);
}
