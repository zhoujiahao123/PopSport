package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexuslink.R;
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.util.CircleImageView;

import java.util.List;

import cn.lemon.multi.MultiView;

/**
 * Created by 猿人 on 2017/2/8.
 */

public class CommunityRecyclerAdapter extends RecyclerView.Adapter<CommunityRecyclerAdapter.CommunityViewHolder> {
    //===============================================常量
    private static final String TAG = "CommunityRecyclerAdapter";
    //===============================================数据
    private List<CommunityInfo.CommunityBean> data ;
    private Context mContext;
    private LayoutInflater inflater;
    public CommunityRecyclerAdapter(Context context,List<CommunityInfo.CommunityBean> data) {
        this.data = data;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.community_recyler_item,parent,false);
        return new CommunityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommunityViewHolder holder, int position) {
        CommunityInfo.CommunityBean communityBean = data.get(position);
        //加载话题头像
        Glide.with(mContext).load(communityBean.getUserImageUrl()).into(holder.userImage);
        //用户名字和level
        holder.userName.setText(communityBean.getUserName());
        holder.userLevel.setText("Lv."+communityBean.getUserLevel());
        //话题信息
        holder.mContent.setText(communityBean.getContent());
        holder.imagesContent.setImages(communityBean.getContentImagsUrl());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CommunityViewHolder extends RecyclerView.ViewHolder{
        CircleImageView userImage;
        TextView userName,userLevel,mContent;
        MultiView imagesContent;
        public CommunityViewHolder(View itemView) {
            super(itemView);
            userImage = (CircleImageView) itemView.findViewById(R.id.user_image);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            userLevel = (TextView) itemView.findViewById(R.id.user_level);
            mContent = (TextView) itemView.findViewById(R.id.tv_content);
            imagesContent = (MultiView) itemView.findViewById(R.id.multi_view);
        }
    }
}
