package com.nexuslink.model.rankmodel;

import com.nexuslink.model.BaseModel;

/**
 * Created by ASUS-NB on 2017/4/5.
 */

public interface RankModel extends BaseModel{
    void getRankInfo(OnRankCallBackLisenter lisenter);
}
