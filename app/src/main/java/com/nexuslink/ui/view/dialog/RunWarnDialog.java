package com.nexuslink.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nexuslink.R;

/**
 * Created by 猿人 on 2017/2/27.
 */

public class RunWarnDialog extends Dialog implements View.OnClickListener {
    //===============================================view
    TextView confirm;
    TextView cancel;
    Context mContext;

    private static int default_width = 160; //默认宽度
    private static int default_height = 120;//默认高度

    /**
     * 回调接口
     */
    public interface OnClickListener{
        void onClick(boolean confirm);
    }
    OnClickListener listener;
    public void setOnClickListenr(OnClickListener listenr){
        this.listener = listenr;
    }

    public RunWarnDialog(Context context) {
        super(context);
        mContext = context;
    }

    public RunWarnDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.run_wran_dialog,null);
        this.setContentView(view);
        confirm = (TextView) findViewById(R.id.confirm);
        cancel = (TextView) findViewById(R.id.cancel);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        //设置窗口大小
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //set width,height by density and gravity
        float density = getDensity(mContext);
        params.width = (int) (default_width*density);
        params.height = (int) (default_height*density);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }
    private float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm:
                if(listener != null){
                    listener.onClick(true);
                }
                break;
            case R.id.cancel:
                if(listener != null){
                    listener.onClick(false);
                }
                break;
            default:
                break;
        }
    }
}
