package com.nexuslink.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
                .error(R.drawable.error__photo)
                .into(imageView);
    }
    public static void imageDisplayWithFile(File file ,ImageView imageView){
        Glide.with(BaseApplication.getContext())
                .load(file)
                .error(R.drawable.error__photo)
                .into(imageView);
    }
}
