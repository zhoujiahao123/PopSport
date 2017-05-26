package com.nexuslink.presenter.writearticlepresenter;

import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.writearticlemodel.WriteArticlModeleImpl;
import com.nexuslink.model.writearticlemodel.WriteArticleModel;
import com.nexuslink.ui.view.WriteArticleView;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/24.
 */

public class WriteArticlePresenterImpl implements WriteArtilcePresenter {
    private WriteArticleView mView;
    private WriteArticleModel mArticleModel;

    public WriteArticlePresenterImpl(WriteArticleView mView) {
        this.mView = mView;
        mArticleModel = new WriteArticlModeleImpl();
    }

    @Override
    public void publishArticle(final List<String> filesName) {
        mView.showProgress();
        mArticleModel.publishArticleText(mView.getInputText(), new CallBackListener() {
            @Override
            public void onFinish(Object o) {
                //如果成功就进行图片的上传
                if(filesName!=null && filesName.size() != 0){
                    int aId = (int) o;
                    mArticleModel.publishArticleImages(aId, filesName, new CallBackListener() {
                        @Override
                        public void onFinish(Object o) {
                            mView.hideProgress();
                            mView.showSuccess();
                        }
                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                        }
                    });

                }else{
                    //未发表图片
                    mView.hideProgress();
                    mView.showSuccess();
                }

            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                mView.hideProgress();
                mView.showError(e.getMessage());
            }
        });
    }
}
