package com.nexuslink.util;

import android.util.Base64;

/**
 * Created by 猿人 on 2017/3/5.
 */

public class Base64Utils {
    /**
     * 加密
     * @param str
     * @return
     */
    public static String encode(String str){
        return new String(Base64.encode(str.getBytes(),Base64.DEFAULT));
    }
    /**
     * 解密
     */
    public static String decode(String str){
        return new String(Base64.decode(str.getBytes(),Base64.DEFAULT));
    }
}
