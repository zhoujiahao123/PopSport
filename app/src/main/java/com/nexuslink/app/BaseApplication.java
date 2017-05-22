package com.nexuslink.app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

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

public class BaseApplication extends MultiDexApplication {

    public static Context mContext;
    //===============================================缓存
    public static DiskLruCacheHelper helper;
    private static DaoSession daoSession;


    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        XLog.init(LogLevel.ALL);
        Log.i("application","onCreate");
        InitService.start(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public synchronized static DiskLruCacheHelper getHelper() {
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
