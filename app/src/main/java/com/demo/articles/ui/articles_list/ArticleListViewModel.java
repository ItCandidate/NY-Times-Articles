package com.demo.articles.ui.articles_list;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.demo.articles.BuildConfig;
import com.demo.articles.data.AppDataManager;
import com.demo.articles.data.remote.models.MostPopularModelResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 30/3/19.
 */
public class ArticleListViewModel extends ViewModel {

    private AppDataManager appDataManager;
    protected MutableLiveData<Boolean> isError = new MutableLiveData<>();
    protected MutableLiveData<List<MostPopularModelResponse.Result>> responseData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    {
        appDataManager = AppDataManager.getInstance();
    }

    protected void getMostPopularArticles(int period) {
        disposable.add(appDataManager.getMostPopularArticles(period, BuildConfig.API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<MostPopularModelResponse>() {
                    @Override
                    public void onSuccess(MostPopularModelResponse response) {
                        responseData.postValue(response.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        isError.postValue(true);
                        e.printStackTrace();
                    }
                }));
    }

    @Override
    protected void onCleared() {
        if (disposable != null)
            disposable.dispose();
        super.onCleared();
    }
}
