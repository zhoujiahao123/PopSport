package com.nexuslink.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ASUS-NB on 2017/1/15.
 */

public class ToastUtil {
    private static Toast mInstance;

    public static void showToast(Context context,String content){
        if(mInstance ==null){
            mInstance = Toast.makeText(context,content,Toast.LENGTH_SHORT);
        }else {
            mInstance.setText(content);
        }
        mInstance.show();
    }

    public static void showToastLong(Context context,String content){
        if(mInstance ==null){
            mInstance = Toast.makeText(context,content,Toast.LENGTH_LONG);
        }else {
            mInstance.setText(content);
        }
        mInstance.show();
    }
}
