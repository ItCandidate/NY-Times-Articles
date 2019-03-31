package com.demo.articles.data.remote.api;

import com.demo.articles.data.remote.models.MostPopularModelResponse;
import com.demo.articles.data.remote.AppApiManager;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created on 30/3/19.
 */
public interface IMostPopularApiService {

    String NO_AUTH = AppApiManager.HEADER_KEY_AUTH + ":" + AppApiManager.HEADER_KEY_NO_AUTH;
    String HEADER_CONTENT_TYPE = AppApiManager.HEADER_CONTENT_TYPE;
    String HEADER_ACCEPT = AppApiManager.HEADER_ACCEPT;
    String prefix = "/svc/mostpopular/v2/viewed/{period}";

    @Headers({NO_AUTH})
    @GET(prefix)
    Single<MostPopularModelResponse> getMostPopular(@Path("period") int path,
                                                    @Query("api-key") String apiKey);
}
