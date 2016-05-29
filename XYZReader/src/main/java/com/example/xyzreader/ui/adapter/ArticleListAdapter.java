package com.example.xyzreader.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xyzreader.R;
import com.example.xyzreader.XYZReaderApplication;
import com.example.xyzreader.model.pojo.Article;
import com.example.xyzreader.ui.listeners.ItemClickListener;
import com.example.xyzreader.util.Util;
import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

/**
 * Created by vijaykedia on 20/05/16.
 * This class will act as adapter class for putting items in {@link com.example.xyzreader.ui.screens.article_list_screen.ArticleListActivity#recyclerView}
 */
public class ArticleListAdapter extends RealmRecyclerViewAdapter<Article, ArticleListAdapter.ArticleViewHolder> {

    private final ItemClickListener listener;

    public ArticleListAdapter(@NonNull final Context context, @Nullable final RealmResults<Article> data, @NonNull final ItemClickListener listener) {
        super(context, data, true);
        this.listener = listener;
    }

    @Override
    @NonNull
    public ArticleViewHolder onCreateViewHolder(@Nullable final ViewGroup parent, final int viewType) {
        View itemView = inflater.inflate(R.layout.list_item_article_layout, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ArticleViewHolder holder, final int position) {
        final Article article = getData().get(position);

        Picasso.with(context)
                .load(article.getThumb())
                .into(holder.thumbnailView, PicassoPalette.with(article.getThumb(), holder.thumbnailView)
                        .use(PicassoPalette.Profile.MUTED_LIGHT)
                        .intoBackground(holder.itemView, PicassoPalette.Swatch.RGB)
                        .intoTextColor(holder.titleView, PicassoPalette.Swatch.TITLE_TEXT_COLOR)
                        .intoTextColor(holder.subtitleView, PicassoPalette.Swatch.TITLE_TEXT_COLOR)
                );

        holder.titleView.setText(article.getTitle());

        holder.subtitleView.setText(Util.getSubTitle(article.getPublished_date(), article.getAuthor()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(article.getId());
            }
        });
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail)
        public ImageView thumbnailView;

        @BindView(R.id.article_title)
        public TextView titleView;

        @BindView(R.id.article_subtitle)
        public TextView subtitleView;

        public ArticleViewHolder(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            titleView.setTypeface(XYZReaderApplication.getInstance().getTypeFace(XYZReaderApplication.ROBOTO_ITALIC));

            subtitleView.setTypeface(XYZReaderApplication.getInstance().getTypeFace(XYZReaderApplication.ROBOTO_NORMAL));
        }
    }
}
