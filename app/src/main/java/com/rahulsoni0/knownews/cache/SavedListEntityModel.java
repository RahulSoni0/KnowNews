package com.rahulsoni0.knownews.cache;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "savedlist")
public class SavedListEntityModel {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "author")
    String author;
    @ColumnInfo(name = "content")
    String content;
    @ColumnInfo(name = "description")
    String description;
    @ColumnInfo(name = "publishedAt")
    String publishedAt;
    @ColumnInfo(name = "source")
    String source;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "url")
    String url;
    @ColumnInfo(name = "urlToImage")
    String urlToImage;

    @Override
    public String toString() {
        return "SavedNews{" +
                "author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", source=" + source +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public SavedListEntityModel(String author, String content,
                                String description, String publishedAt,
                                String source, String title, String url,
                                String urlToImage) {
        this.author = author;
        this.content = content;
        this.description = description;
        this.publishedAt = publishedAt;
        this.source = source;
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
    }


}
