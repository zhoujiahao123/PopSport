package com.nexuslink.util.loader;

import android.content.Context;
import android.widget.ImageView;

import com.nexuslink.config.Constants;

import java.io.File;

/**
 * Created by ASUS-NB on 2017/3/4.
 */

public interface ILoader {
    void loadNet(ImageView target,String url,Option option);
    void loadFile(ImageView target, File file, Option option);
    void loadResource(ImageView target,int res,Option option);

    void clearDiskMemory(Context context);
    void clearCacheMemory(Context context);

    class Option{
        public static final int RES_NONE = -1;
        public int resLoading = RES_NONE;
        public int resLoadError = RES_NONE;

        public static Option getDefaultOption(){
            return new Option(Constants.RES_LOADING,Constants.RES_LOAD_ERROR);
        }
        public Option(int resLoading,int resLoadError){
            this.resLoading = resLoading;
            this.resLoadError = resLoadError;
        }
    }
}
