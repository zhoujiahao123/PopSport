package com.nexuslink.model.signinmodel;

import com.elvishew.xlog.XLog;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.UIdInfo;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.ToastUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/2/19.
 */

public class SignInModleImpl implements SignInModel {

    @Override
    public void requestRegister(String uName, String uPassword, char uGender, int uHeight, int uWeight, final com.nexuslink.model.signinmodel.OnCallBackListener listener) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .requestRegister(uName,uPassword,uGender,uHeight,uWeight)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UIdInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(e);
                        XLog.e(e.toString());
                    }

                    @Override
                    public void onNext(UIdInfo uIdInfo) {
                        XLog.e(uIdInfo.getCode());
                        XLog.e(uIdInfo.getuId());
                        if(uIdInfo.getuId()==0){
//                            ToastUtil.showToast(BaseApplication.getContext(),"用户名已经存在");
                            listener.onFailed(new Throwable("用户名已存在"));
                        }else if(uIdInfo.getCode()==500){
                            listener.onFailed(new Throwable("服务器出现错误"));
                        }else {
                            listener.onSucceed(uIdInfo);
                        }
                    }
                });
    }

    @Override
    public void getError() {

    }
}
