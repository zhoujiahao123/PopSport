package com.nexuslink.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.util.SaveImageListener;

import java.util.concurrent.ExecutionException;

/**
 * Created by 猿人 on 2017/2/10.
 */

public class ViewImageFragment extends Fragment {
    private String url;
    private Bitmap bitmap;
    //===============================================常量
    private static final String TAG = "ViewImageShowActivity";
    private static final int SUCCESS = 1;
    private static final int FAILED = 0;
    //===============================================view
    private ImageView imageView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS:
                    imageView.setImageBitmap(bitmap);
                    Log.i(TAG,"加载成功");
                    if(imageListener != null){
                        imageListener.onLoadSuccess(bitmap);
                    }
                    break;
                case FAILED:
                    Log.i(TAG,"失败");
                    if(imageListener != null){
                        imageListener.onLoadFailed("加载异常");
                    }
            }
        }
    };

    //===============================================回调接口
    private SaveImageListener imageListener;
    public void setSaveImageListener(SaveImageListener listener){
        this.imageListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            url = getArguments().getString(Constants.IMAGE_URL);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_show_image,container,false);
        imageView = (ImageView) view.findViewById(R.id.image_show);
        if(url != null) {
            new Thread(new LoadImageThread()).start();
        }
        return view;
    }

    //加载图片线程
    class LoadImageThread extends Thread{
        @Override
        public void run() {
            try {
                bitmap = Glide.with(getContext()).load(url).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                if(bitmap != null){
                    //加载成功
                   handler.sendEmptyMessage(SUCCESS);
                }else{
                    //加载失败
                    handler.sendEmptyMessage(FAILED);
                }
            } catch (InterruptedException e) {
                //加载失败
                Log.i(TAG,"异常interrupt");
                e.printStackTrace();
                handler.sendEmptyMessage(FAILED);
            } catch (ExecutionException e) {
                Log.i(TAG,"异常Execution");
                e.printStackTrace();
                //加载失败
                handler.sendEmptyMessage(FAILED);
            }
        }
    }

}
