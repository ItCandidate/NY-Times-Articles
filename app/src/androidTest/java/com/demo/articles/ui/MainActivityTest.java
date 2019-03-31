package com.demo.articles.ui;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.demo.articles.R;
import com.demo.articles.ui.articles_list.ArticlesListFragment;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created on 30/3/19.
 */
public class MainActivityTest {

    @Rule
    //Activity Test Rule for the activity which contains my login fragment
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.getActivity();
        mActivityRule.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new ArticlesListFragment()).commit();
    }

    @Test
    public void isContainerPresent() {
        View view = mActivity.findViewById(R.id.container);
        Assert.assertNotNull(view);
    }

    @Test
    public void onRecyclerViewClick() {
        Espresso.onView(withId(R.id.rvArticles)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
        mActivityRule = null;
    }
}