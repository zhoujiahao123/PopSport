package com.nexuslink.ui.view;

import com.nexuslink.UserDao;
import com.nexuslink.util.DBUtil;

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

}
