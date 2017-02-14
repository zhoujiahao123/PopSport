package com.nexuslink.ui.view;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 猿人 on 2017/2/14.
 */

public interface CommunityUserInfoView {
    void loadUserInfo(ImageView imageView, TextView nameText, TextView levelText,String imageUrl, String useName, String userLevel);
}
