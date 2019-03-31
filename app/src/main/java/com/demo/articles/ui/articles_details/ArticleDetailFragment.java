package com.demo.articles.ui.articles_details;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.articles.R;
import com.demo.articles.data.remote.models.MostPopularModelResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 30/3/19.
 */
public class ArticleDetailFragment extends Fragment {

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvContent)
    TextView tvContent;

    @BindView(R.id.tvDate)
    TextView tvDate;

    private MostPopularModelResponse.Result model;
    private ArticleDetailViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initObservers();
    }

    private void initObservers() {
        mViewModel.responseData.observe(this, articleDetailModel -> {
            articleDetailModel.setDate(model.getPublishedDate());
            tvTitle.setText(articleDetailModel.getTitle());
            tvDate.setText(articleDetailModel.getDate());
            tvContent.setText(articleDetailModel.getContent());
        });
    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        model = (MostPopularModelResponse.Result) getArguments().getSerializable("data");
        mViewModel = ViewModelProviders.of(this).get(ArticleDetailViewModel.class);
        mViewModel.getMostPopularArticleDetails(model.getUrl());

    }
}
