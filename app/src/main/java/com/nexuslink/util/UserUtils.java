package com.nexuslink.util;

import com.nexuslink.User;
import com.nexuslink.UserDao;

/**
 * Created by 猿人 on 2017/2/14.
 */

public class UserUtils {

    public static User getUser(){
        return DBUtil.getUserDao().queryBuilder().where(UserDao.Properties.Already.eq(1)).unique();
    }
    /**
     * 取得用户id
     * @return
     */
    public static int getUserId(){
        return getUser().getUid();
    }
    /**
     * 计算user的等级
     */
    public static String getUserLevel(int Exp){
        return  "19";
    }
    /**
     * 获取用户昵称
     */
    public static String getUserName(){
        return getUser().getUName();
    }

    /**
     * 取得用户体重
     */
    public static int getUserWeight(){
        return getUser().getUWeight();
    }

}
