package com.nexuslink.model.writearticlemodel;

import android.util.Log;

import com.nexuslink.config.Api;
import com.nexuslink.config.Constants;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.data.PublishImagesResult;
import com.nexuslink.model.data.WriteArticleResult;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.UserUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 猿人 on 2017/2/24.
 */

public class WriteArticlModeleImpl implements WriteArticleModel {
    Api api = ApiUtil.getInstance(Constants.BASE_URL);
    @Override
    public void publishArticleText(String text, final CallBackListener listener) {
        api.publishArtice(UserUtils.getUserId(),text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WriteArticleResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError((Exception) e);
                    }

                    @Override
                    public void onNext(WriteArticleResult writeArticleResult) {
                        if(writeArticleResult.getCode() == Constants.SUCCESS){
                            listener.onFinish(writeArticleResult.getAId());
                        }else{
                            listener.onError(new Exception("发表话题失败"));
                        }
                    }
                });
    }

    @Override
    public void publishArticleImages(int aId, List<String> filesName, final CallBackListener listener) {
                //通过fileName 封装file

        List<MultipartBody.Part> parts = new ArrayList<>(filesName.size());
        for (int i = 0; i< filesName.size();i++) {
            File file = new File(filesName.get(i));
            //封装requestBody
            RequestBody body = RequestBody.create(MediaType.parse("image/png"),file);
            //封装MutlipartBody
            MultipartBody.Part part = MultipartBody.Part.createFormData("aImgs",file.getName(),body);
            parts.add(part);
        }
        Log.i("图片上传封装","封装完成进行测试");

        //封装完成发送请求
        api.publishImages(UserUtils.getUserId(),aId,parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PublishImagesResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError((Exception) e);
                    }

                    @Override
                    public void onNext(PublishImagesResult publishImagesResult) {
                        if(publishImagesResult.getCode() ==Constants.SUCCESS){
                            listener.onFinish(null);
                        }else{
                            listener.onError(new Exception("上传图片失败"));
                        }
                    }
                });


    }


}
