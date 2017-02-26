package com.nexuslink.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.elvishew.xlog.XLog;
import com.nexuslink.R;
import com.nexuslink.app.BaseApplication;

import java.io.File;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public class ImageUtil {

    public static void imageDisplay(String url,ImageView imageView){
        Glide.with(BaseApplication.getContext())
                .load(url)
                .error(R.drawable.head_photo)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }
    public static void imageDisplayWithFile(File file ,ImageView imageView){
        Glide.with(BaseApplication.getContext())
                .load(file)
                .error(R.drawable.head_photo)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }
    public static void imageDisplayHeadImage(String url ,ImageView imageView){
        Glide.with(BaseApplication.getContext())
                .load(url)
                .error(R.drawable.head_photo)
                .diskCacheStrategy(DiskCacheStrategy.NONE).
                skipMemoryCache(true)
                .into(imageView);
    }
}
