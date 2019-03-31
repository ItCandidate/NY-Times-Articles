package com.demo.articles.ui.articles_details;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 30/3/19.
 */
public class ArticleDetailViewModel extends ViewModel {

    protected MutableLiveData<Boolean> isError = new MutableLiveData<>();
    protected MutableLiveData<ArticleDetailModel> responseData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    @SuppressLint("CheckResult")
    protected void getMostPopularArticleDetails(String url) {
        ArticleDetailModel articleDetailModel = new ArticleDetailModel();
        Observable.fromCallable(() -> {
            Document document = Jsoup.connect(url).get();
            articleDetailModel.setTitle(document.title());
            articleDetailModel.setContent(document.select("p").text());
            return false;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> responseData.postValue(articleDetailModel),
                        (error -> isError.postValue(true)));
    }

    @Override
    protected void onCleared() {
        if (disposable != null)
            disposable.dispose();
        super.onCleared();
    }
}
