package com.nexuslink.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nexuslink.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 猿人 on 2017/2/24.
 */

public class PhotoChooseResultAdapter extends RecyclerView.Adapter<PhotoChooseResultAdapter.PhotoViewHolder> {
    /**
     * 初始化需要数据
     */
    private Context mContext;
    private LayoutInflater inflater;
    private List<String> paths = new ArrayList<>() ;
    private List<Bitmap> datas = new ArrayList<>();
    //常亮
    private static long MAX_SIZE = 1024*100;
    public PhotoChooseResultAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        //首位不进行设置
        datas.add(null);
    }



    /**
     * 外部读取数据的接口
     */
    public List<String> getFilesPath(){
        return paths;
    }
    /**
     *选择图片点击接口
     * @return
     */
    public interface onChoosePhotosListener{
        void onClick();
    }
    private onChoosePhotosListener listener;
    public void setOnChoosePhotosListener(onChoosePhotosListener onChoosePhotosListener){
        this.listener = onChoosePhotosListener;
    }
    /**
     * 增加item
     */
    public void addItem(Bitmap bitmap,String path){
        datas.add(bitmap);
        paths.add(path);
    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.photo_item,parent,false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        //设置点击与首位
        if(position == 0){
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.camera);
            holder.imageView.setImageBitmap(bitmap);
            if(listener != null){
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick();
                    }
                });
            }
        }else{
            //设置选择图片暂位图
            holder.imageView.setImageBitmap(datas.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public PhotoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.photo__image);
        }
    }


}
