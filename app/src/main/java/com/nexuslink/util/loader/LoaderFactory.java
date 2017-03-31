package com.nexuslink.util.loader;

/**
 * Created by ASUS-NB on 2017/3/4.
 */

public class LoaderFactory {
    private static ILoader loader;
    public static ILoader getGlideLoader(){
        if(loader == null){
            synchronized (GlideLoader.class){
                if(loader==null){
                    loader = new GlideLoader();
                }
            }
        }
        return loader;
    }
}
