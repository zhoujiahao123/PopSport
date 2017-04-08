package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.FriendInfo;
import com.nexuslink.util.CircleImageView;

/**
 * Created by 猿人 on 2017/4/8.
 */

public class FriendRecyclerAdapter extends RecyclerArrayAdapter<FriendInfo> {
    public FriendRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendRecyclerViewHolder(parent, R.layout.person_friend_item);
    }

    class FriendRecyclerViewHolder extends BaseViewHolder<FriendInfo> {
        CircleImageView userImageTv;
        TextView userNameTv;
        TextView userLevelTv;
        TextView stepsTv;
        TextView milesTv;
        TextView sexTv;
        TextView fansNumTv;

        public FriendRecyclerViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            userImageTv = $(R.id.user_image);
            userNameTv = $(R.id.user_name);
            userLevelTv = $(R.id.user_level);
            stepsTv = $(R.id.steps);
            milesTv = $(R.id.miles);
            fansNumTv = $(R.id.fans_num);
            sexTv = $(R.id.sex);
        }

        @Override
        public void setData(FriendInfo data) {
            super.setData(data);
            Glide.with(getContext()).load(Constants.PHOTO_BASE_URL + data.getfImg()).crossFade().thumbnail(0.3f).into(userImageTv);
            userNameTv.setText(data.getfName());
            userLevelTv.setText(data.getfExp());
            stepsTv.setText(data.getfHistoryStep() + "");
            milesTv.setText(data.getfHistoryMileage() + "");
            fansNumTv.setText(data.getfFansNum() + "");
            sexTv.setText(data.getfGender() == 'M' ? "男" : "女");
        }
    }
}
