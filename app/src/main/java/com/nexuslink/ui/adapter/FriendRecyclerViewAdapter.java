package com.nexuslink.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elvishew.xlog.XLog;
import com.nexuslink.R;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.model.data.SearchAndFollowedInfo;
import com.nexuslink.model.data.SearchInfo;
import com.nexuslink.model.friendmodel.OnRegisterListener;
import com.nexuslink.ui.activity.FriendActivity;
import com.nexuslink.ui.activity.FriendInfoActivity;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.IdUtil;
import com.nexuslink.util.ToastUtil;
import com.ufreedom.floatingview.Floating;
import com.ufreedom.floatingview.FloatingBuilder;
import com.ufreedom.floatingview.FloatingElement;
import com.ufreedom.floatingview.effect.TranslateFloatingTransition;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ASUS-NB on 2017/1/16.
 */

public class FriendRecyclerViewAdapter extends RecyclerView.Adapter<FriendRecyclerViewAdapter.MyViewHodler> implements OnRegisterListener{
    private Context mContext;
    int mFollowedNum =-1;
    private String mFollowed[]=new String[20];
    private String nickName[]={"周家豪0","周家豪1","周家豪2","周家豪3","周家豪4","周家豪5","周家豪6","周家豪7","周家豪8","周家豪9","周家豪10",
            "周家豪11","周家豪12","周家豪13","周家豪14","周家豪15","周家豪16","周家豪17","周家豪18","周家豪19"};
    private SearchInfo mSearchInfo;
    int userNum=0;
    int lastUserNum=0;
    int position = 0;
    int times=0;
    private FollowedInfo followedInfo;

    @Override
    public void onFollowSucceed(int position) {
        XLog.e("开始删除"+position);
        notifyItemRemoved(position);
        userNum--;
        times++;
    }

    @Override
    public void onRegister() {
        EventBus.getDefault().register(this);
        XLog.e("已经订阅");
    }

