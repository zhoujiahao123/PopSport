package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.RankInfo;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.IdUtil;
import com.nexuslink.util.loader.LoaderFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS-NB on 2017/4/9.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final  int HEAD = 0;
    private static final int Layout = 1;
    private Context context;
    List<RankInfo.UsersBean> mData = new ArrayList<>();
    int myPos;
    public void addData(RankInfo rankInfo){
        int myUid= (int) IdUtil.getuId();
        XLog.e("我的id是"+myUid);
        for(int i=0;i<rankInfo.getUsers().size();i++){
            mData.add(rankInfo.getUsers().get(i));
            if(myUid==rankInfo.getUsers().get(i).getUid()){
                myPos = i;
                XLog.e("我的位置是"+myPos);
            }
        }
        this.notifyDataSetChanged();
    }
    public MyAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return HEAD;
        }
        return Layout;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHodler myViewHodler;
        MyHeadViewHodler headViewHodler;
        if(viewType==HEAD){
            View view = LayoutInflater.from(context).inflate(R.layout.item_head,parent,false);
             headViewHodler = new MyHeadViewHodler(view);
            return headViewHodler;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
             myViewHodler= new MyViewHodler(view);
            return myViewHodler;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyHeadViewHodler){
            if(position==0){
                if(mData.get(myPos).getUImg()==null){
                    LoaderFactory.getGlideLoader().loadResource(((MyHeadViewHodler) holder).headImage,R.drawable.person_head,null);
                }else {
                    LoaderFactory.getGlideLoader().loadNet(((MyHeadViewHodler) holder).headImage, Constants.PHOTO_BASE_URL+mData.get(myPos).getUImg(),null);
                }
                ((MyHeadViewHodler) holder).nickName.setText(mData.get(myPos).getUName());
                int myPosPlusOne = myPos+1;
                ((MyHeadViewHodler) holder).rankNum.setText("第"+myPosPlusOne+"名");
                ((MyHeadViewHodler) holder).textNum.setText(mData.get(myPos).getUHistoryMileage()+"");
            }
        }else {
            if(position!=0){
                if(mData.get(position).getUImg()==null){
                    LoaderFactory.getGlideLoader().loadResource(((MyViewHodler) holder).headImage,R.drawable.person_head,null);
                }else {
                    LoaderFactory.getGlideLoader().loadNet(((MyViewHodler) holder).headImage, Constants.PHOTO_BASE_URL+mData.get(position).getUImg(),null);
                }
                ((MyViewHodler) holder).nickName.setText(mData.get(position+2).getUName());
                ((MyViewHodler) holder).rankNum.setText(position+3+"");
                ((MyViewHodler) holder).textNum.setText(mData.get(position+2).getUHistoryMileage()+"");
            }
        }
    }

    @Override
    public int getItemCount() {
        if(mData==null){
            return 0;
        }
        return mData.size()-2;
    }

    class MyViewHodler extends RecyclerView.ViewHolder{
        TextView rankNum;
        CircleImageView headImage;
        TextView nickName;
        TextView textNum;
        public MyViewHodler(View itemView) {
            super(itemView);
            rankNum = (TextView) itemView.findViewById(R.id.rank_num);
            headImage = (CircleImageView) itemView.findViewById(R.id.head_image);
            nickName = (TextView) itemView.findViewById(R.id.nick_name);
            textNum = (TextView) itemView.findViewById(R.id.text_num);
        }
    }
    class MyHeadViewHodler extends RecyclerView.ViewHolder{
        CircleImageView headImage;
        TextView nickName;
        TextView rankNum;
        TextView textNum;
        public MyHeadViewHodler(View itemView) {
            super(itemView);
            rankNum = (TextView) itemView.findViewById(R.id.rank_num);
            headImage = (CircleImageView) itemView.findViewById(R.id.head_image);
            nickName = (TextView) itemView.findViewById(R.id.nick_name);
            textNum = (TextView) itemView.findViewById(R.id.text_num);
        }
    }
}
