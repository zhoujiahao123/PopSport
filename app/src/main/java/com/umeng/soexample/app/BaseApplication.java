 package com.umeng.soexample.app;

import android.app.Application;
import android.content.Context;

import com.antfortune.freeline.FreelineCore;
import com.facebook.stetho.Stetho;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


/**
 * Created by ASUS-NB on 2016/12/17.
 */

public class BaseApplication extends Application {
    public static Context mContext;
    {
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        FreelineCore.init(this);
        Config.DEBUG = true;
        mContext = getApplicationContext();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
    /*
    提供全局context
     */
    public static Context getContext(){
        return mContext;
    }
}
