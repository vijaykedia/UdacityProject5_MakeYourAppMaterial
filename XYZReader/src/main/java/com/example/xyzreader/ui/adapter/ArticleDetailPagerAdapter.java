package com.example.xyzreader.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.xyzreader.model.pojo.Article;
import com.example.xyzreader.ui.screens.article_detail_screen.ArticleDetailFragment;

import io.realm.RealmResults;

/**
 * Created by vijaykedia on 21/05/16.
 * This will act as adapter class for {@link android.support.v4.view.ViewPager}
 */
public class ArticleDetailPagerAdapter extends FragmentStatePagerAdapter {

    private RealmResults<Article> articles = null;

    public ArticleDetailPagerAdapter(@NonNull final FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    @NonNull
    public Fragment getItem(final int position) {
        return ArticleDetailFragment.getFragment(articles.get(position));
    }

    @Override
    public int getCount() {
        if (articles != null) {
            return articles.size();
        }
        return -1;
    }

    public void updateData(@NonNull final RealmResults<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }
}
