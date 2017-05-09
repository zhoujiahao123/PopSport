package com.nexuslink.ui.view;

import java.util.List;

/**
 * Created by 猿人 on 2017/4/8.
 */

public interface IFansView<T> extends BaseView {
    void setDatas(List<T> datas);
}
