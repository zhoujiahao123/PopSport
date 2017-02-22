package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.util.CircleImageView;

/**
 * Created by ASUS-NB on 2017/2/13.
 */

public class RankRecyclerViewAdapter extends RecyclerView.Adapter<RankRecyclerViewAdapter.MyViewHolder>{

    private Context mContext;
    public RankRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rank,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.headImage.setImageResource(R.drawable.headimage);
        holder.rankNum.setText(position+2+"");
        holder.nickName.setText("Jacob");
        holder.sum.setText("555555");
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView sum,nickName,rankNum;
        private CircleImageView headImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            sum = (TextView) itemView.findViewById(R.id.sum);
            nickName = (TextView) itemView.findViewById(R.id.nick_name);
            rankNum = (TextView) itemView.findViewById(R.id.rank_num);
            headImage = (CircleImageView) itemView.findViewById(R.id.head_image);
        }
    }
}
