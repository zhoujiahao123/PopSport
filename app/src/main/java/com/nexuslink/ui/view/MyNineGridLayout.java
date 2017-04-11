package com.nexuslink.ui.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.nexuslink.R;
import com.nexuslink.ui.activity.ViewImageShowActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 猿人 on 2017/4/7.
 */

public class MyNineGridLayout extends NineGridLayout {

    protected static final int MAX_W_H_RATIO = 3;
    private Context mContext;

    public MyNineGridLayout(Context context) {
        super(context);
        mContext = context;
    }

    public MyNineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected boolean displayOneImage(RatioImageView imageView, String url, int parentWidth) {
        Glide.with(mContext).load(url).thumbnail(0.3f).placeholder(R.drawable.loadding).into(imageView);
        return true ;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        Glide.with(mContext).load(url).thumbnail(0.3f).placeholder(R.drawable.loadding).into(imageView);
    }

    @Override
    protected void onClickImage(int position, String url, List<String> urlList) {
        Intent intent = new Intent(mContext, ViewImageShowActivity.class);
        intent.putExtra(ViewImageShowActivity.IMAGE_NUM,position);
        intent.putExtra(ViewImageShowActivity.IMAGES_DATA_LIST, (Serializable) urlList);
        mContext.startActivity(intent);
    }
}
