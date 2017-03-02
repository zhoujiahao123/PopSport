package com.nexuslink.util;

import com.nexuslink.Run;
import com.nexuslink.RunDao;
import com.nexuslink.Steps;
import com.nexuslink.StepsDao;

import java.util.List;

/**
 * Created by 猿人 on 2017/3/2.
 */

public class UpLoadDatasUtils {
    private static RunDao runDao = DBUtil.getRunDao();
    private static StepsDao stepsDao = DBUtil.getStepsDao();
    private static final int TRUE = 1;
    private static final int FALSE = 0;
    public static void upLoadRunDatas(){
        List<Run> runList = runDao.queryBuilder().where(RunDao.Properties.HasUpLoad.eq(FALSE)).list();

        if(runList != null){
            //进行上传

            //成功就进行重新赋值
            for(int i =0;i<runList.size();i++){
                runList.get(i).setHasUpLoad(true);
            }
            //更新
            runDao.updateInTx(runList);
        }

    }
    public static void upLoadSteps(){
        List<Steps> stepsList = stepsDao.queryBuilder().where(StepsDao.Properties.HasUpLoad.eq(FALSE)).list();
        if(stepsList != null){
            //进行上传

            //成功就进行重新赋值
            for(int i =0;i<stepsList.size();i++){
                stepsList.get(i).setHasUpLoad(true);
            }
            //更新
            stepsDao.updateInTx(stepsList);
        }

    }
}
