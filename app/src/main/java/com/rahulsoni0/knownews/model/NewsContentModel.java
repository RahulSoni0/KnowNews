package com.rahulsoni0.knownews.model;

import java.util.List;

public class NewsContentModel {

    List<ArticleModel> articles;
    String status;

    @Override
    public String toString() {
        return "NewsContentModel{" +
                "articles=" + articles +
                ", status='" + status + '\'' +
                ", totalResults=" + totalResults +
                '}';
    }

    public List<ArticleModel> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleModel> articles) {
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public NewsContentModel(List<ArticleModel> articles, String status, int totalResults) {
        this.articles = articles;
        this.status = status;
        this.totalResults = totalResults;
    }

    int totalResults;
}
