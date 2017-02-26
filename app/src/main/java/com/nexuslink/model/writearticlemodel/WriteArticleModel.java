package com.nexuslink.model.writearticlemodel;

import com.nexuslink.model.CallBackListener;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/24.
 */

public interface WriteArticleModel {
    void publishArticleText(String text, CallBackListener listener);
    void publishArticleImages(int aId, List<String> files,CallBackListener listener);
}
