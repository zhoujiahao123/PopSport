package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.FansInfo;
import com.nexuslink.ui.fragment.FansFragment;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.ImageUtil;

/**
 * Created by ASUS-NB on 2017/2/25.
 */

public class FansAdapter extends RecyclerView.Adapter<FansAdapter.MyViewHolder>{
    private Context mContext;
    FansInfo fansInfo;

    public FansAdapter(Context mContext,FansInfo fansInfo) {
        this.mContext = mContext;
        this.fansInfo = fansInfo;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fans,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageUtil.imageDisplayHeadImage(Constants.PHOTO_BASE_URL+fansInfo.getFans().get(position).getFImg(),holder.headImage);
        holder.nickName.setText(fansInfo.getFans().get(position).getFName());
    }


    @Override
    public int getItemCount() {
        return fansInfo.getFans().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nickName;
        CircleImageView headImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            nickName = (TextView) itemView.findViewById(R.id.nick_name);
            headImage = (CircleImageView) itemView.findViewById(R.id.head_image);
        }
    }

}
