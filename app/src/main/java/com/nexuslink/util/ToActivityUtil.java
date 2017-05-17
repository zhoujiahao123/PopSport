package com.nexuslink.util;
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.List;
 
/**
 * 
 * @Description:这个工具类主要通过显式意图来跳转到指定Activity 大家可以再添加一些通过隐式意图来跳转的重载方法.
 * 
 */
public class ToActivityUtil {
 
    /**
     * 
     * @Description: 隐式启动,跳转
     * @param packageContext
     * @param action
     *            含操作的Intent
     */
    public static void startActivityIntentSafe(Context packageContext,
            Intent action) {
        // Verify it resolves
        PackageManager packageManager = packageContext.getPackageManager();
        List activities = packageManager.queryIntentActivities(action,
                PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;
 
        // Start an activity if it's safe
        if (isIntentSafe) {
            packageContext.startActivity(action);
        }
 
    }
 
    /**
     * @Description: 跳转,带参数的方法;需要其它的数据类型,再继续重载吧,暂时只写这么多吧,意义不大
     * @param packageContext
     * @param cls
     * @param keys
     * @param values  手动改变int[] values类型,可以传递其它数据类型,就不重载了
     */
    public static void toNextActivity(Context packageContext, Class<?> cls,
            String[] keys, int[] values) {
        Intent i = new Intent(packageContext, cls);
        for (int j = 0; j < values.length; j++) {
            i.putExtra(keys[j], values[j]);
        }
        packageContext.startActivity(i);
 
    }
 
    /**
     * @Description: 跳转
     * @param packageContext
     *            from,一般传XXXActivity.this
     * @param cls
     *            to,一般传XXXActivity.class
     */
    public static void toNextActivity(Context packageContext, Class<?> cls) {
        Intent i = new Intent(packageContext, cls);
        packageContext.startActivity(i);
    }
 
    /**
     * @Description: 跳转,带参数的方法;需要其它的数据类型,再继续重载吧
     * @param packageContext
     * @param cls
     * @param keyvalues
     *            需要传进去的String参数{{key1,values},{key2,value2}...}
     */
    public static void toNextActivity(Context packageContext, Class<?> cls,
            String[][] keyvalues) {
        Intent i = new Intent(packageContext, cls);
        for (String[] strings : keyvalues) {
            i.putExtra(strings[0], strings[1]);
        }
        packageContext.startActivity(i);
 
    }
 
    public static void toNextActivityAndFinish(Context packageContext,
            Class<?> cls) {
        Intent i = new Intent(packageContext, cls);
        packageContext.startActivity(i);
 
        ((Activity) packageContext).finish();
    }
 
    public static void finish(Activity activity) {
        activity.finish();
    }
}