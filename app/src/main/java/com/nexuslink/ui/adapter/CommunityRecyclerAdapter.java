package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexuslink.R;
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.ui.view.likeview.CommentPathAdapter;
import com.nexuslink.ui.view.likeview.LikeView;
import com.nexuslink.ui.view.view.headerview.MultiView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.KeyBoardUtils;

import java.util.List;



/**
 * Created by 猿人 on 2017/2/8.
 */

public class CommunityRecyclerAdapter extends RecyclerView.Adapter<CommunityRecyclerAdapter.CommunityViewHolder> {
    //===============================================常量
    private static final String TAG = "CommunityRecyclerAdapter";
    //===============================================数据
    private List<CommunityInfo.CommunityBean> data ;
    private Context mContext;
    private LayoutInflater inflater;
    public CommunityRecyclerAdapter(Context context,List<CommunityInfo.CommunityBean> data) {
        this.data = data;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }
    //用户头像点击接口
    public interface userIconClickListener{
        void onClickListener(int pos);
    }
    private userIconClickListener clickListener;
    public void setUserIconClickListener(userIconClickListener listener){
        this.clickListener = listener;
    }
    @Override
    public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.community_recyler_item,parent,false);
        return new CommunityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommunityViewHolder holder, final int position) {
        CommunityInfo.CommunityBean communityBean = data.get(position);
        //加载话题头像
        Glide.with(mContext).load(communityBean.getUserImageUrl()).into(holder.userImage);
        //回调点击接口
        holder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null){
                    clickListener.onClickListener(position);
                }
            }
        });

        //用户名字和level
        holder.userName.setText(communityBean.getUserName());
        holder.userLevel.setText("Lv."+communityBean.getUserLevel());

        //话题信息
        holder.mContent.setText(communityBean.getText());
        holder.imagesContent.setImages(communityBean.getImages());

        //设置点赞和评论监听
        holder.likeView.setActivated(communityBean.isLikeArticle());
        holder.likeView.setNumber(communityBean.getLikeNum());
        holder.likeView.setCallback(new LikeView.SimpleCallback(){
                @Override
                public void activate(LikeView view) {
                    super.activate(view);
                }

                @Override
                public void deactivate(LikeView view) {
                    super.deactivate(view);
                }
            }
        );
        //设置评论
        holder.comment.setNumber(communityBean.getCommentNum());
        holder.comment.setGraphAdapter(CommentPathAdapter.getInstance());
        holder.comment.setCallback(new LikeView.SimpleCallback(){
            @Override
            public boolean onClick(LikeView view) {
                //弹出编辑框逻辑
                holder.linearLayout.setVisibility(View.VISIBLE);
                holder.commentInput.requestFocus();
                //弹起输入框
                KeyBoardUtils.openKeybord(holder.commentInput,mContext);
                //发送请求
                return true;
            }
        });
        //设置评论区

        holder.commentPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.linearLayout.setVisibility(View.GONE);
                KeyBoardUtils.closeKeybord(holder.commentInput,mContext);
                //请求相应
            }
        });

    }
    public void addItems(int index,List<CommunityInfo.CommunityBean> list){
        data.addAll(index,list);
        notifyDataSetChanged();
    }
    public long getUserId(int pos){
        return data.get(pos).getUserId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CommunityViewHolder extends RecyclerView.ViewHolder{
        CircleImageView userImage;
        TextView userName,userLevel,mContent;
        MultiView imagesContent;
        LikeView likeView;
        LikeView comment;
        EditText commentInput;
        LinearLayout linearLayout;
        private Button commentPost;
        public CommunityViewHolder(View itemView) {
            super(itemView);
            userImage = (CircleImageView) itemView.findViewById(R.id.user_image);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            userLevel = (TextView) itemView.findViewById(R.id.user_level);
            mContent = (TextView) itemView.findViewById(R.id.tv_content);
            imagesContent = (MultiView) itemView.findViewById(R.id.multi_view);
            likeView = (LikeView) itemView.findViewById(R.id.like_view);
            comment = (LikeView) itemView.findViewById(R.id.comment);
            commentInput = (EditText) itemView.findViewById(R.id.input_comment);
            commentPost = (Button) itemView.findViewById(R.id.input_send_comment);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.comment_linear);
        }
    }
}
