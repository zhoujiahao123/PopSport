package com.nexuslink.model.runhousemodel;

import com.nexuslink.config.Api;
import com.nexuslink.config.Constants;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.data.LoadRoomsResult;
import com.nexuslink.model.data.MyLoadRoomsResult;
import com.nexuslink.model.data.RoomsBean;
import com.nexuslink.util.ApiUtil;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 猿人 on 2017/2/5.
 */

public class RunHouseModeImp implements RunHouseModel<List<RoomsBean>> {

    Api api = ApiUtil.getInstance(Constants.BASE_URL);

    @Override
    public void onRefresh(int startId, final CallBackListener<List<RoomsBean>> listener) {
        api.getRooms(startId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LoadRoomsResult>() {
                    @Override
                    public void call(LoadRoomsResult loadRoomsResult) {
                        if(loadRoomsResult.getCode() == Constants.SUCCESS){
                            listener.onFinish(loadRoomsResult.getRoom());
                        }else{
                            listener.onError(new Exception("获取跑房信息时出错"));
                        }
                    }
                });
    }

    @Override
    public void getMyRooms(int uId, final CallBackListener<List<RoomsBean>> listener) {
        api.getMyRooms(uId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MyLoadRoomsResult>() {
                    @Override
                    public void call(MyLoadRoomsResult loadRoomsResult) {
                       if(loadRoomsResult.getCode() == Constants.SUCCESS || loadRoomsResult.getCode()== Constants.FAILED){
                           listener.onFinish(loadRoomsResult.getRooms());
                       }
                    }
                });
//        final OkHttpClient client = new OkHttpClient();
//        FormBody body = new FormBody.Builder().add("uId", String.valueOf(UserUtils.getUserId())).build();
//        final Request request = new Request.Builder().url(Constants.BASE_URL+"room/myRooms").post(body).build();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response response = client.newCall(request).execute();
//                    MyLoadRoomsResult result = new Gson().fromJson(response.body().string(),MyLoadRoomsResult.class);
//                    Log.i("result",result.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }
}


