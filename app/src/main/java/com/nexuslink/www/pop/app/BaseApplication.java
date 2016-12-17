package com.nexuslink.www.pop.app;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


/**
 * Created by ASUS-NB on 2016/12/17.
 */

public class BaseApplication extends Application {
    {
        PlatformConfig.setQQZone("100424468", "585523ac310c933504000104");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
    }
}
