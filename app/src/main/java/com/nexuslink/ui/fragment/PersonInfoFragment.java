package com.nexuslink.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nexuslink.R;
import com.nexuslink.ui.BaseFragment;
import com.nexuslink.ui.adapter.PersonInfoViewPagerAdapter;
import com.nexuslink.ui.view.PersonInfoBezierView;
import com.nexuslink.util.CircleImageView;
import com.wuxiaolong.androidutils.library.DisplayMetricsUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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

    LinearLayout mSettingLinear;
    /**
     * 数据
     */
    List<Fragment> fragments = new ArrayList<>();
    @BindView(R.id.more)
    CircleImageView More;
    PopupWindow pw;

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
        //初始化popupwindow
        View view = LayoutInflater.from(getContext()).inflate(R.layout.more_setting_poup,null);
        mSettingLinear = (LinearLayout) view.findViewById(R.id.setting_linear);
        pw = new PopupWindow(view, DisplayMetricsUtil.dip2px(getContext(),42),DisplayMetricsUtil.dip2px(getContext(),3*48));
        pw.setOutsideTouchable(true);
        pw.setFocusable(true);

    }


    @OnClick(R.id.more)
    public void onViewClicked() {
        pw.update();
        pw.showAsDropDown(More,0,DisplayMetricsUtil.dip2px(getContext(),8));
        YoYo.with(Techniques.RubberBand).duration(300).playOn(mSettingLinear);
    }
}
