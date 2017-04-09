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
import com.nexuslink.model.data.FansInfo;
import com.nexuslink.util.CircleImageView;

/**
 * Created by 猿人 on 2017/4/8.
 */

public class FansRecyclerAdapter extends RecyclerArrayAdapter<FansInfo.FansBean> {
    public FansRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FansRecyclerViewHolder(parent, R.layout.person_friend_item);
    }

    class FansRecyclerViewHolder extends BaseViewHolder<FansInfo.FansBean> {
        CircleImageView userImageTv;
        TextView userNameTv;


        public FansRecyclerViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            userImageTv = $(R.id.user_image);
            userNameTv = $(R.id.user_name);

        }

        @Override
        public void setData(FansInfo.FansBean data) {
            super.setData(data);
            Glide.with(getContext()).load(Constants.PHOTO_BASE_URL + data.getFImg()).crossFade().thumbnail(0.3f).into(userImageTv);
            userNameTv.setText(data.getFName());
        }


    }
}
