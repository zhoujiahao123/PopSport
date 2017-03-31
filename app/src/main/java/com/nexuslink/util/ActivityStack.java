package com.nexuslink.util;

/**
 * Created by 猿人 on 2017/3/31.
 */

import android.app.Activity;

import java.util.Stack;

/**
 * Activity管理
 */
public class ActivityStack {
    private static Stack<Activity> mActivityStack = new Stack<Activity>();
    private static ActivityStack instance = new ActivityStack();

    private ActivityStack() {
    }

    public static ActivityStack getScreenManager() {
        return instance;
    }

    // 弹出当前activity并销毁
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            mActivityStack.remove(activity);
            activity = null;
        }
    }

    // 将当前Activity推入栈中
    public void pushActivity(Activity activity) {
        mActivityStack.add(activity);
    }

    // 退出栈中所有Activity
    public void clearAllActivity() {
        while (!mActivityStack.isEmpty()) {
            Activity activity = mActivityStack.pop();
            if (activity != null) {
                activity.finish();
            }
        }
    }

}

