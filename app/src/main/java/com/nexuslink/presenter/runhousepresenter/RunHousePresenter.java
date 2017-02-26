package com.nexuslink.presenter.runhousepresenter;

/**
 * Created by 猿人 on 2017/2/25.
 */

public interface RunHousePresenter {
    void onRefresh(int startId,boolean autoRefresh);
    void onLoadMore(int startId);
}
