package com.example.xyzreader.model.pojo;

import android.support.annotation.NonNull;

import org.parceler.Parcel;

import io.realm.ArticleRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by vijaykedia on 19/05/16.
 * Data Model
 */
@Parcel(implementations = {ArticleRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = {Article.class})
public class Article extends RealmObject {

    @PrimaryKey
    private int id;

    @Required
    private String photo;

    @Required
    private String thumb;

    private double aspect_ratio;

    @Required
    private String author;

    @Required
    private String title;

    @Required
    private String published_date;

    @Required
    private String body;

    /**
     * Default constructor
     */
    public Article() {
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    @NonNull
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(@NonNull final String photo) {
        this.photo = photo;
    }

    @NonNull
    public String getThumb() {
        return thumb;
    }

    public void setThumb(@NonNull final String thumb) {
        this.thumb = thumb;
    }

    public double getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(double aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    @NonNull
    public String getAuthor() {
        return author;
    }

    public void setAuthor(@NonNull final String author) {
        this.author = author;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull final String title) {
        this.title = title;
    }

    @NonNull
    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(@NonNull final String published_date) {
        this.published_date = published_date;
    }

    @NonNull
    public String getBody() {
        return body;
    }

    public void setBody(@NonNull final String body) {
        this.body = body;
    }
}
