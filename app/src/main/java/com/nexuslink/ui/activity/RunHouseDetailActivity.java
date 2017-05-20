package com.nexuslink.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nexuslink.HasJoinedRooms;
import com.nexuslink.HasJoinedRoomsDao;
import com.nexuslink.R;
import com.nexuslink.model.data.RoomsBean;
import com.nexuslink.model.data.SetUpAlarm;
import com.nexuslink.presenter.runhousepresenter.RunHouseDetailPresenter;
import com.nexuslink.presenter.runhousepresenter.RunHouseDetailPresenterImpl;
import com.nexuslink.ui.adapter.RunHouseDetailAdapter;
import com.nexuslink.ui.view.RunHouseDetailView;
import com.nexuslink.ui.view.view.headerview.LoadingView;
import com.nexuslink.util.DBUtil;
import com.nexuslink.util.TimeUtils;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.UserUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nexuslink.broadcast.AlarmReceiver.COME_FROM_RUNHOUSE;
import static com.nexuslink.broadcast.AlarmReceiver.COME_FROM_RUNHOUSE_VALUE;


public class RunHouseDetailActivity extends AppCompatActivity implements RunHouseDetailView {


    //===============================================view
    @BindView(R.id.toolbar_run_house_detail)
    Toolbar mToolbar;
    @BindView(R.id.tv_run_house_expect)
    TextView mRunHouseExpectTv;
    @BindView(R.id.tv_run_house_start_time)
    TextView mRunHouseStartTimeTv;
    @BindView(R.id.bt_join_in)
    Button mJoinInBt;
    @BindView(R.id.run_house_name)
    TextView runHouseName;
    @BindView(R.id.run_house_personnum)
    TextView mPersonNum;
    @BindView(R.id.recycler_run_house_detail)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_community)
    LoadingView progress;
    @BindView(R.id.activity_run_house_detail)
    LinearLayout activityRunHouseDetail;
    //===============================================变量
    private RoomsBean roomBean;
    private RunHouseDetailAdapter adapter;
    /**
     * presenter
     */
    private RunHouseDetailPresenter presenter;
    /**
     * 辅助变量
     */
    private boolean isDataChanged = false;

    //===============================================常量
    private static final String TAG = "RunHouseDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_house_detail);
        ButterKnife.bind(this);
        presenter = new RunHouseDetailPresenterImpl(this);
        initDatas();
        initViews();
    }


    private void initDatas() {
        roomBean = getIntent().getParcelableExtra("roominfo");
        if (roomBean == null) {
            ToastUtil.showToast(this, "出现未知错误，请重试");
            onBackPressed();
        }
    }

    private final int OVERLAY_PERMISSION_REQ_CODE = 1;
    @Override
    protected void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if(!Settings.canDrawOverlays(this)){
                ToastUtil.showToast(this,"检测到您还未授予悬浮窗口权限，请您授予");
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivityForResult(intent,OVERLAY_PERMISSION_REQ_CODE);
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if(Build.VERSION.SDK_INT>=23) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "权限授予失败，程序可能无法正确运行", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "权限授予成功！", Toast.LENGTH_SHORT).show();
                    //有悬浮窗权限开启服务绑定 绑定权限
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isDataChanged) {
            EventBus.getDefault().post("刷新跑房");
        }
    }

    private void initViews() {

        //设置toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //reyclerview设置
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        //进行界面的设置
        //toolbar上信息设置
        runHouseName.setText(roomBean.getRoomName());
        mPersonNum.setText(roomBean.getUsers().size() + "人");
        //跑房目标框设置
        String str = roomBean.getRoomType() == 1 ? "米" : "分钟";
        mRunHouseExpectTv.setText(roomBean.getRoomGoal() + str);
        //设置目标
        mRunHouseStartTimeTv.setText(roomBean.getStartTime());
        //检测是否已经到
        checkTimeAndShowRemind(roomBean.getStartTime());
        //users设置
        adapter = new RunHouseDetailAdapter(this, roomBean.getUsers());
        mRecyclerView.setAdapter(adapter);
        //bt设置
        List<RoomsBean.UsersBean> users = roomBean.getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUid() == UserUtils.getUserId()) {
                changToQuit();
                break;
            }
        }
    }

    private void checkTimeAndShowRemind(String timeStr) {
        //判断是否加入跑房
        List<HasJoinedRooms> rooms = DBUtil.getHasJoinedRoomsDap().queryBuilder().where(HasJoinedRoomsDao.Properties.RId.eq(roomBean.getRoomId())).build().list();
        if(rooms==null){
            return;
        }
        if(System.currentTimeMillis() > TimeUtils.DateToMills(timeStr)){
            AlertDialog dialog ;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提醒");
            builder.setMessage("您的跑房时间已到了，要进入跑步吗?");
            builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent startRun = new Intent(RunHouseDetailActivity.this, RunActivity.class);
                    startRun.putExtra(COME_FROM_RUNHOUSE, COME_FROM_RUNHOUSE_VALUE);
                    startRun.putExtra("type", roomBean.getRoomType());
                    startRun.putExtra("goal", roomBean.getRoomGoal());
                    startRun.putExtra("rId", roomBean.getRoomId());
                    startActivity(startRun);
                    finish();
                }
            });
            builder.setNegativeButton("算了吧", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog = builder.create();
            dialog.show();
        }
    }

    private void changToQuit() {
        mJoinInBt.setText("退出跑房");
        //0xE00D0F
        mJoinInBt.setBackground(getDrawable(R.drawable.bt_run_house_warn));
    }

    private void changToJoin() {
        mJoinInBt.setText("加入跑房");
        mJoinInBt.setBackground(getDrawable(R.drawable.bt_run_house_background));
    }

    @OnClick(R.id.bt_join_in)
    public void onClick() {
        if (mJoinInBt.getText().toString().equals("加入跑房")) {
            presenter.joinRoom(roomBean.getRoomId());


        } else if (mJoinInBt.getText().toString().equals("退出跑房")) {
            presenter.quitRoom(roomBean.getRoomId());
            EventBus.getDefault().post(new Integer(roomBean.getRoomId()));//取消闹钟提醒
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showSuccess() {
        ToastUtil.showToast(this, "加入成功");
    }

    @Override
    public void showError() {
        ToastUtil.showToast(this, "出错啦(╥╯^╰╥)");
    }

    @Override
    public void setDatas(List<RoomsBean.UsersBean> users) {
        adapter.setDatas(users);
        mPersonNum.setText(adapter.getItemCount() + "人");
        changToQuit();
        isDataChanged = true;
    }

    @Override
    public void removeItem(int userId) {
        adapter.remoteItem(userId);
        mPersonNum.setText(adapter.getItemCount() + "人");
        changToJoin();
        isDataChanged = true;
        if (adapter.getItemCount() == 0) {
            onBackPressed();
        }
    }

    @Override
    public void insertOneRoom() {
        final HasJoinedRoomsDao joinedRoomsDao = DBUtil.getHasJoinedRoomsDap();
        HasJoinedRooms room = new HasJoinedRooms(null, roomBean.getRoomId(), roomBean.getRoomName(), adapter.getItemCount(), roomBean.getStartTime(), roomBean.getRoomGoal(),
                roomBean.getRoomType());
        joinedRoomsDao.insert(room);
        //重新设置闹钟
        EventBus.getDefault().post(new SetUpAlarm());
    }

}
