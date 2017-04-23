package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.model.data.RoomGoal;
import com.nexuslink.util.CircleImageView;

import java.util.List;

/**
 * Created by 猿人 on 2017/3/9.
 */

public class RoomResultRecylerAdapter extends RecyclerView.Adapter<RoomResultRecylerAdapter.RoomResyltRecylcerViewHolder> {

    //===============================================变量
    private Context mContext;
    private LayoutInflater inflater;
    private List<RoomGoal.GoalsBean> goalsBeanList;
    private int type;

    public RoomResultRecylerAdapter(Context mContext, List<RoomGoal.GoalsBean> goalsBeanList,int type) {
        this.mContext = mContext;
        this.goalsBeanList = goalsBeanList;
        this.type = type;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RoomResyltRecylcerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.room_result_recylcer_item,parent,false);
        return new RoomResyltRecylcerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomResyltRecylcerViewHolder holder, int position) {
        //Glide.with(mContext).load("").crossFade().into(holder.userImge);
        holder.userNameTv.setText(goalsBeanList.get(position).getUserName());
        holder.typeImage.setImageDrawable(type == 0? mContext.getDrawable(R.drawable.history_time):mContext.getDrawable(R.drawable.footprint));
        //设置头像点击接口
        holder.userImge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext)
            }
        });
    }

    @Override
    public int getItemCount() {
        return goalsBeanList.size();
    }

    class RoomResyltRecylcerViewHolder extends RecyclerView.ViewHolder{
        CircleImageView userImge;
        ImageView typeImage;
        TextView userNameTv,goalTv;
        public RoomResyltRecylcerViewHolder(View itemView) {
            super(itemView);
            userImge = (CircleImageView) itemView.findViewById(R.id.user_image);
            typeImage = (ImageView) itemView.findViewById(R.id.type);
            userNameTv = (TextView) itemView.findViewById(R.id.user_name);
            goalTv = (TextView) itemView.findViewById(R.id.goal);
        }
    }
}
