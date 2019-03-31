package com.demo.articles.ui.articles_list;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.articles.R;
import com.demo.articles.data.remote.models.MostPopularModelResponse;
import com.demo.articles.ui.MainActivity;
import com.demo.articles.utils.RecyclerViewMargin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 30/3/19.
 */
public class ArticlesListFragment extends Fragment
        implements ArticleListAdapter.IOnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tvError)
    TextView tvError;

    @BindView(R.id.rvArticles)
    RecyclerView rvArticles;

    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArticleListViewModel mViewModel;
    private ArticleListAdapter mAdapter;
    private List<MostPopularModelResponse.Result> data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this).get(ArticleListViewModel.class);

        initRecyclerView();
        initObservers();
        initSwipeToRefresh();

    }

    private void initObservers() {
        mViewModel.isError.observe(this, aBoolean -> {
            hideProgress();
            if (aBoolean) {
                tvError.setVisibility(View.VISIBLE);
                rvArticles.setVisibility(View.GONE);
            } else {
                tvError.setVisibility(View.GONE);
                rvArticles.setVisibility(View.VISIBLE);
            }
        });
        mViewModel.responseData.observe(this, results -> {
            hideProgress();
            if (!results.isEmpty()) {
                tvError.setVisibility(View.GONE);
                rvArticles.setVisibility(View.VISIBLE);
                data.clear();
                data.addAll(results);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initSwipeToRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(() -> {
                    showProgress();
                    callAPI();
                }
        );
    }

    private void initRecyclerView() {
        data = new ArrayList<>();
        mAdapter = new ArticleListAdapter(data, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvArticles.setLayoutManager(mLayoutManager);
        rvArticles.setItemAnimator(new DefaultItemAnimator());
        rvArticles.addItemDecoration(new RecyclerViewMargin(48, 1));
        rvArticles.setAdapter(mAdapter);
    }

    private void callAPI() {
        mViewModel.getMostPopularArticles(7);
    }

    @Override
    public void onItemClick(MostPopularModelResponse.Result item) {
        ((MainActivity)getActivity()).loadArticleDetails(item);
    }

    @Override
    public void onRefresh() {
        callAPI();
    }

    private void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
