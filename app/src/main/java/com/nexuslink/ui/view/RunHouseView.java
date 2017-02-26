package com.nexuslink.ui.view;

import com.nexuslink.model.data.LoadRoomsResult;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/5.
 */

public interface RunHouseView {
    void showProgress();
    void hideProgress();
    void showError();
    void showSuccess();
    void showNoMore();
    void setRunHouseDatas(List<LoadRoomsResult.RoomBean> list);
    void addRunHouse(List<LoadRoomsResult.RoomBean> list);
}
