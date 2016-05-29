package com.example.xyzreader.ui.screens.article_list_screen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.xyzreader.R;
import com.example.xyzreader.dagger.Injector;
import com.example.xyzreader.event.ArticleListEvent;
import com.example.xyzreader.event.ErrorMessageEvent;
import com.example.xyzreader.model.pojo.Article;
import com.example.xyzreader.ui.adapter.ArticleListAdapter;
import com.example.xyzreader.ui.listeners.ItemClickListener;
import com.example.xyzreader.ui.screens.article_detail_screen.ArticleDetailActivity;
import com.example.xyzreader.util.Util;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by vijaykedia on 19/05/16.
 * This will define the UI for main screen i.e grid view
 */
public class ArticleListActivity extends AppCompatActivity implements ArticleListScreen, ItemClickListener {

    private static final String LOG_TAG = ArticleListActivity.class.getSimpleName();

    // ---- Presenter ----
    @Inject
    public ArticleListPresenter presenter;

    // ---- Views ----
    @BindView(R.id.coordinator_layout)
    public CoordinatorLayout coordinatorLayout;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;

    private Snackbar snackbar;

    // ---- Member variables ----
    private ArticleListAdapter adapter;

    private BroadcastReceiver broadcastReceiver;
    private boolean isReceiverRegistered = false;

    private boolean loadingInProgress = false;

    // ---- Activity LifeCycle callback methods ----

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {

        Util.DebugLog(LOG_TAG, "OnCreate() -- ArticleListActivity is being created.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_list_layout);

        // Bind views
        ButterKnife.bind(this);
        Injector.getAppComponent().inject(this);

        presenter.onCreate();

        // Initialize snackBar (interactive UI element to show error message) with arbitrary text
        snackbar = Snackbar.make(coordinatorLayout, "Temp", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RETRY", new View.OnClickListener() {
            @Override
            public void onClick(@NonNull final View v) {
                presenter.onRetryClick(getListScreen());
            }
        });

        // Initialize broadcast receiver
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(@NonNull final Context context, @NonNull final Intent intent) {

                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    presenter.onNetworkChange(getListScreen());
                }
            }
        };

        // Set up RecyclerView
        int columnCount = getResources().getInteger(R.integer.list_column_count);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL));

        adapter = new ArticleListAdapter(this, null, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {

        Util.DebugLog(LOG_TAG, "onRestart() -- User came back and ArticleListActivity is re-displayed to the user.");

        super.onRestart();
    }

    @Override
    protected void onStart() {

        Util.DebugLog(LOG_TAG, "onStart() -- ArticleListActivity is about to be become visible.");

        super.onStart();

        registerBroadcastReceiverForMonitoringNetworkConnectivity();

        presenter.onStart(this);
    }

    @Override
    protected void onResume() {

        Util.DebugLog(LOG_TAG, "onResume() -- ArticleListActivity has become visible and user is interacting with it.");

        super.onResume();
    }

    @Override
    protected void onPause() {

        Util.DebugLog(LOG_TAG, "onPause() -- ArticleListActivity is about to be paused. Another activity is taking focus.");

        super.onPause();
    }

    @Override
    protected void onStop() {

        Util.DebugLog(LOG_TAG, "onStop() -- ArticleListActivity is stopped and is no longer visible.");

        presenter.onStop(this);

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        Util.DebugLog(LOG_TAG, "onDestroy() -- ArticleListActivity is about to be destroyed.");

        presenter.onDestroy();

        unregisterBroadcastReceiver();

        super.onDestroy();
    }

    // ---- Helper methods ----

    private ArticleListScreen getListScreen() {
        return this;
    }

    public void registerBroadcastReceiverForMonitoringNetworkConnectivity() {
        if (!isReceiverRegistered) {
            final IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(broadcastReceiver, filter);
            isReceiverRegistered = true;
        }
    }

    public void unregisterBroadcastReceiver() {
        unregisterReceiver(broadcastReceiver);
        isReceiverRegistered = false;
    }

    private void loadArticles() {
        presenter.loadArticles(this);
    }

    // ---- Method used as a callback by presenter ----

    @Override
    public void onNetworkChange() {

        Util.DebugLog("XYZReader BroadcastReceiver", "Received network change event");

        if (!loadingInProgress) {
            loadArticles();
        }
    }

    public void onRetryClick() {

        Util.DebugLog("Main Screen SnackBar", "Received retry click event");

        loadArticles();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        loadingInProgress = true;
        hideSnackBar();
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        loadingInProgress = false;
    }

    public void showSnackBar() {
        snackbar.show();
    }

    public void hideSnackBar() {
        snackbar.dismiss();
    }

    @Override
    public void onItemClick(final int id) {
        startActivity(new Intent(this, ArticleDetailActivity.class).putExtra(ArticleDetailActivity.ARTICLE_ID_KEY, id));
    }

    public void showErrorMessage(@NonNull final String message) {

        hideProgressBar();
        snackbar.setText(message);
        showSnackBar();
    }

    @Override
    public boolean isNetworkAvailable() {
        return Util.isNetworkAvailable(this);
    }

    // ---- EventBus event listeners ----

    @Subscribe(threadMode = ThreadMode.MAIN)
    @SuppressWarnings("unused")
    public void onEvent(@NonNull final ErrorMessageEvent errorMessageEvent) {
        showErrorMessage(errorMessageEvent.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @SuppressWarnings("unused")
    public void onEvent(@NonNull final ArticleListEvent articleListEvent) {

        final RealmResults<Article> articles = articleListEvent.getArticles();

        articles.addChangeListener(new RealmChangeListener<RealmResults<Article>>() {
            @Override
            public void onChange(@NonNull final RealmResults<Article> element) {
                // Since we are not deleting any data from realm, hence we are not checking if data is valid

                hideProgressBar();

                if (snackbar != null) {
                    snackbar.dismiss();
                }
            }
        });

        adapter.updateData(articles);
    }
}
