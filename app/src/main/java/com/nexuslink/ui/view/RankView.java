package com.nexuslink.ui.view;

import com.nexuslink.model.data.RankInfo;

/**
 * Created by ASUS-NB on 2017/1/14.
 */

public interface RankView extends BaseView{
    //显示用户头像及名称和总的路程
    void showRank(RankInfo rankInfo);
}
