package com.nexuslink.util;

import okhttp3.OkHttpClient;

/**
 * Created by 猿人 on 2017/4/27.
 */

public class OkHttpUtils {
    private static OkHttpClient client;


    public synchronized static OkHttpClient getInstance() {
        if (client == null){
            client =  new OkHttpClient();
        }
        return client;
    }
}
