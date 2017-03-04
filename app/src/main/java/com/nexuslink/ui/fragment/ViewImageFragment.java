package com.nexuslink.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.ui.view.view.headerview.LoadingView;
import com.nexuslink.util.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.toolsfinal.FileUtils;

/**
 * Created by 猿人 on 2017/2/10.
 */

public class ViewImageFragment extends Fragment {
    @BindView(R.id.progress)
    LoadingView progress;
    private String url;
    //===============================================常量
    private static final String TAG = "ViewImageShowActivity";
    private static final int SUCCESS = 1;
    private static final int FAILED = 0;
    //===============================================view
    private ImageView imageView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    ToastUtil.showToast(getContext(),"保存成功");
                    break;
                case FAILED:
                    ToastUtil.showToast(getContext(),"保存失败");
                    break;
                default:
                    break;
            }
        }
    };

    //===============================================点击事件接口
    public interface onPicClickListener{
        void onPicClick();
    }
    private onPicClickListener listener;
    public void setOnPickClickListener(onPicClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(Constants.IMAGE_URL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_show_image, container, false);
        ButterKnife.bind(this, view);
        imageView = (ImageView) view.findViewById(R.id.image_show);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onPicClick();
                }
            }
        });
        //显示I加载
        progress.setVisibility(View.VISIBLE);
        if (url != null) {
            Glide.with(getContext()).load(url).crossFade().thumbnail(0.2f).listener(requestListener).into(imageView);
        }
        return view;
    }
    //图片加载监听
    RequestListener<String, GlideDrawable> requestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            e.printStackTrace();
            progress.setVisibility(View.GONE);
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            progress.setVisibility(View.GONE);
            return false;
        }
    };

    public void saveImage(){
        if(url != null){
            new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 首先保存图片
                    File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
                    //小米手机必须这样获得public绝对路径
                    String filesName = "Pop图片";
                    File appDir = new File(file, filesName);
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }
                    String fileName = System.currentTimeMillis() + ".jpg";
                    File currentFile = new File(appDir, fileName);

                    File picCacheFile = Glide.with(getContext()).load(url).downloadOnly(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).get();
                    FileUtils.copyFile(picCacheFile.getPath(),currentFile.getPath());
                    //保存成功
                    handler.sendEmptyMessage(SUCCESS);
                    //其次把文件插入到系统图库
                    try {
                        MediaStore.Images.Media.insertImage(getContext().getContentResolver(),
                                currentFile.getAbsolutePath(), fileName, null);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    // 最后通知图库更新
                    getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(new File(currentFile.getPath()))));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(FAILED);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(FAILED);
                }
            }
         }).start();
        }else{
            ToastUtil.showToast(getContext(),"图片地址出错");
        }

}


}
