package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.nexuslink.R;
import com.nexuslink.model.data.RoomGoal;
import com.nexuslink.util.CircleImageView;

/**
 * Created by 猿人 on 2017/3/9.
 */

public class RoomResultRecyclerAdapter extends RecyclerArrayAdapter<RoomGoal.GoalsBean> {

   private int type = -1;
    public RoomResultRecyclerAdapter(Context context,int type) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RoomResultRecyclerViewHolder(parent, R.layout.room_result_recylcer_item);
    }
    class RoomResultRecyclerViewHolder extends BaseViewHolder<RoomGoal.GoalsBean>{

        TextView goal;
        CircleImageView image;
        TextView name;
        TextView rankNum;

        public RoomResultRecyclerViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            rankNum = $(R.id.rank_number);
            name = $(R.id.name);
            image = $(R.id.user_image);
            goal = $(R.id.goal);
        }

        @Override
        public void setData(RoomGoal.GoalsBean data) {
            super.setData(data);
            rankNum.setText(getLayoutPosition()+2+"");
            name.setText(data.getUserName());
            String typeStr = type == 0?"s":"m";
            goal.setText(data.getGoal()+typeStr);
            //差一个头像
        }
    }


}
