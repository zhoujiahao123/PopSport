package com.nexuslink.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;

import com.facebook.stetho.Stetho;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.util.GlideImageLoader;
import com.nexuslink.util.cache.DiskLruCacheHelper;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMShareAPI;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

import java.io.IOException;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by 猿人 on 2017/3/30.
 */

public class InitService extends IntentService {
    public static final String INIT_ONCREATE = "com.nexuslink.pop.oncreate";

    public InitService() {
        super("InitService");
    }
    public static void start(Context context){
        Intent intent = new Intent(context,InitService.class);
        intent.setAction(INIT_ONCREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            if(intent.getAction().equals(INIT_ONCREATE)){
                performInit();
            }
        }

    }

    private void performInit() {
        UMShareAPI.get(this);
        Config.DEBUG = true;
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
        //创建数据库
        //初始化缓存
        try {
            BaseApplication.helper = new DiskLruCacheHelper(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化图片选择器
        ThemeConfig GREEN = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.rgb(0x4C, 0xAF, 0x50))
                .setFabNornalColor(Color.rgb(0x4C, 0xAF, 0x50))
                .setFabPressedColor(Color.rgb(0x38, 0x8E, 0x3C))
                .setCheckSelectedColor(Color.rgb(0x4C, 0xAF, 0x50))
                .setCropControlColor(Color.rgb(0x4C, 0xAF, 0x50))
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();
        //配置imageloader
        ImageLoader imageloader = new GlideImageLoader();
        //设置核心配置信息
        CoreConfig coreConfig = new CoreConfig.Builder(getApplicationContext(), imageloader, GREEN)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
        /**
         * emoji表情
         */
        EmojiManager.install(new IosEmojiProvider());



    }
}
