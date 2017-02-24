package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.nexuslink.ui.view.CommentsList;
import com.nexuslink.ui.view.likeview.CommentPathAdapter;
import com.nexuslink.ui.view.likeview.LikeView;
import com.nexuslink.ui.view.view.headerview.MultiView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.KeyBoardUtils;
import com.nexuslink.util.UserUtils;
import com.nexuslink.util.cache.DiskLruCacheHelper;

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


    //用户头像点击接口
    public interface UserIconClickListener{
        void onClickListener(int pos);
    }
    private UserIconClickListener clickListener;
    public void setUserIconClickListener(UserIconClickListener listener){
        this.clickListener = listener;
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
        holder.commentDetialLinear.setAdapter(null);
    }

    @Override
    public void onBindViewHolder(final CommunityViewHolder holder, final int position) {

        //进行用户相关信息的加载
        //设置发表话题人的相关信息
        CommunityInfo.ArticlesBean.UserBeanBean user = data.get(position).getUserBean();
        //设置头像
        Glide.with(mContext).load(Constants.PHOTO_BASE_URL+user.getUImg())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                //设置50%的缩略图
                .thumbnail(0.5f)
                .crossFade().into(holder.userImage);
        //设置其他信息
        holder.userName.setText(user.getUName());
        holder.userLevel.setText("Lv."+UserUtils.getUserLevel(user.getUExp()));
        //回调点击接口
        holder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null){
                    clickListener.onClickListener(position);
                }
            }
        });

        //话题信息 图片和文字
        holder.mContent.setText(data.get(position).getText());
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
        holder.commentDetialLinear.setLayoutManager(new LinearLayoutManager(mContext));
         if(data.get(position).getCommentNum() > 0 &&
                 isFirstLoads.size() != 0 &&
                 isFirstLoads.get(position) == true) {
             Log.i(TAG,"从网络中进行调用");
            isFirstLoads.set(position,false);
            presenter.loadComment(holder.commentDetialLinear, data.get(position).getArticleId(), position);
        }else if(data.get(position).getCommentNum() <= 0){
            holder.commentDetialLinear.setVisibility(View.GONE);
        }else{
             Log.i(TAG,"从缓存中调用");
             List<CommentItemData> commentItemDatas = helper.getAsSerializable(data.get(position).getArticleId()+"comments");
             CommentsAdapter adapter = new CommentsAdapter(commentItemDatas,mContext);
             //再次绑定
             holder.commentDetialLinear.setAdapter(null);
             holder.commentDetialLinear.setAdapter(adapter);
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
        if(listener!=null){
            listener.Completed();
        }
        notifyDataSetChanged();
    }
    //设置刷新回调接口
    public interface onCompleteListener{
        void Completed();
        void Error(String msg);
    }
    private onCompleteListener listener;
    public void setOnCompleteListener(onCompleteListener listener){
        this.listener = listener;
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

        if(listener !=null){
            listener.Completed();
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

    class CommunityViewHolder extends RecyclerView.ViewHolder{
        CircleImageView userImage;
        TextView userName,userLevel,mContent;
        MultiView imagesContent;
        LikeView likeView;
        LikeView comment;
        EditText commentInput;
        //输入框
        LinearLayout linearLayout;
        //评论区的内容
        CommentsList commentDetialLinear;
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
            commentDetialLinear = (CommentsList) itemView.findViewById(R.id.comment_detail_linear);
        }
    }



}
