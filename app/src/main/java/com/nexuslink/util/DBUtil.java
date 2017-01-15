package com.nexuslink.util;

import com.nexuslink.DaoMaster;
import com.nexuslink.DaoSession;
import com.nexuslink.StepsDao;
import com.nexuslink.app.BaseApplication;

/**
 * Created by 猿人 on 2017/1/14.
 */

public class DBUtil {

    //管理对象
    private static DaoMaster master;
    private static DaoSession session;

    public static StepsDao getStepsDap(){
        master = new DaoMaster(BaseApplication.db);
        session = master.newSession();
        return session.getStepsDao();
    }

}
