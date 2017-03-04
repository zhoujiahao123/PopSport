package com.nexuslink.ui.view.fab;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;

/**
 * Created by 猿人 on 2017/3/3.
 */

public class MyFabBehavior extends FloatingActionButton.Behavior {
    private static final FastOutSlowInInterpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    private float viewY ;//距离底部的距离
    private boolean isAnim = false;

    public MyFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        if(child.getVisibility() == View.VISIBLE && viewY == 0){
            viewY = coordinatorLayout.getHeight() - child.getY();
        }
        return  (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;//判断是否竖直滚动;
    }

//    //在嵌套滑动进行,对象消费滚动距离时回调
//    @Override
//    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dx, int dy, int[] consumed) {
//        //dy大于0是向上滚动 小于0是向下滚动
//
//
//    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyConsumed >=0&&!isAnim&&child.getVisibility()==View.VISIBLE) {
            hide(child);
        } else if (dyConsumed <0&&!isAnim&&child.getVisibility()==View.GONE) {
            show(child);
        }
    }

    private void show(final FloatingActionButton child) {
        ViewPropertyAnimator animator = child.animate().translationY(0).setInterpolator(INTERPOLATOR);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                child.setVisibility(View.VISIBLE);
                isAnim = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnim = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                hide(child);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void hide(final FloatingActionButton child) {
        ViewPropertyAnimator animator = child.animate().translationY(viewY).setInterpolator(INTERPOLATOR);

        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnim = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                child.setVisibility(View.GONE);
                isAnim = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                show(child);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }
}
