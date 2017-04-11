package com.nexuslink.ui.view;

import com.nexuslink.model.data.RoomsBean;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/25.
 */

public interface RunHouseDetailView {
    void showProgress();
    void hideProgress();
    void showSuccess();
    void showError();
    void setDatas(List<RoomsBean.UsersBean> users);
    void removeItem(int userId);
    void insertOneRoom();
}
