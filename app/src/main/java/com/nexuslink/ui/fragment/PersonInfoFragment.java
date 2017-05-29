package com.nexuslink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.FriendsInfo;
import com.nexuslink.model.data.Info;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.ui.activity.AlterActivity;
import com.nexuslink.ui.activity.FriendsAndFansActivity;
import com.nexuslink.ui.activity.RankActivity;
import com.nexuslink.ui.activity.SearchActivity;
import com.nexuslink.ui.activity.TaskActivity;
import com.nexuslink.ui.adapter.PersonInfoViewPagerAdapter;
import com.nexuslink.ui.view.PersonInfoBezierView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.SharedPrefsUtil;
import com.nexuslink.util.ToActivityUtil;
import com.nexuslink.util.UserUtils;
import com.wuxiaolong.androidutils.library.DisplayMetricsUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.nexuslink.util.SharedPrefsUtil.getValue;

/**
 * Created by ASUS-NB on 2017/1/14.
 */

public class PersonInfoFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.user_image)
    CircleImageView userImage;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_level)
    TextView userLevel;
    @BindView(R.id.friends_num)
    TextView friendsNum;
    @BindView(R.id.funs_num)
    TextView fansNum;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.personinfo_bezier)
    PersonInfoBezierView mBezier;

    private final String USER_IMAGE = "userImage";
    private final String USER_NAME = "userName";
    private final String USER_LEVEL = "userLevel";
    private final String FRIEND_NUM = "friendsNum";
    private final String FANS_NUM = "fansNum";
    private final String SEX = "sex";
    private final String SHARE_PRF_NAME = "userinfo";

    private final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build();
    private final Gson gson = new Gson();

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate-");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestory");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initView() {
        //初始化fragment
        Fragment achievementFragment = new AchievementFragment();
        Fragment personArticleFragment = new PersonArticleFragment();
        ((PersonArticleFragment) personArticleFragment).setuId(UserUtils.getUserId());
        Fragment myRoomsFragment = new MyRoomFragment();

        fragments.add(achievementFragment);
        fragments.add(myRoomsFragment);
        fragments.add(personArticleFragment);

        //舒适化viewpager
        PersonInfoViewPagerAdapter adapter = new PersonInfoViewPagerAdapter(getFragmentManager());
        adapter.setFragments(fragments);
        mViewPager.setOffscreenPageLimit(3);//改变最大上限
        mViewPager.setAdapter(adapter);
        //初始化popupwindow
        View view = LayoutInflater.from(getContext()).inflate(R.layout.more_setting_poup, null);
        view.findViewById(R.id.setting).setOnClickListener(this);
        view.findViewById(R.id.rank_list).setOnClickListener(this);
        view.findViewById(R.id.task).setOnClickListener(this);
        view.findViewById(R.id.search).setOnClickListener(this);
        mSettingLinear = (LinearLayout) view.findViewById(R.id.setting_linear);
        pw = new PopupWindow(view, DisplayMetricsUtil.dip2px(getContext(), 120), DisplayMetricsUtil.dip2px(getContext(), 135));
        pw.setOutsideTouchable(true);
        pw.setFocusable(true);

        fansNum.setOnClickListener(this);
        friendsNum.setOnClickListener(this);
        setUserInfo();


    }


    private void setUserInfo() {
        //初始化个人信息
        initUserData(null);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //请求用户信息
                    RequestBody body1 = new FormBody.Builder().add("uId", String.valueOf(UserUtils.getUserId()))
                            .build();
                    Log.i(TAG,UserUtils.getUserId()+"");
                    Request request1 = new Request.Builder()
                            .url(Constants.BASE_URL + "user/getInfo")
                            .post(body1)
                            .build();
                    Response response1 = client.newCall(request1).execute();
                    if(response1.isSuccessful()){
                        final UserInfo userInfo = gson.fromJson(response1.body().string(), UserInfo.class);
                        if (userInfo.getCode() == Constants.SUCCESS) {
                            SharedPrefsUtil.putValue(getContext(), SHARE_PRF_NAME, USER_IMAGE, userInfo.getUser().getUImg());
                            SharedPrefsUtil.putValue(getContext(), SHARE_PRF_NAME, USER_NAME, userInfo.getUser().getUName());
                            SharedPrefsUtil.putValue(getContext(), SHARE_PRF_NAME, USER_LEVEL, userInfo.getUser().getUExp());
                            SharedPrefsUtil.putValue(getContext(), SHARE_PRF_NAME, FANS_NUM, userInfo.getUser().getUFansnum());
                            SharedPrefsUtil.putValue(getContext(), SHARE_PRF_NAME, SEX, userInfo.getUser().getUGender());
                        }
                        Log.i(TAG,userInfo.getUser().toString());
                        //设置用户信息
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //设置信息
                                Glide.with(getContext()).load(Constants.PHOTO_BASE_URL + userInfo.getUser().getUImg())
                                        .into(userImage);
                                userName.setText(userInfo.getUser().getUName());
                                userLevel.setText(UserUtils.getUserLevel(userInfo.getUser().getUExp()));
                                fansNum.setText(userInfo.getUser().getUFansnum() + "");
                                sex.setText(userInfo.getUser().getUGender().equals("M") ? "男" : "女");
                            }
                        });
                        //请求用户好友数
                        RequestBody body2 = new FormBody.Builder()
                                .add("uId", String.valueOf(UserUtils.getUserId())).build();
                        Request request2 = new Request.Builder()
                                .url(Constants.BASE_URL + "friend/mine")
                                .post(body2)
                                .build();
                        Response response2 = client.newCall(request2).execute();
                        if(response2.isSuccessful()){
                            final FriendsInfo friendsInfo = gson.fromJson(response2.body().string(), FriendsInfo.class);
                            if (friendsInfo.getCode() == Constants.SUCCESS) {
                                SharedPrefsUtil.putValue(getContext(), SHARE_PRF_NAME, FRIEND_NUM, friendsInfo.getUsers().size());
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    friendsNum.setText(friendsInfo.getUsers().size() + "");
                                }
                            });
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initUserData(Info info) {
        {
            String image_url = Constants.PHOTO_BASE_URL + getValue(getContext(), SHARE_PRF_NAME, USER_IMAGE, null);
            String userNameStr = SharedPrefsUtil.getValue(getContext(), SHARE_PRF_NAME, USER_NAME, UserUtils.getUserName());
            String userLevelStr = UserUtils.getUserLevel(SharedPrefsUtil.getValue(getContext(), SHARE_PRF_NAME, USER_LEVEL, 0));
            String friendsNumStr = SharedPrefsUtil.getValue(getContext(), SHARE_PRF_NAME, FRIEND_NUM, 0) + "";
            String fansNumStr = SharedPrefsUtil.getValue(getContext(), SHARE_PRF_NAME, FANS_NUM, 0) + "";
            String sexStr = SharedPrefsUtil.getValue(getContext(), SHARE_PRF_NAME, SEX, "").equals("M") ? "男" : "女";
            if (image_url == null) {
                Glide.with(getContext()).load(R.drawable.small_pop_logo).into(userImage);
            } else {
                Glide.with(getContext()).load(image_url).diskCacheStrategy(DiskCacheStrategy.RESULT).skipMemoryCache(true).into(userImage);
            }
            userName.setText(userNameStr);
            userLevel.setText(userLevelStr);
            friendsNum.setText(friendsNumStr);
            fansNum.setText(fansNumStr);
            sex.setText(sexStr);
        }
    }


    @OnClick(R.id.more)
    public void onViewClicked() {
        pw.update();
        pw.showAsDropDown(More, -20, DisplayMetricsUtil.dip2px(getContext(), 10));
        YoYo.with(Techniques.RubberBand).duration(300).playOn(mSettingLinear);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pw != null) {
            pw = null;//释放引用
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting:
                ToActivityUtil.toNextActivity(getContext(), AlterActivity.class);
                break;
            case R.id.task:
                ToActivityUtil.toNextActivity(getContext(), TaskActivity.class);
                break;
            case R.id.rank_list:
                ToActivityUtil.toNextActivity(getContext(), RankActivity.class);
                break;
            case R.id.search:
                ToActivityUtil.toNextActivity(getContext(), SearchActivity.class);
                break;
            case R.id.funs_num:
            case R.id.friends_num:
                ToActivityUtil.toNextActivity(getContext(), FriendsAndFansActivity.class);
        }
        pw.dismiss();
    }
}
