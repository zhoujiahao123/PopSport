package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.RankInfo;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.loader.LoaderFactory;

/**
 * Created by ASUS-NB on 2017/2/13.
 */

public class RankRecyclerViewAdapter extends RecyclerView.Adapter<RankRecyclerViewAdapter.MyViewHolder>{
    private RankInfo rankInfo;
    private Context mContext;
    static OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClock(int position);
    }
    public static void setListener(OnItemClickListener listener){
        mListener = listener;
    }
    public RankRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public void addData(RankInfo rankInfo){
        this.rankInfo = rankInfo;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rank,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            LoaderFactory.getGlideLoader().loadNet(holder.headImage, Constants.PHOTO_BASE_URL+rankInfo.getUsers().get(position).getUImg(),null);
            holder.rankNum.setText(position+2+"");
            holder.nickName.setText(rankInfo.getUsers().get(position).getUName());
            holder.sum.setText(rankInfo.getUsers().get(position+1).getUHistoryMileage()+"");
    }

    @Override
    public int getItemCount() {
        if (rankInfo==null)
            return 0;
        else
        return rankInfo.getUsers().size()-1;
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClock(getPosition()+1);
                }
            });
        }
    }
}