    public interface CallbackListener{
        void onItemClicked(int uId,int fId,int pos);
    }
    private static CallbackListener mListener;
    public static void setCallbackListener(CallbackListener listener){
        mListener  = listener;
    }
    public FriendRecyclerViewAdapter(Context mContext, SearchInfo searchInfo) {
        this.mContext = mContext;
        FriendActivity.setOnRegisterListener(this);
        XLog.e("我测试的FriendRecyclerViewAdapter.java的文件的 构造方法执行了");
    }


    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_alluser_item,parent,false);
        MyViewHodler viewHodler = new MyViewHodler(view);
        XLog.e("我测试的FriendRecyclerViewAdapter.java的文件的 onCreateViewHolder执行了");
        return viewHodler;
    }

    @Override
    public int getItemCount() {
        XLog.e("我测试的FriendRecyclerViewAdapter.java的文件的 getItemCount执行了");
        return mSearchInfo==null?0:userNum;
    }

    @Override
    public void onBindViewHolder(MyViewHodler holder, int position) {
        XLog.e("我测试的FriendRecyclerViewAdapter.java的文件的 onBindViewHolder");
        XLog.e(mSearchInfo.getUsers().size());
        XLog.e(position);
        holder.tvName.setText(mSearchInfo.getUsers().get(position).getFName());
        Glide.with(mContext)
                .load(Constants.PHOTO_BASE_URL+mSearchInfo.getUsers().get(position).getFImg())
                .into(holder.imageHead);
        XLog.e(Constants.BASE_URL+mSearchInfo.getUsers().get(position).getFImg());
        holder.btnFollow.setText("关注");
        holder.btnFollow.setClickable(true);
        holder.btnFollow.setBackgroundResource(R.drawable.selector_button_follow);
         for(int j=0;j<=mFollowedNum;j++){
             if(holder.tvName.getText()==mFollowed[j]){
                 holder.btnFollow.setBackgroundResource(R.drawable.selector_button_followed);
                 holder.btnFollow.setText("已关注");
                 holder.btnFollow.setClickable(false);
             }
         }
    }

    public class MyViewHodler extends RecyclerView.ViewHolder{
        CircleImageView imageHead;
        TextView tvName;
        Button btnFollow;
        public MyViewHodler(View itemView) {
            super(itemView);
            imageHead = (CircleImageView) itemView.findViewById(R.id.image_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            btnFollow = (Button) itemView.findViewById(R.id.btn_follow);
            imageHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent friendInfoIntent = new Intent(mContext, FriendInfoActivity.class);
                    friendInfoIntent.putExtra("uImg",mSearchInfo.getUsers().get(getPosition()+times).getFImg());
                    friendInfoIntent.putExtra("uName",mSearchInfo.getUsers().get(getPosition()+times).getFName());
                    if(friendInfoIntent.resolveActivity(mContext.getPackageManager())!=null){
                        mContext.startActivity(friendInfoIntent);
                    }
                }
            });
            btnFollow.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    for(int j=0;j<=mFollowedNum;j++){
                        if(tvName.getText()==mFollowed[j]){
                            return;
                        }
                    }
                    mFollowedNum++;
                    mFollowed[mFollowedNum]=tvName.getText().toString();
                    ImageView imageView = new ImageView(mContext);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    imageView.setImageResource(R.drawable.followimage);
                    FloatingElement element = new FloatingBuilder()
                            .anchorView(btnFollow)
                            .targetView(imageView)
                            .offsetX(0)
                            .offsetY(-40)
                            .floatingTransition(new TranslateFloatingTransition())
                            .build();
                    Floating floating = new Floating(FriendActivity.getFriendActivity());
                    floating.startFloating(element);
                    btnFollow.setBackgroundResource(R.drawable.selector_button_followed);
                    btnFollow.setText("已关注");
                    btnFollow.setClickable(false);
                    mListener.onItemClicked((int) IdUtil.getuId(),mSearchInfo.getUsers().get(getPosition()+times).getFId(),getPosition());
                    }
            });
        }
    }
    @Subscribe (threadMode =ThreadMode.MAIN,sticky = true)
    public void receiveSearchInfo(SearchAndFollowedInfo info){
        XLog.e("订阅的事件执行了");
        times=0;
        if(mSearchInfo!=null){
            notifyItemRangeRemoved(0,mSearchInfo.getUsers().size());
        }
        mSearchInfo = info.getSearchInfo();
        followedInfo = info.getFollowedInfo();
        EventBus.getDefault().unregister(this);
        for(int i=0;i<mSearchInfo.getUsers().size();i++){
            for(int j=0;j<followedInfo.getUsers().size();j++){
                XLog.e("这里的i为"+i);
                if(mSearchInfo.getUsers().get(i).getFId()==followedInfo.getUsers().get(j).getFId()||mSearchInfo.getUsers().get(i).getFId()==IdUtil.getuId()){
                    XLog.e("这里执行了一次哦");
                    mSearchInfo.getUsers().remove(i);
                    ToastUtil.showToast(BaseApplication.getContext(),"您已经关注了该用户");
                    i--;
                    break;
                }
            }
        }
//            for(int j=0;j<searchInfo.getUsers().size();j++){
//                XLog.e("大循环中");
//                XLog.e("这里的size是"+searchInfo.getUsers().size());
//                if(position==0){
//                    isDisplayUserId[position++]=searchInfo.getUsers().get(j).getFId();
//                    XLog.e("pos为0的时候");
//                    XLog.e("这个j是"+j);
//                    XLog.e("这个id是"+searchInfo.getUsers().get(j).getFId());
//                    XLog.e("这个村粗id是"+isDisplayUserId[position-1]);
//                }else {
//                    int num=position;
//                    for (int i=0;i<num;i++){
//                        if(isDisplayUserId[i]==searchInfo.getUsers().get(j).getFId()){
//                            XLog.e("这个j是"+j);
//                            XLog.e("这个id是"+searchInfo.getUsers().get(j).getFId());
//                            XLog.e("已经被移除"+isDisplayUserId[i]);
//                            searchInfo.getUsers().remove(j);
//                        }else {
//                            isDisplayUserId[position++]=searchInfo.getUsers().get(j).getFId();
//                            XLog.e("目前数组里面的内容是"+isDisplayUserId[i]);
//                    }
//                    }
//                }
//            }
//        XLog.e("这个的大小为"+searchInfo.getUsers().size());
//        lastUserNum = userNum;
//        XLog.e("userNum的大小为"+userNum);
//        if(searchInfo.getUsers().size()!=0){
//            notifyItemInserted(0);
//            XLog.e("这里也执行了");
//        }
        userNum = mSearchInfo.getUsers().size();
        notifyItemInserted(0);
    }
}
