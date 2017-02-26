package com.nexuslink.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.ui.activity.FriendInfoActivity;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.ImageUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public class MyFriendRecyclerViewAdapter extends RecyclerView.Adapter<MyFriendRecyclerViewAdapter.MyViewHolder>{
    private Context mContext;
    private List<String> nickName;
    private List<String> headImg;
    private List<Integer> userId;
    private List<Boolean[]> userAchieve;
    FollowedInfo followedInfo;

    public MyFriendRecyclerViewAdapter(Context mContext,FollowedInfo followedInfo) {
        this.mContext = mContext;
        this.followedInfo = followedInfo;
        init();
    }
    private void init(){
        nickName=new ArrayList<>();
        headImg=new ArrayList<>();
        userId = new ArrayList<>();
        for(int i=0;i<followedInfo.getUsers().size();i++){
            nickName.add(followedInfo.getUsers().get(i).getFName());
            headImg.add(followedInfo.getUsers().get(i).getFImg());
            userId.add(followedInfo.getUsers().get(i).getFId());
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_alluser_item,parent,false);
        MyViewHolder viewHodler = new MyViewHolder(view);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.btnFollow.setText("私信");
        XLog.e(Constants.PHOTO_BASE_URL+headImg.get(position));
        ImageUtil.imageDisplayHeadImage(Constants.PHOTO_BASE_URL+headImg.get(position),holder.imageHead);
        holder.tvName.setText(nickName.get(position));
        holder.imageHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent friendInfoIntent = new Intent(mContext, FriendInfoActivity.class);
                friendInfoIntent.putExtra("uImg",headImg.get(position));
                friendInfoIntent.putExtra("uName",nickName.get(position));
                friendInfoIntent.putExtra("uId",userId.get(position));
                if(friendInfoIntent.resolveActivity(mContext.getPackageManager())!=null){
                    mContext.startActivity(friendInfoIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return nickName.size();
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
