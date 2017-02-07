package com.nexuslink.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nexuslink.R;
import com.nexuslink.app.BaseApplication;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public class ImageUtil {
    public static void imageDisplay(String url,ImageView imageView){
        Glide.with(BaseApplication.getContext())
                .load(url)
                .placeholder(R.drawable.head)
                .error(R.drawable.errorphoto)
                .into(imageView);
    }
}
