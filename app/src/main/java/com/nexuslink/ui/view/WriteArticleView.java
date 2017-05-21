package com.nexuslink.ui.view;

/**
 * Created by 猿人 on 2017/2/24.
 */

public interface WriteArticleView {
    String getInputText();
    void showProgress();
    void hideProgress();
    void showSuccess();
    void showError(String msg);
}
