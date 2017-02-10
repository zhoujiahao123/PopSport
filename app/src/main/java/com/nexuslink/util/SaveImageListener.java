package com.nexuslink.util;

import android.graphics.Bitmap;

/**
 * Created by 猿人 on 2017/2/10.
 */

public interface SaveImageListener {
    void onLoadSuccess(Bitmap bitmap);
    void onLoadFailed(String str);
}
