package com.nexuslink.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.nexuslink.util.DataCleanManager;

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

    }
    private void clearSharedPreference(Context mContext){
        DataCleanManager.cleanSharedPreference(mContext);
    }
}
