package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.FriendsInfo;
import com.nexuslink.model.data.FollowInfo;
import com.nexuslink.model.data.FriendInfo;
import com.nexuslink.ui.fragment.PersonArticleFragment;
import com.nexuslink.ui.view.PersonInfoBezierView;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.UserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class OtherPersonActivity extends AppCompatActivity {

    @BindView(R.id.user_image)
    CircleImageView mUserImage;
    @BindView(R.id.user_name)
    TextView mName;
    @BindView(R.id.user_level)
    TextView mLevel;
    @BindView(R.id.friends_num)
    TextView mFriendNum;
    @BindView(R.id.funs_num)
    TextView mFansNum;
    @BindView(R.id.sex)
    TextView mSex;

    @BindView(R.id.article_frame)
    FrameLayout articleFrame;
    @BindView(R.id.follow_bt)
    ImageView followBt;
    @BindView(R.id.personinfo_bezier)
    PersonInfoBezierView personinfoBezier;

    private int uId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_person);
        ButterKnife.bind(this);


        uId = getIntent().getIntExtra("uId", -1);
        if (uId == UserUtils.getUserId()) {
            followBt.setVisibility(View.GONE);
        }
        //设置文章
        PersonArticleFragment articleFragment = new PersonArticleFragment();
        articleFragment.setuId(uId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.article_frame, articleFragment);
        transaction.commit();
        //设置个人
        setUserInfo();

    }

    private void setUserInfo() {
        if (uId == -1) {
            ToastUtil.showToast(this, "出错啦");
            finish();
            return;
        }
        ApiUtil.getInstance(Constants.BASE_URL).getFriendInfo(UserUtils.getUserId(), uId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FriendInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(OtherPersonActivity.this,e.getMessage());

                    }

                    @Override
                    public void onNext(FriendInfo friendInfo) {
                        if (friendInfo.getCode() == Constants.SUCCESS) {
                            //设置信息
                            if (friendInfo.getFriend().getUImg() != null) {
                                Glide.with(OtherPersonActivity.this).load(Constants.PHOTO_BASE_URL + friendInfo.getFriend().getUImg())
                                        .into(mUserImage);
                            } else {
                                ToastUtil.showToast(OtherPersonActivity.this, "当前用户未设置头像");
                            }
                            mName.setText(friendInfo.getFriend().getUName());
                            mLevel.setText(UserUtils.getUserLevel(friendInfo.getFriend().getUExp()));
                            mFansNum.setText(friendInfo.getFriend().getUFansnum() + "");
                            mSex.setText(friendInfo.getFriend().getUGender().equals("M") ? "男" : "女");
                            if (friendInfo.isIsFollowed()) {
                                followBt.setImageDrawable(getDrawable(R.drawable.concern));
                            }
                        }
                    }
                });
        //继续请求用户好友树
        ApiUtil.getInstance(Constants.BASE_URL).getFriends(UserUtils.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FriendsInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(OtherPersonActivity.this,e.getMessage());
                    }

                    @Override
                    public void onNext(FriendsInfo friendsInfo) {
                        if (friendsInfo.getCode() == Constants.SUCCESS) {
                            mFriendNum.setText(friendsInfo.getUsers().size() + "");
                        }
                    }
                });
    }

    private final int CONCERN = 1;

    @OnClick(R.id.follow_bt)
    public void onViewClicked() {
        ApiUtil.getInstance(Constants.BASE_URL).getFollowInfo(UserUtils.getUserId(), uId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FollowInfo>() {
                    @Override
                    public void call(FollowInfo followInfo) {
                        if (followInfo.getCode() == Constants.SUCCESS) {
                            if (followInfo.getFollowFlag() == CONCERN) {
                                followBt.setImageDrawable(getDrawable(R.drawable.concern));
                                mFansNum.setText(followInfo.getFFansNum() + "");
                            } else{
                                followBt.setImageDrawable(getDrawable(R.drawable.unconcern));
                                mFansNum.setText(followInfo.getFFansNum() + "");
                            }
                        }
                    }
                });
    }
}

