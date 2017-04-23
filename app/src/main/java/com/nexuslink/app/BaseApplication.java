 package com.nexuslink.app;

 import android.app.Application;
 import android.content.Context;

 import com.elvishew.xlog.LogLevel;
 import com.elvishew.xlog.XLog;
 import com.nexuslink.DaoMaster;
 import com.nexuslink.DaoSession;
 import com.nexuslink.service.InitService;
 import com.nexuslink.util.cache.DiskLruCacheHelper;
 import com.umeng.socialize.PlatformConfig;


 /**
 * Created by ASUS-NB on 2016/12/17.
 */

public class BaseApplication extends Application {

    public static Context mContext;
    {
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("4258197523", "30268867be9ea03cd1f41c8a93f8795f");    }
     //===============================================缓存
     public static DiskLruCacheHelper helper;
    private static DaoSession daoSession;


    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        XLog.init(LogLevel.ALL);

        InitService.start(this);
    }
    /*
    提供全局context
     */
    public static Context getContext(){
        return mContext;
    }

     public synchronized static DaoSession getDaosession(){
         if(daoSession == null){
             daoSession = new DaoMaster(new DaoMaster.DevOpenHelper(mContext,"PopSport",null)
                     .getWritableDatabase()).newSession();
         }
         return daoSession;
     }

}
