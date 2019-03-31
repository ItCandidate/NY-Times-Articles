package com.demo.articles.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created on 30/3/19.
 */
public class Utils {

    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
