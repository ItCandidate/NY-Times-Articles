package com.demo.articles.app;

import android.app.Application;

import com.demo.articles.data.AppDataManager;
import com.demo.articles.data.remote.AppApiManager;

/**
 * Created on 30/3/19.
 */
public class ArticleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppDataManager.getInstance(getApplicationContext(),
                AppApiManager.getInstance(this));
    }
}
