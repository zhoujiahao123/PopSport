package com.nexuslink.util.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.io.File;

/**
 * Created by ASUS-NB on 2017/3/4.
 */

public class GlideLoader implements ILoader {
    @Override
    public void loadNet(ImageView target, String url, Option option) {
        load(getRequestManager(target.getContext()).load(url),target,option);
    }

    @Override
    public void loadFile(ImageView target, File file, Option option) {
        load(getRequestManager(target.getContext()).load(file),target,option);
    }

    @Override
    public void loadResource(ImageView target, int res, Option option) {
        load(getRequestManager(target.getContext()).load(res),target,option);
    }

    @Override
    public void clearDiskMemory(Context context) {
        Glide.get(context).clearDiskCache();
    }

    @Override
    public void clearCacheMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    public RequestManager getRequestManager(Context context){
        return Glide.with(context);
    }

    public void load(DrawableTypeRequest request,ImageView target,Option option){
        if(option == null){
            option = Option.getDefaultOption();
        }
        if(option.resLoading!=Option.RES_NONE){
            request.placeholder(option.resLoading);
        }
        if(option.resLoadError!=Option.RES_NONE){
            request.error(option.resLoadError);
        }

        request.crossFade().dontAnimate().into(target);
    }
}
