package com.example.xyzreader.ui.screens.article_list_screen;

/**
 * Created by vijaykedia on 26/05/16.
 * This will define interface for operations on main screen
 */
public interface ArticleListScreen {

    // Event Handlers
    void onNetworkChange();

    void onRetryClick();

    // checking for internet connectivity
    boolean isNetworkAvailable();

    // Handling progress bars
    void showProgressBar();

    void hideProgressBar();
}
