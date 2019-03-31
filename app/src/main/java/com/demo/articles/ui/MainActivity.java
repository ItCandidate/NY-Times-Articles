package com.demo.articles.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.articles.R;
import com.demo.articles.data.remote.models.MostPopularModelResponse;
import com.demo.articles.ui.articles_details.ArticleDetailFragment;
import com.demo.articles.ui.articles_list.ArticlesListFragment;
import com.demo.articles.utils.FragmentHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentHelper.fragmentTransaction(this, FragmentHelper.ADD_FRAGMENT,
                new ArticlesListFragment(), R.id.container, false, ArticlesListFragment.class.getSimpleName(), false);

    }

    public void loadArticleDetails(MostPopularModelResponse.Result item) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle b = new Bundle();
        b.putSerializable("data", item);
        fragment.setArguments(b);
        FragmentHelper.fragmentTransaction(this, FragmentHelper.REPLACE_FRAGMENT,
                fragment, R.id.container, true,
                ArticleDetailFragment.class.getSimpleName(), true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
