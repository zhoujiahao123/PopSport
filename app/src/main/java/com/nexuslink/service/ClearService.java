package com.nexuslink.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.nexuslink.util.DBUtil;
import com.nexuslink.util.SharedPrefsUtil;

/**
 * Created by 猿人 on 2017/5/15.
 */

public class ClearService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public ClearService() {
        super("ClearDatas");
    }
    
    public static void start(Context context){
        Intent intent = new Intent(context,ClearService.class);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        clearSharedPreference(getApplicationContext());
        DBUtil.clearAll();
    }
    private void clearSharedPreference(Context mContext){
        SharedPrefsUtil.putValue(mContext, "already", "already", 0);
        SharedPrefsUtil.putValue(mContext,"userinfo","friendsNum",0);
        SharedPrefsUtil.putValue(mContext,"userinfo","userImage",null);
        SharedPrefsUtil.putValue(mContext,"userinfo","userName","Pop1号");
        SharedPrefsUtil.putValue(mContext,"userinfo","fansNum",0);
        SharedPrefsUtil.putValue(mContext,"userinfo","sex","M");
        SharedPrefsUtil.putValue(mContext,"userinfo","userLevel",0);
    }
}
