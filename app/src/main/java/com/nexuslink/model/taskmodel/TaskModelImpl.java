package com.nexuslink.model.taskmodel;

import com.elvishew.xlog.XLog;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.TaskFlag;
import com.nexuslink.util.ApiUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/2/18.
 */

public class TaskModelImpl implements TaskModel{
    @Override
    public void upLoadTask(int uId, int type, int taskNum, final com.nexuslink.model.taskmodel.OnCallBackListener listener) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .upLoadTask(uId,type,taskNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TaskFlag>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(e);
                        XLog.e(e.toString());
                    }

                    @Override
                    public void onNext(TaskFlag taskFlag) {
                        XLog.e("成功");
                    }
                });
    }

    @Override
    public void getError() {

    }
}
