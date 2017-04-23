package com.nexuslink.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nexuslink.R;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.CommentItemData;
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.presenter.communitypresenter.CommunityPresenter;
import com.nexuslink.ui.activity.ArticleDetailActivity;
import com.nexuslink.ui.activity.FriendInfoActivity;
import com.nexuslink.ui.view.likeview.CommentPathAdapter;
import com.nexuslink.ui.view.likeview.LikeView;
import com.nexuslink.ui.view.view.headerview.MultiView;
import com.nexuslink.util.Base64Utils;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.KeyBoardUtils;
import com.nexuslink.util.UserUtils;
import com.nexuslink.util.cache.DiskLruCacheHelper;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiTextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 猿人 on 2017/2/8.
 */

public class CommunityRecyclerAdapter extends RecyclerView.Adapter<CommunityRecyclerAdapter.CommunityViewHolder> {
    //===============================================常量
    private static final String TAG = "CommunityRecycler";
    //===============================================数据
    private List<CommunityInfo.ArticlesBean> data = new ArrayList<>() ;
    private Context mContext;
    private LayoutInflater inflater;

    //用于加载总体
    private CommunityPresenter presenter;
    //用于加载用户信息的presenter
    //控制键盘弹起落下变脸
    private boolean isOpen = false;

    //===============================================缓存类
    private DiskLruCacheHelper helper = BaseApplication.helper;
    //===============================================辅助变量
    public List<Boolean> isFirstLoads = new ArrayList<>();


    public CommunityRecyclerAdapter(Context context,CommunityPresenter presenter) {
        this.mContext = context;
        this.presenter = presenter;
        inflater = LayoutInflater.from(mContext);
    }



