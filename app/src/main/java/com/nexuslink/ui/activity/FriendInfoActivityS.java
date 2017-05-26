package com.nexuslink.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.example.zhouwei.library.CustomPopWindow;
import com.nexuslink.R;
import com.nexuslink.app.BaseActivity;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.EventEntry;
import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.model.friendinfosmodel.FriendInfoSModelImpl;
import com.nexuslink.presenter.friendinfospresenter.FriendInfoSPresenter;
import com.nexuslink.presenter.friendinfospresenter.FriendInfoSPresenterImpl;
import com.nexuslink.ui.view.FriendInfoSView;
import com.nexuslink.util.BlurDrawable;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.RatingUtil;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.loader.ILoader;
import com.nexuslink.util.loader.LoaderFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ASUS-NB on 2017/3/5.
 */

public class FriendInfoActivityS extends BaseActivity implements FriendInfoSView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.head_image)
    CircleImageView headImage;
    @BindView(R.id.nick_name)
    TextView nickName;
    @BindView(R.id.fan_num)
    TextView fanNum;
    @BindView(R.id.follow_num)
    TextView followNum;
    @BindView(R.id.gender_impl)
    TextView genderImpl;
    @BindView(R.id.rating_impl)
    TextView ratingImpl;
    @BindView(R.id.achieve_impl)
    TextView achieveImpl;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.background)
    RelativeLayout background;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.top)
    RelativeLayout top;
    private InputStream inputStream;
    //上一个activity传过来的数据
    private String uImg, uName;
    private final static int BACKGROUD = 1;
    private int uId;
    private byte[] data;
    //Glide的实例
    private ILoader loader = LoaderFactory.getGlideLoader();
    private OkHttpClient okHttpClient = new OkHttpClient();
    private FriendInfoSPresenter presenter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == BACKGROUD) {
                doBlur(data);
                XLog.e("这个data的大小为" + data.length);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        ButterKnife.bind(this);
        initToolbar(toolbar);
        if (!isNetworkActive()) {
            Intent intent = new Intent(this, NoNetActivity.class);
            startActivity(intent);
        }
        //获取上一个activity传来的数据
        uId = getIntent().getIntExtra("uId", -1);
        if (uId == -1) {
            ToastUtil.showToast(this, "哎呀,数据的获取出现了一些问题");
        }
        uImg = getIntent().getStringExtra("uImg");
        uName = getIntent().getStringExtra("uName");
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.add("更改背景");
        menuItem.setIcon(R.drawable.change_bg);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                int width = windowManager.getDefaultDisplay().getWidth();
                View view = LayoutInflater.from(FriendInfoActivityS.this).inflate(R.layout.item_pop, null);
                final CustomPopWindow window = new CustomPopWindow.PopupWindowBuilder(FriendInfoActivityS.this)
                        .setView(view)
                        .setFocusable(true)
                        .setOutsideTouchable(true)
                        .create()
                        .showAsDropDown(toolbar, width - 10, -30);
                RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.pop_window);
                rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(FriendInfoActivityS.this, AlterPhotoActivity.class));
                        window.dissmiss();
                    }
                });
                return true;
            }
        });
        return true;
    }

    private void doBlur(byte[] data) {
        final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        final BlurDrawable blurDrawable = new BlurDrawable(this, getResources(), bitmap);
        blurDrawable.setBlur(100);
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(blurDrawable.getBlurDrawable());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            background.setBackground(imageView.getDrawable());
            background.getBackground().setAlpha(100);
        }
    }

    private void getImageStream() {
        Request request = new Request.Builder().url(Constants.PHOTO_BASE_URL + uImg).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    inputStream = response.body().byteStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                    outputStream.close();
                    inputStream.close();
                    data = outputStream.toByteArray();
                    Message message = new Message();
                    message.what = BACKGROUD;
                    handler.sendMessage(message);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        getImageStream();
        presenter = new FriendInfoSPresenterImpl(new FriendInfoSModelImpl(), this);
        if (uId != -1) {
            getUserInfo(uId);
            getUserFollow(uId);
        }
    }

    @Override
    public void getUserInfo(int uId) {
        presenter.getUserInfo(uId);
    }

    @Override
    public void showUserInfo(UserInfo userInfo) {
        loader.loadNet(headImage, Constants.PHOTO_BASE_URL + userInfo.getUser().getUImg(), null);
        nickName.setText(userInfo.getUser().getUName());
        XLog.e(userInfo.getUser().getUName());
        fanNum.setText(userInfo.getUser().getUFansnum() + "");
        followNum.setText(50 + "");
        genderImpl.setText(userInfo.getUser().getUGender().equals("M") ? "男" : "女");
        XLog.e(userInfo.getUser().getUGender());
        achieveImpl.setText(checkAchieve((Boolean[]) userInfo.getUser().getUAchievements().toArray()));
        ratingImpl.setText(RatingUtil.getRating(userInfo.getUser().getUExp()));
        XLog.e(RatingUtil.getRating(userInfo.getUser().getUExp()));
    }

    /**
     * 成就判断
     */
    private String checkAchieve(Boolean[] achieve) {
        int max = -1;
        for (int i = 0; i < 8; i++) {
            if (achieve[i])
                max = i;
        }
        if (max != -1) {
            switch (max) {
                case 0:
                    return "突破3公里";
                case 1:
                    return "突破5公里";
                case 2:
                    return "突破10公里";
                case 3:
                    return "跑步小能手";
                case 4:
                    return "突破半马";
                case 5:
                    return "突破全马";
                case 6:
                    return "我爱人人";
                case 7:
                    return "人人爱我";
                default:
                    try {
                        throw new Exception("没有匹配的成就");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        return "暂无";
    }

    @Override
    public void getUserFailed() {
        ToastUtil.showToast(this, "哎呀,获取信息的时候出现了错误");
    }

    @Override
    public void getUserFollow(int uId) {
        presenter.getFollowedInfo(uId);
    }

    @Override
    public void showUserFollowed(FollowedInfo followedInfo) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FriendInfoAdapter mAdapter = new FriendInfoAdapter(this);
        for (int i = 0; i < followedInfo.getUsers().size(); i++) {
            mAdapter.addData(followedInfo.getUsers().get(i).getFId(), followedInfo.getUsers().get(i).getFImg(), followedInfo.getUsers().get(i).getFName());
        }
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(mAdapter);
    }

    @Override
    public void getFollowedFailed() {
        ToastUtil.showToast(this, "哎呀,获取信息的时候出现了错误");
    }

    class FriendInfoAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        private Context mContext;
        private List<String> uImgData = new ArrayList<>();
        private List<String> uNameData = new ArrayList<>();
        private List<Integer> uIdData = new ArrayList<>();

        public FriendInfoAdapter(Context context) {
            mContext = context;
        }

        public void addData(int uId, String uImg, String uName) {
            uIdData.add(uId);
            uImgData.add(uImg);
            uNameData.add(uName);
            notifyDataSetChanged();
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_friend_info, parent, false);
            ItemViewHolder holder = new ItemViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            XLog.e(uImgData.get(position));
            loader.loadNet(holder.imageView, Constants.PHOTO_BASE_URL + uImgData.get(position), null);
        }


        @Override
        public int getItemCount() {
            return uIdData.size();
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.head_image);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void change_bg(EventEntry eventEntry) {
        String photoPath = eventEntry.photos.get(0).getPath();
        eventEntry.photos.clear();
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
        Drawable drawable = new BitmapDrawable(bitmap);
        top.setBackground(drawable);
        XLog.e("切换背景的操作执行了");
    }
}
