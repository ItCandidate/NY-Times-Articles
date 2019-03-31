package com.demo.articles.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.demo.articles.R;

/**
 * Created on 30/3/19.
 */
public class FragmentHelper {

    public final static int ADD_FRAGMENT = 0;
    public final static int REPLACE_FRAGMENT = 1;

    public static void fragmentTransaction(AppCompatActivity activity, int transactionType,
                                           Fragment fragment, int container, boolean isAddToBackStack,
                                           String tag, boolean isAnimate) {
        FragmentTransaction trans = activity.getSupportFragmentManager().beginTransaction();
        if (isAnimate) {
            trans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.enter_back, R.anim.exit_back);
        }
        switch (transactionType) {
            case ADD_FRAGMENT:
                trans.add(container, fragment, tag);
                break;
            case REPLACE_FRAGMENT:
                trans.replace(container, fragment, tag);
                if (isAddToBackStack)
                    trans.addToBackStack(tag);
                break;
        }
        trans.commit();
    }
}
