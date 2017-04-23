package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.model.data.CommentItemData;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/23.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<CommentItemData> datas;

    public CommentsAdapter(List<CommentItemData> datas, Context context) {
        this.datas = datas;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 增添数据接口
     */
    public void addItem(CommentItemData commentItemData){
        datas.add(commentItemData);
        notifyDataSetChanged();
    }

    /**
     * 设置总数据接口
     */
    public void setDatas(List<CommentItemData> commentItemDataList){
        datas.clear();
        datas.addAll(commentItemDataList);
        notifyDataSetChanged();
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.comment_item,parent,false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {

        CommentItemData commentItemData = datas.get(position);
        SpannableString msg = new SpannableString(commentItemData.getUserName()+":"+commentItemData.getCommentText());
        msg.setSpan(new ForegroundColorSpan(0xff6b8747),0,commentItemData.getUserName().length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tv.setText(msg);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public CommentsViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.comment);
        }
    }
}
