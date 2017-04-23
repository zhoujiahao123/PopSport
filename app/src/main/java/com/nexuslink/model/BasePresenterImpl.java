package com.nexuslink.model;

import com.nexuslink.presenter.IBasePresenter;
import com.nexuslink.ui.view.BaseView;

/**
 * Created by 猿人 on 2017/4/8.
 */

/**
 *
 * @param <T> view
 * @param <E> 数据
 */
public class BasePresenterImpl<T extends BaseView,E> implements IBasePresenter,CallBackListener<E> {

    protected T mView;

    @Override
    public void onFinish(E o) {
        mView.hideProgress();
    }

    @Override
    public void onError(Exception e) {
        mView.hideProgress();
    }

    @Override
    public void attachView(BaseView view) {
        mView = (T) view;
    }

    @Override
    public void onCreate() {

    }
}
