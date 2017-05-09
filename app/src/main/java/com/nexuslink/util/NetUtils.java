package com.nexuslink.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by 猿人 on 2017/4/1.
 */

public class NetUtils {
    public static boolean isNetConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(networkInfo!=null&&networkInfo.isAvailable()){
            return true;
        }else {
            return false;
        }
    }
}
