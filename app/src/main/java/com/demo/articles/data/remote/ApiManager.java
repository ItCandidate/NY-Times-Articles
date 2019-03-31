package com.demo.articles.data.remote;

import com.demo.articles.data.remote.models.MostPopularModelResponse;

import io.reactivex.Single;

/**
 * Created on 30/3/19.
 */
public interface ApiManager {

    Single<MostPopularModelResponse> getMostPopularArticles(int period, String apiKey);
}