    @Override
    public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.community_recyler_item,parent,false);
        return new CommunityViewHolder(view);
    }

    @Override
    public void onViewRecycled(CommunityViewHolder holder) {
        super.onViewRecycled(holder);
        Glide.clear(holder.userImage);
        holder.commentDetialLinear.removeAllViews();
    }

    @Override
    public void onBindViewHolder(final CommunityViewHolder holder, final int position) {

        //为一个话题item设置点击监听
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(mContext, ArticleDetailActivity.class);
                intent.putExtra("articleId", data.get(position).getArticleId());
                mContext.startActivity(intent);
            }
        });

        //进行用户相关信息的加载
        //设置发表话题人的相关信息
        CommunityInfo.ArticlesBean.UserBeanBean user = data.get(position).getUserBean();
        //设置头像
        Glide.with(mContext).load(Constants.PHOTO_BASE_URL+user.getUImg())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                //设置50%的缩略图
                .thumbnail(0.5f)
                .crossFade().into(holder.userImage);
        //设置其他信息
        holder.userName.setText(user.getUName());
        holder.userLevel.setText("Lv."+UserUtils.getUserLevel(user.getUExp()));
        //点击跳转
        holder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(mContext, FriendInfoActivity.class);
                //uImg uName uId
                CommunityInfo.ArticlesBean.UserBeanBean userBeanBean = data.get(position).getUserBean();
                intent.putExtra("uImg",userBeanBean.getUImg());
                intent.putExtra("uName",userBeanBean.getUName());
                intent.putExtra("uId",userBeanBean.getUid());
                mContext.startActivity(intent);
            }
        });

        //话题信息 图片和文字
        //设置文本内容时候，进行解析
        holder.mContent.setText(Base64Utils.decode(data.get(position).getText()));
        holder.imagesContent.setImages(getCommunityImages(data.get(position).getImages()));

        //设置点赞
        holder.likeView.setActivated(data.get(position).isLikeArticle());
        holder.likeView.setNumber(data.get(position).getLikeNum());
        holder.likeView.setCallback(new LikeView.SimpleCallback(){
                        @Override
                        public void activate(LikeView view) {
                            super.activate(view);
                            presenter.postLike(data.get(position).getUserId(),data.get(position).getArticleId());
                        }
                        @Override
                        public void deactivate(LikeView view) {
                            super.deactivate(view);
                            presenter.postDisLike(data.get(position).getUserId(),data.get(position).getArticleId());
                        }
                    }
        );
        //设置评论图标
        //评论区的个数用外部集合个数接口实现
        holder.comment.setNumber(data.get(position).getCommentNum());
        holder.comment.setGraphAdapter(CommentPathAdapter.getInstance());
        holder.comment.setCallback(new LikeView.SimpleCallback(){
            @Override
            public boolean onClick(LikeView view) {
                if(!isOpen){
                    isOpen = true;

                    holder.linearLayout.setVisibility(View.VISIBLE);
                    holder.commentInput.requestFocus();
                    //弹起输入框
                    KeyBoardUtils.openKeybord(holder.commentInput,mContext);
                }else{
                    isOpen = false;
                    KeyBoardUtils.closeKeybord(holder.commentInput,mContext);
                    holder.linearLayout.setVisibility(View.GONE);
                }
                return true;
            }
        });


        //设置总评论区

         if(data.get(position).getCommentNum() > 0 &&
                 isFirstLoads.size() != 0 &&
                 isFirstLoads.get(position) == true) {
             Log.i(TAG,"从网络中进行调用");
            isFirstLoads.set(position,false);
            presenter.loadComment(holder.commentDetialLinear, data.get(position).getArticleId(), position);
        }else{
             Log.i(TAG,"从缓存中调用");

             List<CommentItemData> commentItemDatas = helper.getAsSerializable(data.get(position).getArticleId()+"comments");
             if(commentItemDatas != null && commentItemDatas.size() > 0){
                 for(int i = 0 ;i<commentItemDatas.size();i++){
                     CommentItemData commentItemData = commentItemDatas.get(i);
                     View view = inflater.inflate(R.layout.comment_item,null);
                     SpannableString msg = new SpannableString(commentItemData.getUserName()+":"+commentItemData.getCommentText());
                     msg.setSpan(new ForegroundColorSpan(0xff6b8747),0,commentItemData.getUserName().length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                     EmojiTextView tv = (EmojiTextView)view.findViewById(R.id.comment);
                     tv.setText(msg);
                     holder.commentDetialLinear.addView(view);
                 }
             }


             //再次绑定

        }

        //设置自我评论区
        holder.commentPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"进行评论");
                KeyBoardUtils.closeKeybord(holder.commentInput,mContext);
                //请求响应
                Log.i(TAG,"aod"+data.get(position).getArticleId()+"");
                presenter.postComment(holder.commentDetialLinear,holder.commentInput
                ,holder.linearLayout,UserUtils.getUserId(),data.get(position).getArticleId(),position);
                holder.commentPost.setClickable(false);
                holder.commentPost.setBackground(mContext.getResources().getDrawable(R.drawable.bt_unclickable));
            }
        });
    }


    /**
     * 封装图片url
     * @param images
     * @return
     */
    private String[] getCommunityImages(List<String> images) {
        String imageUrls[] = new String[images.size()];
       for(int i =0;i<imageUrls.length;i++){
           imageUrls[i] = Constants.PHOTO_BASE_URL+images.get(i);
       }
        return imageUrls;

    }

    /**
     * 增添数据接口
     */
    public void addItems(List<CommunityInfo.ArticlesBean> list){
        int index = data.size();
        data.addAll(index,list);
        for(int i =0;i < list.size();i++){
            isFirstLoads.add(i+index,true);
        }

        notifyDataSetChanged();
    }

    /**
     *
     */
    public List<CommunityInfo.ArticlesBean> getDatas(){
        return data;
    }
    /**
     * 设置数据
     */
    public void setDatas(List<CommunityInfo.ArticlesBean> list){
        data.clear();
        data.addAll(list);

        isFirstLoads.clear();
        for(int i =0;i<list.size();i++){
            isFirstLoads.add(i,true);
        }



        notifyDataSetChanged();
    }

    public void addCommentNum(int pos){
        int num = data.get(pos).getCommentNum();
        data.get(pos).setCommentNum(++num);
        notifyDataSetChanged();
    }




    public long getUserId(int pos){
        return data.get(pos).getUserId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * viewholder
     */
    class CommunityViewHolder extends RecyclerView.ViewHolder{
        CircleImageView userImage;
        TextView userName,userLevel;
        EmojiTextView mContent;
        MultiView imagesContent;
        LikeView likeView;
        LikeView comment;
        EmojiEditText commentInput;
        //输入框
        LinearLayout linearLayout;
        //评论区的内容
        LinearLayout commentDetialLinear;
        private Button commentPost;
        public CommunityViewHolder(View itemView) {
            super(itemView);
            userImage = (CircleImageView) itemView.findViewById(R.id.user_image);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            userLevel = (TextView) itemView.findViewById(R.id.user_level);
            mContent = (EmojiTextView) itemView.findViewById(R.id.tv_content);
            imagesContent = (MultiView) itemView.findViewById(R.id.multi_view);
            likeView = (LikeView) itemView.findViewById(R.id.like_view);
            comment = (LikeView) itemView.findViewById(R.id.comment);
            commentInput = (EmojiEditText) itemView.findViewById(R.id.input_comment);
            commentPost = (Button) itemView.findViewById(R.id.input_send_comment);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.comment_linear);
            commentDetialLinear = (LinearLayout) itemView.findViewById(R.id.comments_detail);

        }
    }



}
