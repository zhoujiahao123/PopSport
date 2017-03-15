package com.nexuslink.util;

import android.util.Log;

import com.google.gson.Gson;
import com.nexuslink.Run;
import com.nexuslink.RunDao;
import com.nexuslink.Steps;
import com.nexuslink.StepsDao;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.Result;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;

/**
 * Created by 猿人 on 2017/3/2.
 */

public class UpLoadDatasUtils {

    private  static final RunDao runDao = DBUtil.getRunDao();
    private  static final StepsDao stepsDao = DBUtil.getStepsDao();
    private static final OkHttpClient client = new OkHttpClient.Builder().build();
    private static final Gson gson = new Gson();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public synchronized static void upLoadRunDatas(){
         List<Run> runList = runDao.queryBuilder().where(RunDao.Properties.HasUpLoad.eq(0)).list();
        if(runList == null){
            return;
        }
        for(int i = 0;i<runList.size();i++){
            Run run = runList.get(i);
            //构建body
            int averageIndex = run.getAverageSpeed().indexOf(".");
            int distanceIndex = run.getUMileage().indexOf(".");

            Call<Result> call = ApiUtil.getInstance(Constants.BASE_URL)
                    .postDistance(UserUtils.getUserId(),Integer.parseInt(run.getUMileage().substring(0,distanceIndex))
                    , Integer.parseInt(run.getDuration()), Integer.parseInt(run.getAverageSpeed().substring(0,averageIndex))
                    ,run.getPathLine(),run.getStartPoint(),run.getEndPoint(),run.getDate()+" "+run.getTime());
            try {
                retrofit2.Response<Result> response = call.execute();
                //如果上传成功就进行更新
                Log.i("upLoad",response.body().getCode()+"");
               if(response.body().getCode() == Constants.SUCCESS){
                   runList.get(i).setHasUpLoad(true);
               }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //循环完成，进行更新
        runDao.updateInTx(runList);

    }
    public  synchronized static void upLoadSteps(){
        //找到昨天以前并且未上传的数据
     List<Steps> stepsList = stepsDao.queryBuilder().where(StepsDao.Properties.HasUpLoad.eq(0)
             ,StepsDao.Properties.Date.lt(sdf.format(System.currentTimeMillis()))).list();

       if(stepsList != null) {
            for (int i = 0; i < stepsList.size(); i++) {

                ApiUtil.getInstance(Constants.BASE_URL)
                        .postStep(UserUtils.getUserId(),stepsList.get(i).getUStep(),stepsList.get(i).getDate());


                //更新
                stepsDao.updateInTx(stepsList);
            }
        }
    }
}
