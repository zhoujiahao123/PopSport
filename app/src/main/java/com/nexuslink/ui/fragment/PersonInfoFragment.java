package com.nexuslink.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.ui.BaseFragment;
import com.nexuslink.ui.adapter.PersonInfoViewPagerAdapter;
import com.nexuslink.ui.view.PersonInfoBezierView;
import com.nexuslink.util.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ASUS-NB on 2017/1/14.
 */

public class PersonInfoFragment extends BaseFragment {

    @BindView(R.id.user_image)
    CircleImageView userImage;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_level)
    TextView userLevel;
    @BindView(R.id.friends_num)
    TextView friendsNum;
    @BindView(R.id.funs_num)
    TextView funsNum;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.personinfo_bezier)
    PersonInfoBezierView mBezier;

    /**
     * 数据
     */
    List<Fragment> fragments = new ArrayList<>();


    @Override
    public int getLayout() {
        return R.layout.personinfo_fragment;
    }

    @Override
    public void initView() {
        //初始化fragment
        Fragment achievementFragment = new AchievementFragment();
        Fragment personArticleFragment = new PersonArticleFragment();
        Fragment myRoomsFragment = new MyRoomFragment();
        Fragment fansFragment = new FansFragment();
        Fragment friendFragment = new FriendFragment();


        fragments.add(achievementFragment);
        fragments.add(personArticleFragment);
        fragments.add(myRoomsFragment);
        fragments.add(fansFragment);
        fragments.add(friendFragment);

        //舒适化viewpager
        PersonInfoViewPagerAdapter adapter = new PersonInfoViewPagerAdapter(getFragmentManager());
        adapter.setFragments(fragments);
        mViewPager.setAdapter(adapter);
    }

}
