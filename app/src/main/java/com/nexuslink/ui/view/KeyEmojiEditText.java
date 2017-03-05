package com.nexuslink.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.vanniktech.emoji.EmojiEditText;

/**
 * Created by 猿人 on 2017/3/5.
 */

public class KeyEmojiEditText extends EmojiEditText {
    public boolean isEmojiPoupShown = false;
    public KeyEmojiEditText(Context context) {
        super(context);
    }

    public KeyEmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /***
     * 回调接口
     */
    public interface OnCancelEmojiPoupListener{
       void onCancel();
    }
    OnCancelEmojiPoupListener listener;
    public void  setOnCancelEmojiLisnter(OnCancelEmojiPoupListener lisnter){
        this.listener = lisnter;
    }

    /**
     * 该方法会在软件盘消耗back键之前调用
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if(isEmojiPoupShown){
            //如何emoji弹窗还弹起，那么就只是关闭emoji
            if(listener != null){
                listener.onCancel();
             }
            return true;
        }
        //否则关闭软键盘或者退出
        return super.dispatchKeyEventPreIme(event);
    }
}
