package com.nexuslink.util;

import com.nexuslink.HasJoinedRoomsDao;
import com.nexuslink.RunDao;
import com.nexuslink.StepsDao;
import com.nexuslink.UserDao;
import com.nexuslink.app.BaseApplication;

/**
 * Created by 猿人 on 2017/1/14.
 */

public class DBUtil {



    public static StepsDao getStepsDao(){
        return BaseApplication.getDaosession().getStepsDao();
    }
    public static RunDao getRunDao(){
        return BaseApplication.getDaosession().getRunDao();
    }
    public static UserDao getUserDao(){
        return BaseApplication.getDaosession().getUserDao();
    }

    public static HasJoinedRoomsDao getHasJoinedRoomsDap(){
        return BaseApplication.getDaosession().getHasJoinedRoomsDao();
    }
    public static void clearAll(){
        BaseApplication.getDaosession().getStepsDao().deleteAll();
        BaseApplication.getDaosession().getRunDao().deleteAll();
        BaseApplication.getDaosession().getHasJoinedRoomsDao().deleteAll();
        BaseApplication.getDaosession().getTaskMileagesDao().deleteAll();
        BaseApplication.getDaosession().getTaskStepsDao().deleteAll();
        BaseApplication.getDaosession().getUserDao().deleteAll();
    }

}
