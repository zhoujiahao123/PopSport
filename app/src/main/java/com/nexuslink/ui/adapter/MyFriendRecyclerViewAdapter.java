package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.ImageUtil;

import java.util.List;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public class MyFriendRecyclerViewAdapter extends RecyclerView.Adapter<MyFriendRecyclerViewAdapter.MyViewHolder>{
    private Context mContext;
    private List<String> nickName;
    private List<String> headImg;
    private List<Integer> userId;
    private List<int[]> userAchieve;

    public MyFriendRecyclerViewAdapter(Context mContext, FollowedInfo followedInfo) {
        this.mContext = mContext;
        initData(followedInfo);
    }


    private void initData(FollowedInfo followedInfo){

        for(int i=0;i<followedInfo.getSimpleUserList().size();i++){
            nickName.add(followedInfo.getSimpleUserList().get(i).getfName());
            headImg.add(followedInfo.getSimpleUserList().get(i).getfImg());
            userId.add(followedInfo.getSimpleUserList().get(i).getfId());
            userAchieve.add(followedInfo.getSimpleUserList().get(i).getfAchievements());
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_alluser_item,parent,false);
        MyViewHolder viewHodler = new MyViewHolder(view);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.btnFollow.setText("私信");
        ImageUtil.imageDisplay(headImg.get(position),holder.imageHead);
        holder.tvName.setText(nickName.get(position));
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageHead;
        TextView tvName;
        Button btnFollow;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageHead = (CircleImageView) itemView.findViewById(R.id.image_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            btnFollow = (Button) itemView.findViewById(R.id.btn_follow);
        }
    }
}
