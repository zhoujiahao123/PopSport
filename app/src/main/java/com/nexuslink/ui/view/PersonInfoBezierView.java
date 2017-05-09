package com.nexuslink.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

import com.nexuslink.R;
import com.nexuslink.util.BezierUtil;
import com.nexuslink.util.Point;
import com.wuxiaolong.androidutils.library.DisplayMetricsUtil;

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
    //需要绘制的底部图形
    private Bitmap mBitmap;
    private int picWidth;
    private int picHeight;
    /**
     * 宽高
     */
    private int hegiht;
    private Context mContext;
    //控制点
    private int mStartPointX, mStartPointY, mEndPointX, mEndPointY;
    private int mControlX, mControlY;
    //颜色
    private int dstColor = Color.WHITE;

    public void setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
        invalidate();
    }

    public void setBottomHeight(int bottomHeight) {
        this.bottomHeight = bottomHeight;
        invalidate();
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
        invalidate();
    }

    public int getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(int picWidth) {
        this.picWidth = picWidth;
        invalidate();
    }

    public int getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(int picHeight) {
        this.picHeight = picHeight;
        invalidate();
    }

    public PersonInfoBezierView(Context context) {
        this(context, null);
    }

    public PersonInfoBezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonInfoBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PersonInfoBezierView, defStyleAttr, 0);
        bottomPadding = (int) a.getDimension(R.styleable.PersonInfoBezierView_paddingBottom, 5);
        bottomHeight = (int) a.getDimension(R.styleable.PersonInfoBezierView_paddingHeight, 100);
        dstColor = a.getColor(R.styleable.PersonInfoBezierView_dstColor, Color.WHITE);
        BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(R.styleable.PersonInfoBezierView_bottomPic);
        if(drawable != null){
            mBitmap = drawable.getBitmap();
        }
        picWidth = (int) a.getDimension(R.styleable.PersonInfoBezierView_bottomPicWidth,0);
        picHeight = (int) a.getDimension(R.styleable.PersonInfoBezierView_bottomPicHeight,0);
        a.recycle();
    }

    private void init() {
        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(dstColor);
    }

    Point vertex;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //初始化横坐标点
        mStartPointX = 0;
        mEndPointX = DisplayMetricsUtil.getScreenWidth(mContext);
        mControlX = mEndPointX / 2;
        hegiht = getMeasuredHeight();
        //初始化参数
        mStartPointY = hegiht + bottomPadding;
        mEndPointY = hegiht + bottomPadding;
        mControlY = hegiht + bottomPadding + bottomHeight;
        //得曲线顶点
        vertex  = BezierUtil.CalculateBezierPointForQuadratic(0.5f
                ,new Point(mStartPointX,mStartPointY),
                new Point(mControlX,mControlY),new Point(mEndPointX,mEndPointY));
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec((int) (vertex.getY()+picHeight/2), heightMode));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mControlX != 0 && mControlY != 0) {
            //绘制曲线
            mPath.reset();
            mPath.moveTo(mStartPointX, mStartPointY);
            mPath.quadTo(mControlX, mControlY, mEndPointX, mEndPointY);
            mPath.lineTo(mEndPointX, mControlY);
            mPath.lineTo(mStartPointX, mControlY);
            mPath.lineTo(mStartPointX, mStartPointY);
            canvas.drawPath(mPath, mPaint);

            if(mBitmap != null){
                //绘制图形
                float left =(vertex.getX() - picWidth/2);
                float right =  (vertex.getX() + picWidth/2);
                float top = (vertex.getY() - picHeight/2);
                float bottom = (vertex.getY() + picHeight/2);
                RectF rect = new RectF(left,top,right,bottom);
                canvas.drawBitmap(mBitmap,null,rect,null);
            }
        }
    }
}
