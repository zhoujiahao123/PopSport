package com.nexuslink.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.LoadRoomsResult;
import com.nexuslink.ui.activity.RunHouseDetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 猿人 on 2017/2/2.
 */

public class RunHouseAdapter extends RecyclerView.Adapter<RunHouseAdapter.RunHouseViewHolder> {

    /**
     * 数据
     */
    private List<LoadRoomsResult.RoomBean> datas = new ArrayList<>();
    /**
     * 格式控制
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("MM:dd HH:mm");
    /**
     * 初始化需要
     */
    private Context mContext;
    private LayoutInflater inflater;


    public RunHouseAdapter(Context context) {
        this.mContext = context;
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
     *增添数据
     */
    public void addItems(List<LoadRoomsResult.RoomBean> list){
        int index = datas.size();
        datas.addAll(index,list);
        notifyDataSetChanged();
    }

    /**
     *设置数据
     */
    public void setDatas(List<LoadRoomsResult.RoomBean> list){
        datas.clear();
        datas.addAll(list);
        notifyDataSetChanged();
    }

    /**
     *提供外部接口调用数据
     */
    public List<LoadRoomsResult.RoomBean> getDatas(){
        return datas;
    }


    @Override
    public RunHouseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.run_house_item,parent,false);
        return new RunHouseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RunHouseViewHolder holder, final int position) {

        holder.runHouseNameTv.setText(datas.get(position).getRoomName());
        holder.runHouseStartTimeTv.setText(sdf.format(datas.get(position).getStartTime()));
        holder.runHouseTypeImage.setImageResource(datas.get(position).getRoomType()==1?R.drawable.roadtype:R.drawable.timetype);
        String str = datas.get(position).getRoomType() == 1 ? "米":"分钟";
        holder.runHouseDetail.setText(datas.get(position).getRoomGoal()+str);
        holder.currentPersons.setText(datas.get(position).getUsers().size()+"人");
        //加载图片
        if(datas.get(position).getUsers() != null && datas.get(position).getUsers().size() >0){
            Glide.with(mContext).load(Constants.PHOTO_BASE_URL+datas.get(position).getUsers().get(0)
                    .getUImg())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.runHouseImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //点击进入详细界面
                Intent intent = new Intent(mContext, RunHouseDetailActivity.class);
                intent.putExtra("roominfo", datas.get(position));
                mContext.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return datas.size();
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
