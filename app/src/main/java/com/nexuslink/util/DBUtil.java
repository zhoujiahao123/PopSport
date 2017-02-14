package com.nexuslink.util;

import com.nexuslink.DaoMaster;
import com.nexuslink.DaoSession;
import com.nexuslink.RunDao;
import com.nexuslink.StepsDao;
import com.nexuslink.UserDao;
import com.nexuslink.app.BaseApplication;

/**
 * Created by 猿人 on 2017/1/14.
 */

public class DBUtil {

    //管理对象
    private static DaoMaster master;
    private static DaoSession session;

    public static StepsDao getStepsDao(){
        master = new DaoMaster(BaseApplication.db);
        session = master.newSession();
        return session.getStepsDao();
    }
    public static RunDao getRunDao(){
        master = new DaoMaster(BaseApplication.db);
        session = master.newSession();
        return session.getRunDao();
    }
    public static UserDao getUserDao(){
        master = new DaoMaster(BaseApplication.db);
        session = master.newSession();
        return session.getUserDao();
    }

}
