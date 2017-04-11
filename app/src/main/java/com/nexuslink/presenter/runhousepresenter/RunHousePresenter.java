package com.nexuslink.presenter.runhousepresenter;

import com.nexuslink.presenter.IBasePresenter;

/**
 * Created by 猿人 on 2017/2/25.
 */

public interface RunHousePresenter extends IBasePresenter{
    void onRefresh(int startId,boolean autoRefresh);
    void onLoadMore(int startId);
    void getMyRooms(int uId);
}
