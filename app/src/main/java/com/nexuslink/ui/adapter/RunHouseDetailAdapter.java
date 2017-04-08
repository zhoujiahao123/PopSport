package com.nexuslink.ui.adapter;

import android.content.Context;
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

import java.util.List;

/**
 * Created by 猿人 on 2017/2/25.
 */

public class RunHouseDetailAdapter extends RecyclerView.Adapter<RunHouseDetailAdapter.RunHouseDetialViewHolder> {
    /**
     * 初始化需要
     */
    private List<LoadRoomsResult.RoomBean.UsersBean> usersList ;
    private Context mContext;
    private LayoutInflater inflater;


    /**
     * 提供增加的接口
     */
    public void addItem(LoadRoomsResult.RoomBean.UsersBean users){
        //增加动画
        notifyItemInserted(usersList.size());
        usersList.add(users);
        //数据更新
        notifyDataSetChanged();
    }
    /**
     * 移除item接口
     */
    public void remoteItem(int id){
        for(int i =0;i<usersList.size();i++){
            if(usersList.get(i).getUid() == id){
                usersList.remove(i);
                notifyDataSetChanged();
                break;
            }
        }
    }

    /**
     * 设置数据接口
     */
    public void setDatas(List<LoadRoomsResult.RoomBean.UsersBean> users){
        usersList.clear();
        usersList.addAll(users);
        notifyDataSetChanged();
    }

    public RunHouseDetailAdapter(Context mContext, List<LoadRoomsResult.RoomBean.UsersBean> usersList) {
        this.mContext = mContext;
        this.usersList = usersList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RunHouseDetialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.run_house_user_item,parent,false) ;
        return new RunHouseDetialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RunHouseDetialViewHolder holder, final int position) {
        //设置用户信息
        Glide.with(mContext).load(Constants.PHOTO_BASE_URL+usersList.get(position).getUImg()).
                 crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.userImage);
        holder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, FriendInfoActivity.class);
//                LoadRoomsResult.RoomBean.UsersBean usersBean = usersList.get(position);
//                intent.putExtra("uImg",usersBean.getUImg());
//                intent.putExtra("uId",usersBean.getUid());
//                intent.putExtra("uName",usersBean.getUName());
//                mContext.startActivity(intent);
            }
        });
        holder.userName.setText(usersList.get(position).getUName());
        holder.historyMiles.setText(usersList.get(position).getUHistoryMileage()+"km");
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class RunHouseDetialViewHolder extends  RecyclerView.ViewHolder{
        ImageView userImage;
        TextView userName,historyMiles;
        public RunHouseDetialViewHolder(View itemView) {
            super(itemView);
            userImage = (ImageView) itemView.findViewById(R.id.user_image);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            historyMiles = (TextView) itemView.findViewById(R.id.history_miles);
        }
    }
}
