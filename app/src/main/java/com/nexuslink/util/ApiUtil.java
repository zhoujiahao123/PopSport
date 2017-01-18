package com.nexuslink.util;

import com.nexuslink.config.Api;

/**
 * Created by ASUS-NB on 2017/1/15.
 */

public class ApiUtil {
    private static Api mInstance ;

    public static Api getInstance(String baseUrl){
        if(mInstance==null){
            mInstance = RetrofitUtil.getRetrofitInstance(baseUrl).create(Api.class);
        }
        return mInstance;
    }
}
