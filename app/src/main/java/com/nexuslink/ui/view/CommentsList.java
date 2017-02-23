package com.nexuslink.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 猿人 on 2017/2/23.
 */

public class CommentsList extends RecyclerView {
    public CommentsList(Context context) {
        this(context,null);
    }

    public CommentsList(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CommentsList(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        //始终不拦截,
        return false;
    }
}
