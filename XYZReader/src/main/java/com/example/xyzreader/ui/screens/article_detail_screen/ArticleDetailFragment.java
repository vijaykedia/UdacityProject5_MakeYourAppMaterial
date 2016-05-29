package com.example.xyzreader.ui.screens.article_detail_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.xyzreader.R;
import com.example.xyzreader.XYZReaderApplication;
import com.example.xyzreader.dagger.Injector;
import com.example.xyzreader.model.pojo.Article;
import com.example.xyzreader.util.Util;
import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vijaykedia on 21/05/16.
 * This will server the body portion of detail view
 */
public class ArticleDetailFragment extends Fragment implements ArticleDetailScreen.Body {

    private static final String LOG_TAG = ArticleDetailFragment.class.getSimpleName();

    // ---- Presenter ----
    @Inject
    public ArticleDetailPresenter presenter;

    // ---- Views ----
    @BindView(R.id.article_detail_appbar_layout)
    public AppBarLayout appBarLayout;

    @BindView(R.id.article_detail_collapsing_toolbar)
    public CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.article_detail_toolbar_image_view)
    public ImageView toolbarImageView;

    @BindView(R.id.article_detail_progress_bar)
    public ProgressBar progressBar;

    @BindView(R.id.article_detail_meta_bar)
    public LinearLayout metaBar;

    @BindView(R.id.article_detail_title_text_view)
    public TextView title;

    @BindView(R.id.article_detail_subtitle_text_view)
    public TextView subTitle;

    @BindView(R.id.article_detail_toolbar)
    public Toolbar toolbar;

    @BindView(R.id.article_detail_up_navigation)
    public ImageView upNavigation;

    @BindView(R.id.article_detail_body_text_view)
    public TextView body;

    @BindView(R.id.article_detail_floating_share_button)
    public FloatingActionButton floatingActionButton;

    // ---- Data variables ----
    private Article currentArticle;

    /**
     * Default constructor
     */
    public ArticleDetailFragment() {
    }

    @NonNull
    public static ArticleDetailFragment getFragment(@NonNull final Article article) {
        final ArticleDetailFragment detailFragment = new ArticleDetailFragment();
        final Bundle bundle = new Bundle();
        bundle.putParcelable("article", Parcels.wrap(article));
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    // ---- Fragment Lifecycle methods ----

    @Override
    public void onAttach(@NonNull final Context context) {

        Util.DebugLog(LOG_TAG, "onAttach() -- ArticleDetailFragment has been associated with the activity.");

        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {

        Util.DebugLog(LOG_TAG, "onCreate() -- ArticleDetailFragment created.");

        super.onCreate(savedInstanceState);

        Injector.getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {

        Util.DebugLog(LOG_TAG, "onCreateView() -- Creating view hierarchy associated with ArticleDetailFragment defined in article_detail_body_layout.xml");

        final View rootView = inflater.inflate(R.layout.article_detail_body_layout, container, false);

        // ---- bind views ----
        ButterKnife.bind(this, rootView);

        currentArticle = Parcels.unwrap(getArguments().getParcelable("article"));

        presenter.onCreateView(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {

        Util.DebugLog(LOG_TAG, "onActivityCreated() -- Host activity onCreate() is completed");

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {

        Util.DebugLog(LOG_TAG, "onStart() -- ArticleDetailFragment is visible to user.");

        super.onStart();
    }

    @Override
    public void onResume() {

        Util.DebugLog(LOG_TAG, "onResume() -- ArticleDetailFragment is visible in running activity.");

        super.onResume();
    }

    @Override
    public void onPause() {

        Util.DebugLog(LOG_TAG, "onPause() -- Another activity is in foreground, but the activity in which ArticleDetailFragment lives is still visible.");

        super.onPause();
    }

    @Override
    public void onStop() {

        Util.DebugLog(LOG_TAG, "onStop() -- ArticleDetailFragment is not visible. Either the host activity has been stopped or the fragment has been removed from the hosting activity but added to the back stack.");

        super.onStop();
    }

    @Override
    public void onDestroyView() {

        Util.DebugLog(LOG_TAG, "onDestroyView() -- View hierarchy associated with ArticleDetailFragment is being removed");

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {

        Util.DebugLog(LOG_TAG, "onDestroy() -- ArticleDetailFragment is no longer in use and is destroyed.");

        super.onDestroy();
    }

    @Override
    public void onDetach() {

        Util.DebugLog(LOG_TAG, "onDetach() -- ArticleDetailFragment is being disassociated from the activity");

        super.onDetach();
    }

    // ---- presenter call back methods ----

    @Override
    public void displayData() {

        progressBar.setVisibility(View.VISIBLE);

        Picasso.with(getContext()).load(currentArticle.getPhoto()).into(toolbarImageView, PicassoPalette.with(currentArticle.getThumb(), toolbarImageView)
                .use(PicassoPalette.Profile.MUTED_LIGHT)
                .intoBackground(metaBar, PicassoPalette.Swatch.RGB)
                .intoTextColor(title, PicassoPalette.Swatch.TITLE_TEXT_COLOR)
                .intoTextColor(subTitle, PicassoPalette.Swatch.TITLE_TEXT_COLOR)
                .intoCallBack(new PicassoPalette.CallBack() {
                    @Override
                    public void onPaletteLoaded(Palette palette) {
                        progressBar.setVisibility(View.GONE);
                    }
                }));

        upNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackNavigation(getParent());

            }
        });

        title.setText(currentArticle.getTitle());
        title.setTypeface(XYZReaderApplication.getInstance().getTypeFace(XYZReaderApplication.ROBOTO_ITALIC));

        setUpActionBarLayout(currentArticle.getTitle());

        subTitle.setText(Util.getSubTitle(currentArticle.getPublished_date(), currentArticle.getAuthor()));
        title.setTypeface(XYZReaderApplication.getInstance().getTypeFace(XYZReaderApplication.ROBOTO_LIGHT_ITALIC));

        body.setText(Html.fromHtml(currentArticle.getBody()));
        body.setTypeface(XYZReaderApplication.getInstance().getTypeFace(XYZReaderApplication.ROBOTO_NORMAL));

        // To make text clickable
        // Reference : http://stackoverflow.com/questions/6226699/how-to-make-text-view-clickable-in-android
        body.setMovementMethod(LinkMovementMethod.getInstance());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onShareButtonClick(getParent());
            }
        });
    }

    @Override
    public void onShareButtonClick() {
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                .setType("text/plain")
                .setText("Some sample text")
                .getIntent(), getString(R.string.action_share)));
    }

    @Override
    public void onUpNavigationClick() {
        ((AppCompatActivity) getActivity()).onSupportNavigateUp();
    }

    // ---- Helper methods ----

    private void setUpActionBarLayout(@NonNull final String title) {

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(@NonNull final AppBarLayout appBarLayout, final int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
    }

    /**
     * This will be called by inner classes to pass the reference of {@link ArticleDetailFragment} to {@link ArticleDetailPresenter}
     *
     * @return reference of current object
     */
    @NonNull
    private ArticleDetailFragment getParent() {
        return this;
    }
}
