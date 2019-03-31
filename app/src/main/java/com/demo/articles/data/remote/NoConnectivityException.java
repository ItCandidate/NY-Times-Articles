package com.demo.articles.data.remote;

import java.io.IOException;

import static com.demo.articles.utils.IConstant.INetworksErrors.NETWORK_ERROR;

/**
 * Created on 17/12/18.
 */
public class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return NETWORK_ERROR;
    }
}
