package com.example.xyzreader.ui.screens.article_detail_screen;

/**
 * Created by vijaykedia on 26/05/16.
 *
 */
public class ArticleDetailScreen {

    public interface Header {

        void showArticle();

    }

    public interface Body {

        void displayData();

        void onShareButtonClick();

        void onUpNavigationClick();
    }
}
