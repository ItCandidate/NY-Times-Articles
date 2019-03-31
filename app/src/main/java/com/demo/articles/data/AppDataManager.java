package com.demo.articles.data;

import android.content.Context;

import com.demo.articles.data.remote.models.MostPopularModelResponse;
import com.demo.articles.data.remote.ApiManager;

import io.reactivex.Single;

public class AppDataManager implements DataManager, ApiManager {

    private static AppDataManager instance;
    private Context context;
    private ApiManager apiManager;

    private String accessToken = "";

    private AppDataManager(Context context, ApiManager apiManager) {
        this.context = context.getApplicationContext();
        this.apiManager = apiManager;
    }

    public static AppDataManager getInstance(Context context, ApiManager apiManager) {
        if (instance == null) {
            synchronized (AppDataManager.class) {
                if (instance == null) {
                    instance = new AppDataManager(context, apiManager);
                }
            }
        }
        return instance;
    }

    public static AppDataManager getInstance() {
        if (instance == null) {
            synchronized (AppDataManager.class) {
                if (instance == null) {
                    throw new NullPointerException("Null instance");
                }
            }
        }
        return instance;
    }


    @Override
    public Single<MostPopularModelResponse> getMostPopularArticles(int period, String apiKey) {
        return apiManager.getMostPopularArticles(period, apiKey);
    }
}