package com.demo.articles.ui.articles_details;

/**
 * Created on 30/3/19.
 */
public class ArticleDetailModel {

    String title;
    String date;
    String content;

    public ArticleDetailModel(){}

    public ArticleDetailModel(String title, String date, String content) {
        this.title = title;
        this.date = date;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
