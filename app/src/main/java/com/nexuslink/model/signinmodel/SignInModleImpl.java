package com.nexuslink.model.signinmodel;

import com.nexuslink.config.Constants;
import com.nexuslink.model.data.UIdInfo;
import com.nexuslink.model.data.UpLoadUserImageResult;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.BitmapCompressUpUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/2/19.
 */

public class SignInModleImpl implements SignInModel {

    @Override
    public void requestRegister(String uName, String uPassword, char uGender, int uHeight, int uWeight, final String imagePath, final com.nexuslink.model.signinmodel.OnCallBackListener listener) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .requestRegister(uName, uPassword, uGender, uHeight, uWeight)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<UIdInfo, Observable<UpLoadUserImageResult>>() {
                    @Override
                    public Observable<UpLoadUserImageResult> call(UIdInfo uIdInfo) {
                        if (uIdInfo.getuId() == 0) {
//                            ToastUtil.showToast(BaseApplication.getContext(),"用户名已经存在");
                            listener.onFailed(new Throwable("用户名已存在"));
                            return null;
                        } else if (uIdInfo.getCode() == 500) {
                            listener.onFailed(new Throwable("服务器出现错误"));
                            return null;
                        } else {
                            //注册成功，进行用户头像注册
                            //将图片进行压缩
                            String str = BitmapCompressUpUtils.compressImage(imagePath, imagePath + "111", 50);
                            File file = new File(str);
                            RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);
                            return ApiUtil.getInstance(Constants.BASE_URL).changUserImage(uIdInfo.getuId(), body);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UpLoadUserImageResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UpLoadUserImageResult upLoadUserImageResult) {
                        if (upLoadUserImageResult.getCode() == Constants.SUCCESS) {
                            listener.onSucceed(null);
                        }else{
                            listener.onFailed(null);
                        }
                        deletePhoto(imagePath);
                    }
                });
    }

    @Override
    public void getError() {

    }
    private void deletePhoto(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
}
