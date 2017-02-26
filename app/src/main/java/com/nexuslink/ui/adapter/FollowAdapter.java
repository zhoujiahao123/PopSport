package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.ui.fragment.FollowFragment;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.ImageUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ASUS-NB on 2017/2/24.
 */

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.MyViewHolder> {

    private Context mContext;
    private FollowedInfo mFollowedInfo;
    public FollowAdapter(Context context, FollowedInfo followedInfo) {
        mContext = context;
        EventBus.getDefault().register(this);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_follow,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageUtil.imageDisplayHeadImage(Constants.PHOTO_BASE_URL+mFollowedInfo.getUsers().get(position).getFImg(),holder.headImage);
        holder.nickName.setText(mFollowedInfo.getUsers().get(position).getFName());
    }

    @Override
    public int getItemCount() {
        return mFollowedInfo.getUsers().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView headImage;
        TextView nickName;
        public MyViewHolder(View itemView) {
            super(itemView);
            headImage = (CircleImageView) itemView.findViewById(R.id.head_image);
            nickName = (TextView) itemView.findViewById(R.id.nick_name);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onFollowedInfo(FollowedInfo followedInfo){
        mFollowedInfo = followedInfo;
        EventBus.getDefault().unregister(this);
    }
}
