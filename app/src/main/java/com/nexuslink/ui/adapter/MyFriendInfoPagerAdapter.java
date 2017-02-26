package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.nexuslink.R;
import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.model.data.FriendInfo;
import com.nexuslink.model.myfriendmodel.OnNumberChangedLisntener;
import com.nexuslink.ui.activity.FriendInfoActivity;
import com.nexuslink.ui.fragment.ArticalFragment;
import com.nexuslink.ui.fragment.FansFragment;
import com.nexuslink.ui.fragment.FollowFragment;
import com.nexuslink.ui.fragment.MyFriendFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by ASUS-NB on 2017/2/24.
 */

public class MyFriendInfoPagerAdapter extends FragmentPagerAdapter implements OnNumberChangedLisntener{
    private Context mContext;
    private String mType[] = {"关注","粉丝","动态"};
    private int fansNum,followNum,postNum;
    TextView textNum;
    TextView textType;
    FollowedInfo mFollowedInfo;
    int uId;
    public MyFriendInfoPagerAdapter(FragmentManager fm, Context context, FollowedInfo followedInfo,int uId) {
        super(fm);
        mContext = context;
        mFollowedInfo = followedInfo;
        EventBus.getDefault().post(mFollowedInfo);
        onFollowNumberChanged(followedInfo.getUsers().size());
        FriendInfoActivity.setOnNumberChangedListener(this);
        this.uId = uId;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==1){
            return FansFragment.newInstance();
        }else if(position==0){
            return FollowFragment.getInstance();
        }else if(position==2){
            return ArticalFragment.newInstance(uId);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mType.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
    public View getTabView(int position){
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_friend_tab,null);
         textType = (TextView) view.findViewById(R.id.type);
        textType.setText(mType[position]);
        return view;
    }

    @Override
    public void onFansNumberChanged(int fansNum) {
        this.fansNum = fansNum;
    }

    @Override
    public void onFollowNumberChanged(int followNum) {
        this.followNum = followNum;
    }

    @Override
    public void onNumberChanged(int postNum) {
        this.postNum = postNum;
    }
}
