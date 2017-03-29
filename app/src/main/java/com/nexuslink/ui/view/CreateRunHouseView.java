package com.nexuslink.ui.view;

/**
 * Created by 猿人 on 2017/2/25.
 */

public interface CreateRunHouseView {
    void showSuccess();
    void showError();
    void showProgress();
    void hideProgress();
    int getType();
    int getGoal();
    String getRoomName();
    String getStartTime();
    void insertOneRoom(int rId);
}
