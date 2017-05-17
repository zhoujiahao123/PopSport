package com.nexuslink.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.search.RandomSearchResult;
import com.nexuslink.ui.activity.OtherPersonActivity;
import com.nexuslink.util.CircleImageView;

/**
 * Created by 猿人 on 2017/4/28.
 */

public class RandomFriendsAdapter extends RecyclerArrayAdapter<RandomSearchResult.UsersBean> {

    private Context mContext;

    public RandomFriendsAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RandomViewHolder(parent, R.layout.search_result_item);
    }

    class RandomViewHolder extends BaseViewHolder<RandomSearchResult.UsersBean> {

        CircleImageView userImage;
        TextView userName;
        TextView stepsTv, milesTv, fansTv;

        public RandomViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            userImage = $(R.id.image);
            userName = $(R.id.name);
            stepsTv = $(R.id.steps);
            milesTv = $(R.id.miles);
            fansTv = $(R.id.fans_num);
        }

        @Override
        public void setData(final RandomSearchResult.UsersBean data) {
            super.setData(data);
            userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,OtherPersonActivity.class);
                    intent.putExtra("uId",data.getUid());
                    mContext.startActivity(intent);
                }
            });
            Glide.with(mContext).load(Constants.PHOTO_BASE_URL + data.getUImg()).crossFade().into(userImage);
            userName.setText(data.getUName());
            stepsTv.setText(data.getUHistoryStep() + "");
            milesTv.setText(data.getUHistoryMileage() + "");
            fansTv.setText(data.getUFansnum() + "");
            userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OtherPersonActivity.class);
                    intent.putExtra("uId", data.getUid());
                    mContext.startActivity(intent);
                }
            });

        }
    }
}
