package com.demo.articles.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.demo.articles.BuildConfig;
import com.demo.articles.data.remote.models.MostPopularModelResponse;
import com.demo.articles.data.remote.api.IMostPopularApiService;
import com.demo.articles.utils.Utils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 30/3/19.
 */
public class AppApiManager implements ApiManager {

    public static final String HEADER_KEY_NO_AUTH = "NO_AUTH";
    public static final String HEADER_KEY_AUTH = "Authorization";
    public static final String HEADER_CONTENT_TYPE = "Content-Type: application/json";
    public static final String HEADER_ACCEPT = "accept: application/json, text/plain, */*";

    private static AppApiManager instance;
    private OkHttpClient httpClient;
    private Retrofit retrofit;

    public static AppApiManager getInstance(Context mContext) {
        if (instance == null) {
            synchronized (AppApiManager.class) {
                if (instance == null) {
                    instance = new AppApiManager(mContext);
                }
            }
        }
        return instance;
    }

    private AppApiManager(Context mContext) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient(mContext))
                .build();
    }

    private OkHttpClient getHttpClient(Context mContext) {
        if (httpClient == null) {
            final int CONNECT_TIMEOUT = 60;
            final int READ_TIMEOUT = 60;

            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.addInterceptor(new Interceptor());
//            builder.addInterceptor(new NetworkInterceptor(mContext));
            builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
            builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);

            long cacheSize = (5 * 1024 * 1024);
            Cache cache = new Cache(mContext.getCacheDir(), cacheSize);
            builder.cache(cache);
            builder.addInterceptor(chain -> {
                Request request = chain.request();

                if (Utils.isNetworkConnected(mContext)) {
                    request = request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build();
                } else {
                    request = request.newBuilder().header(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build();
                }
                return chain.proceed(request);
            });
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            }

            httpClient = builder.build();
        }
        return httpClient;
    }

    private class Interceptor implements okhttp3.Interceptor {
        @Override
        public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
            Request oldRequest = chain.request();
            Request.Builder newRequestBuilder = oldRequest.newBuilder();
            if (oldRequest.header(HEADER_KEY_AUTH) != null) {
                newRequestBuilder.removeHeader(HEADER_KEY_AUTH); //If header contains "NO_AUTH", means we don't need token for this request
            } else {
                newRequestBuilder.addHeader("Authorization", " Bearer ");
            }

            okhttp3.Response response = chain.proceed(newRequestBuilder.build());

            if (BuildConfig.DEBUG) {
                Log.d("http", response.peekBody(1024).string());
            }

            return response;
        }
    }

    @Override
    public Single<MostPopularModelResponse> getMostPopularArticles(int period, String apiKey) {
        return retrofit.create(IMostPopularApiService.class).getMostPopular(period, apiKey);
    }
}
