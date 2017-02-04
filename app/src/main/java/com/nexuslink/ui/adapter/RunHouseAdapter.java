package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexuslink.R;
import com.nexuslink.model.data.RunHouseInfo;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/2.
 */

public class RunHouseAdapter extends RecyclerView.Adapter<RunHouseAdapter.RunHouseViewHolder> {

    private List<RunHouseInfo.RunHouseBean> data;
    private Context mContext;
    private LayoutInflater inflater;

    public RunHouseAdapter(Context context,List<RunHouseInfo.RunHouseBean> data) {
        this.mContext = context;
        this.data = data;
        inflater = LayoutInflater.from(mContext);
    }
    //点击接口
    public interface OnClickListener{
        void onItemClickListener(View view,int pos);
    }
    private OnClickListener mClickListnter;
    public void setOnClickListener(OnClickListener listener){
        this.mClickListnter = listener;
    }

    /**
     *add
     */
    public void addItems(List<RunHouseInfo.RunHouseBean> list){
        data.addAll(list);
        notifyDataSetChanged();
    }
    public void deleteItem(int pos){
        data.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(0,data.size());
    }


    @Override
    public RunHouseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.run_house_item,parent,false);
        return new RunHouseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RunHouseViewHolder holder, final int position) {
        holder.runHouseNameTv.setText(data.get(position).getName());
        holder.runHouseStartTimeTv.setText(data.get(position).getStartTime());
        holder.runHouseTypeImage.setImageResource(data.get(position).getRunType()==1?R.drawable.roadtype:R.drawable.timetype);
        holder.runHouseDetail.setText(data.get(position).getRunDetial());
        holder.currentPersons.setText(data.get(position).getCurrentPersons());
        //加载图片
        Glide.with(mContext).load(data.get(position).getImageUrl()).into(holder.runHouseImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListnter!=null){
                    mClickListnter.onItemClickListener(v,position);
                }
            }
        });

    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    class RunHouseViewHolder extends  RecyclerView.ViewHolder{
        ImageView runHouseImage,runHouseTypeImage;
        TextView runHouseNameTv,runHouseStartTimeTv,runHouseDetail,currentPersons;
        public RunHouseViewHolder(View itemView) {
            super(itemView);
            runHouseImage = (ImageView) itemView.findViewById(R.id.run_house_image);
            runHouseNameTv = (TextView) itemView.findViewById(R.id.run_house_name);
            runHouseStartTimeTv = (TextView) itemView.findViewById(R.id.run_house_start_time);
            runHouseTypeImage = (ImageView) itemView.findViewById(R.id.run_house_type_image);
            runHouseDetail = (TextView) itemView.findViewById(R.id.run_house_detail);
            currentPersons = (TextView) itemView.findViewById(R.id.run_house_current_persons);
        }
    }
}
