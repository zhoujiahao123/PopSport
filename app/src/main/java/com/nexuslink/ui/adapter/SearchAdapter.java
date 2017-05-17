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
import com.nexuslink.model.data.FollowInfo;
import com.nexuslink.model.data.FriendInfo;
import com.nexuslink.model.data.SearchInfo;
import com.nexuslink.ui.activity.OtherPersonActivity;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.UserUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 猿人 on 2017/4/28.
 */

public class SearchAdapter extends RecyclerArrayAdapter<SearchInfo.UsersBean> {

    private Context mContext;

    public SearchAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchViewHolder(parent, R.layout.search_result_item);
    }

    class SearchViewHolder extends BaseViewHolder<SearchInfo.UsersBean> {


        CircleImageView userImage;
        TextView userName;
        TextView stepsTv, milesTv, fansTv, follow;

        public SearchViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            userImage = $(R.id.image);
            userName = $(R.id.name);
            stepsTv = $(R.id.steps);
            milesTv = $(R.id.miles);
            fansTv = $(R.id.fans_num);
            follow = $(R.id.follow);
        }

        @Override
        public void setData(final SearchInfo.UsersBean data) {
            super.setData(data);
            ApiUtil.getInstance(Constants.BASE_URL).getFriendInfo(UserUtils.getUserId(), data.getFId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<FriendInfo>() {
                        @Override
                        public void call(final FriendInfo friendInfo) {
                            if (friendInfo.getCode() == Constants.SUCCESS) {
                                Glide.with(mContext).load(Constants.PHOTO_BASE_URL + friendInfo.getFriend().getUImg()).crossFade().into(userImage);
                                userName.setText(friendInfo.getFriend().getUName());
                                stepsTv.setText(friendInfo.getFriend().getUHistoryStep() + "");
                                milesTv.setText(friendInfo.getFriend().getUHistoryMileage() + "");
                                fansTv.setText(friendInfo.getFriend().getUFansnum() + "");
                                userImage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(mContext, OtherPersonActivity.class);
                                        intent.putExtra("uId", friendInfo.getFriend().getUid());
                                        mContext.startActivity(intent);
                                    }
                                });
                                //设置关注
                                if (friendInfo.isIsFollowed()) {
                                    follow.setText("已关注");
                                    follow.setBackgroundColor(mContext.getColor(R.color.gray));
                                } else {
                                    follow.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ApiUtil.getInstance(Constants.BASE_URL).getFollowInfo(UserUtils.getUserId(),data.getFId())
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Action1<FollowInfo>() {
                                                        @Override
                                                        public void call(FollowInfo followInfo) {
                                                            follow.setText("已关注");
                                                            follow.setBackgroundColor(mContext.getColor(R.color.gray));
                                                        }
                                                    });
                                        }
                                    });
                                }
                            }
                        }
                    });
        }
    }
}
