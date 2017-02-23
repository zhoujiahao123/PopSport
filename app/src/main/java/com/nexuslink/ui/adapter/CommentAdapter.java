package com.nexuslink.ui.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.model.data.CommentItemData;

import java.util.List;

/**
 * Created by mabeijianxi on 2016/1/6.
 */
public class CommentAdapter extends BaseAdapter {
    private final Context mContext;
    private List<CommentItemData> mEvaluatereplysList;

    public CommentAdapter(Context mContext, List<CommentItemData> evaluatereplysList) {
        this.mContext = mContext;
        this.mEvaluatereplysList = evaluatereplysList;
    }
    public void addItem(CommentItemData commentItemData){
        mEvaluatereplysList.add(commentItemData);
        notifyDataSetChanged();
    }

    public void setDatas(List<CommentItemData> list){
        mEvaluatereplysList.clear();
        mEvaluatereplysList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mEvaluatereplysList != null) {
            return mEvaluatereplysList.size();
        }
        return 0;
    }

    @Override
    public CommentItemData getItem(int position) {
        return mEvaluatereplysList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
//           convertView = View.inflate(mContext, R.layout.item_evaluatereply, parent);
            convertView = LayoutInflater.from(mContext).inflate(R.layout.comment_item, parent, false);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.comment);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CommentItemData evaluatereplysBean = mEvaluatereplysList.get(position);

        SpannableString msp = new SpannableString(evaluatereplysBean.getUserName() + ":" + evaluatereplysBean.getCommentText());
        msp.setSpan(new ForegroundColorSpan(0xff6b8747), 0, evaluatereplysBean.getUserName().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tv.setText(msp);
        return convertView;
    }

    static class ViewHolder {
        public TextView tv;
    }


}
