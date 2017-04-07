package com.nexuslink.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;

import com.nexuslink.R;

/**
 * Created by 猿人 on 2017/4/7.
 */

public class PersonInfoBezierView extends ConstraintLayout {

    private final String TAG = "PersonInfoView";

    //贝塞尔曲线画笔
    private Paint mPaint;
    //绘制路径
    private Path mPath = new Path();
    //容器中y值最大的view下的padding长度
    private int bottomPadding = 5;
    //贝塞尔控制点距离容器中y值最大的view的纵坐标长度
    private int bottomHeight = 100;
    //最大底部宽度
    private int maxBottom = 0;

    //控制点
    private int mStartPointX, mStartPointY, mEndPointX, mEndPointY;
    private int mControlX, mControlY;

    //颜色
    private int dstColor = Color.WHITE;

    public void setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
    }

    public void setBottomHeight(int bottomHeight) {
        this.bottomHeight = bottomHeight;
    }

    public PersonInfoBezierView(Context context) {
        this(context, null);
    }

    public PersonInfoBezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonInfoBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PersonInfoBezierView, defStyleAttr, 0);
        bottomPadding = (int) a.getDimension(R.styleable.PersonInfoBezierView_paddingBottom, 5);
        bottomHeight = (int) a.getDimension(R.styleable.PersonInfoBezierView_paddingHeight, 100);
        dstColor = a.getColor(R.styleable.PersonInfoBezierView_dstColor, Color.WHITE);
        a.recycle();
    }

    private void init() {
        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(dstColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //初始化横坐标点
        mStartPointX = 0;
        mEndPointX = w;
        mControlX = w / 2;
        Log.i(TAG, "ControlX:" + mControlX);
        Log.i(TAG, "h:" + h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        maxBottom = getMeasuredHeight();
        //初始化参数
        mStartPointY = maxBottom + bottomPadding;
        mEndPointY = maxBottom + bottomPadding;
        mControlY = maxBottom + bottomPadding + bottomHeight;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mControlY, heightMode));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "mControlY:" + mControlY);
        if (mControlX != 0 && mControlY != 0) {
            mPath.reset();
            mPath.moveTo(mStartPointX, mStartPointY);
            mPath.quadTo(mControlX, mControlY, mEndPointX, mEndPointY);
            mPath.lineTo(mEndPointX, mControlY);
            mPath.lineTo(mStartPointX, mControlY);
            mPath.lineTo(mStartPointX, mStartPointY);
            canvas.drawPath(mPath, mPaint);
        }
    }
}
