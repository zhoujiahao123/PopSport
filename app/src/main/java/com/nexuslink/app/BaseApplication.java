 package com.nexuslink.app;

 import android.app.Application;
 import android.content.Context;
 import android.database.sqlite.SQLiteDatabase;

 import com.elvishew.xlog.LogLevel;
 import com.elvishew.xlog.XLog;
 import com.facebook.stetho.Stetho;
 import com.nexuslink.DaoMaster;
 import com.nexuslink.DaoSession;
 import com.nexuslink.util.cache.DiskLruCacheHelper;
 import com.umeng.socialize.Config;
 import com.umeng.socialize.PlatformConfig;
 import com.umeng.socialize.UMShareAPI;

 import java.io.IOException;

 import cn.alien95.resthttp.request.RestHttp;


 /**
 * Created by ASUS-NB on 2016/12/17.
 */

public class BaseApplication extends Application {

    public static Context mContext;
    {
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("4258197523", "30268867be9ea03cd1f41c8a93f8795f");    }
    //===============================================数据库
    public static  SQLiteDatabase db;
     //===============================================缓存
     public static DiskLruCacheHelper helper;

    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        Config.DEBUG = true;
        mContext = getApplicationContext();
        XLog.init(LogLevel.ALL);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
        //创建数据库
        db = new DaoMaster.DevOpenHelper(mContext,"PopSport",null).getWritableDatabase();
        //舒适化图片加载库
        RestHttp.initialize(this);
        //初始化缓存
        try {
            helper = new DiskLruCacheHelper(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    提供全局context
     */
    public static Context getContext(){
        return mContext;
    }

     public static DaoSession getDaosession(){
         DaoSession daoSession = new DaoMaster(db).newSession();
         return daoSession;
     }

}
