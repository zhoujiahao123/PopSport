package com.nexuslink.ui.view;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/5.
 */

public interface RunHouseView<T> extends BaseView {
    void showProgress();
    void hideProgress();
    void showError(String message);
    void showSuccess();
    void showNoMore();
    void setRunHouseDatas(List<T> list);
    void addRunHouse(List<T> list);
}
