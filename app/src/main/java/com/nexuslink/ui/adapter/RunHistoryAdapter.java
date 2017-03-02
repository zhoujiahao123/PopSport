package com.nexuslink.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.Run;
import com.nexuslink.ui.activity.RecordShowActivity;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by 猿人 on 2017/2/28.
 */

public class RunHistoryAdapter extends RecyclerView.Adapter<RunHistoryAdapter.RunHistoryViewHolder> {
    private List<Run> runList;
    private LayoutInflater inflater;
    private Context mContext;
    private DecimalFormat df = new DecimalFormat("#0.0");
    //===============================================常量
    public static final String RUN_RECORD = "run_record";
    public RunHistoryAdapter(Context context, List<Run> runList) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        this.runList = runList;
    }

    @Override
    public RunHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.run_histroy_item,parent,false);
        return new RunHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RunHistoryViewHolder holder, final int position) {
         Run run = runList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Run run1 = runList.get(position);
                Intent intent = new Intent(mContext, RecordShowActivity.class);
                intent.putExtra(RUN_RECORD,run1.getId());
                mContext.startActivity(intent);
            }
        });
        String date = run.getDate()+" "+run.getTime();
        holder.dateTv.setText(date);
        holder.milesTv.setText(df.format(Float.valueOf(run.getUMileage()))+"m");
        holder.durationTv.setText(df.format(Float.valueOf(run.getDuration()))+"s");
    }

    @Override
    public int getItemCount() {
        return runList == null?0:runList.size();
    }

    class RunHistoryViewHolder extends RecyclerView.ViewHolder{
        TextView durationTv,milesTv,dateTv;
        public RunHistoryViewHolder(View itemView) {
            super(itemView);
            durationTv = (TextView) itemView.findViewById(R.id.duration);
            milesTv = (TextView) itemView.findViewById(R.id.miles);
            dateTv = (TextView) itemView.findViewById(R.id.run_date_tv);
        }
    }
}
