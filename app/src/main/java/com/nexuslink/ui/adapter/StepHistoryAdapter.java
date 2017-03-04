package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.Steps;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/28.
 */

public class StepHistoryAdapter extends RecyclerView.Adapter<StepHistoryAdapter.StepHistroyViewHolder> {
    private List<Steps> list;
    private LayoutInflater inflater;


    public StepHistoryAdapter(Context context,List<Steps> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public StepHistroyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.step_history_item,parent,false);
        return new StepHistroyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepHistroyViewHolder holder, int position) {
        Steps steps = list.get(position);
        holder.dateTv.setText(steps.getDate());
        holder.stepsTv.setText(steps.getUStep()+"");
    }

    @Override
    public int getItemCount() {
        return list == null?0:list.size();
    }

    class StepHistroyViewHolder extends RecyclerView.ViewHolder{
        TextView dateTv,stepsTv;
        public StepHistroyViewHolder(View itemView) {
            super(itemView);
            stepsTv = (TextView) itemView.findViewById(R.id.steps_tv);
            dateTv = (TextView) itemView.findViewById(R.id.date_tv);
        }
    }
}
