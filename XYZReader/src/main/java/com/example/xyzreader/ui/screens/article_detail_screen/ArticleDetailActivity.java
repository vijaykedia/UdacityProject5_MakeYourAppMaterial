package com.example.xyzreader.ui.screens.article_detail_screen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.xyzreader.R;
import com.example.xyzreader.dagger.Injector;
import com.example.xyzreader.event.ArticleListEvent;
import com.example.xyzreader.model.pojo.Article;
import com.example.xyzreader.ui.adapter.ArticleDetailPagerAdapter;
import com.example.xyzreader.ui.animations.ZoomOutPageTransformer;
import com.example.xyzreader.util.Util;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by vijaykedia on 21/05/16.
 * This will act as a UI for detail view
 */
public class ArticleDetailActivity extends AppCompatActivity implements ArticleDetailScreen.Header {

    private static final String LOG_TAG = ArticleDetailActivity.class.getSimpleName();

    public static final String ARTICLE_ID_KEY = "article_id";

    // ---- Presenter ----
    @Inject
    public ArticleDetailPresenter presenter;

    // ---- Views ----
    @BindView(R.id.article_detail_view_pager)
    public ViewPager viewPager;

    // ---- Member variables ----
    private int currentArticleId;

    private ArticleDetailPagerAdapter adapter;

    private RealmResults<Article> articles;

    // ---- Activity lifecycle methods ----

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {

        Util.DebugLog(LOG_TAG, "OnCreate() -- ArticleDetailActivity is being created.");

        // inflate layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detail_header_layout);

        // Bind views
        ButterKnife.bind(this);
        Injector.getAppComponent().inject(this);

        // Initialize Presenter
        presenter.onCreate();

        // Get article id from intent
        currentArticleId = getIntent().getIntExtra(ARTICLE_ID_KEY, -1);

        // Setup View pager
        adapter = new ArticleDetailPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Override
    protected void onRestart() {

        Util.DebugLog(LOG_TAG, "onRestart() -- User came back and ArticleDetailActivity is re-displayed to the user.");

        super.onRestart();
    }

    @Override
    protected void onStart() {

        Util.DebugLog(LOG_TAG, "onStart() -- ArticleDetailActivity is about to be become visible.");

        super.onStart();
        presenter.onStart(this);
    }

    @Override
    protected void onResume() {

        Util.DebugLog(LOG_TAG, "onResume() -- ArticleDetailActivity has become visible and user is interacting with it.");

        super.onResume();
    }

    @Override
    protected void onPause() {

        Util.DebugLog(LOG_TAG, "onPause() -- ArticleDetailActivity is about to be paused. Another activity is taking focus.");

        super.onPause();
    }

    @Override
    protected void onStop() {

        Util.DebugLog(LOG_TAG, "onStop() -- ArticleDetailActivity is stopped and is no longer visible.");

        presenter.onStop(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        Util.DebugLog(LOG_TAG, "onDestroy() -- ArticleDetailActivity is about to be destroyed.");

        presenter.onDestroy();
        super.onDestroy();
    }

    // ---- presenter callback methods ----

    /**
     * This will set the position of current article id and will cause fragment to get initialized
     */
    @Override
    public void showArticle() {
        final int position = getPosition();
        viewPager.setCurrentItem(position, true);
    }

    // ---- Helper methods ----

    /**
     * This will determine the position of current article id in data set
     *
     * @return position of current article id in data set
     */
    private int getPosition() {

        for (int i = 0; i < articles.size(); i++) {
            final Article article = articles.get(i);
            if (article.getId() == currentArticleId) {
                return i;
            }
        }
        return 0;
    }

    // ---- EventBus event listeners ----

    @Subscribe(threadMode = ThreadMode.MAIN)
    @SuppressWarnings("unused")
    public void onEvent(@NonNull final ArticleListEvent event) {
        articles = event.getArticles();
        adapter.updateData(articles);
    }
}
