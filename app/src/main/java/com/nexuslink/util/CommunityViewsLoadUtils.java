package com.nexuslink.util;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by 猿人 on 2017/2/14.
 */

public class CommunityViewsLoadUtils {
    /**
     * 用户头像加载
     */
    public static void loadUserImage(Context context,ImageView imageView, String url){
        Glide.with(context).load(url).into(imageView);
    }
    /**
     * 用户名
     */
    public static void loadUserName(TextView textView,String userName){
        textView.setText(userName);
    }
    /**
     * 用户等级
     */
    public static void loadUserLevel(TextView textView,String userLevel){
        textView.setText("Lv."+userLevel);
    }

}
