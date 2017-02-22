package com.nexuslink.util;

import com.nexuslink.UserDao;
import com.nexuslink.app.BaseApplication;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public class IdUtil {
    public static long getuId(){
        return BaseApplication.getDaosession()
                .getUserDao()
                .queryBuilder()
                .where(UserDao.Properties.Already.eq(1))
                .unique().getUid();
    }
}
