package com.nexuslink.util;

/**
 * Created by 猿人 on 2017/2/10.
 */

public interface SaveImageListener {
    void onLoadSuccess(int pos,String filePath);
    void onLoadFailed(String str);
}
