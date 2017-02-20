package com.nexuslink.util;

import com.nexuslink.UserDao;

/**
 * Created by 猿人 on 2017/2/14.
 */

public class UserUtils {
    private static UserDao userDao = DBUtil.getUserDao();

    /**
     * 取得用户id
     * @return
     */
    public static int getUserId(){
        return 15;
    }
    /**
     * 计算user的等级
     */
    public static String getUserLevel(){
        return  "19";
    }
    /**
     * 获取用户昵称
     */
    public static String getUserName(){
        return "张兴锐";
    }

}
