package com.nexuslink.app;

import android.app.Application;
import android.content.Context;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.nexuslink.DaoMaster;
import com.nexuslink.DaoSession;
import com.nexuslink.service.InitService;
import com.nexuslink.util.cache.DiskLruCacheHelper;

import java.io.IOException;


/**
 * Created by ASUS-NB on 2016/12/17.
 */

public class BaseApplication extends Application {

    public static Context mContext;
    //===============================================缓存
    public static DiskLruCacheHelper helper;
    private static DaoSession daoSession;


    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        XLog.init(LogLevel.ALL);

        InitService.start(this);
    }

    public static DiskLruCacheHelper getHelper() {
        if(helper == null){
            try {
                helper = new DiskLruCacheHelper(mContext);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return helper;
    }


    /*
        提供全局context
         */
    public static Context getContext() {
        return mContext;
    }

    public synchronized static DaoSession getDaosession() {
        if (daoSession == null) {
            daoSession = new DaoMaster(new DaoMaster.DevOpenHelper(mContext, "PopSport", null)
                    .getWritableDatabase()).newSession();
        }
        return daoSession;
    }

}
