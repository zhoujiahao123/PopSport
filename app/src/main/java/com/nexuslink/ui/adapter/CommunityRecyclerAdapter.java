package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.CommentItemData;
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.presenter.communitypresenter.CommunityPresenter;
import com.nexuslink.presenter.communitypresenter.CommunityPresenterImpl;
import com.nexuslink.ui.view.CommunityView;
import com.nexuslink.ui.view.likeview.CommentPathAdapter;
import com.nexuslink.ui.view.likeview.LikeView;
import com.nexuslink.ui.view.linearlistview.LinearListView;
import com.nexuslink.ui.view.view.headerview.MultiView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.CommunityViewsLoadUtils;
import com.nexuslink.util.KeyBoardUtils;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 猿人 on 2017/2/8.
 */

public class CommunityRecyclerAdapter extends RecyclerView.Adapter<CommunityRecyclerAdapter.CommunityViewHolder> implements CommunityView {
    //===============================================常量
    private static final String TAG = "CommunityRecycler";
    //===============================================数据
    private List<CommunityInfo.ArticlesBean> data = new ArrayList<>() ;
    private Context mContext;
    private LayoutInflater inflater;
    //单一信息
    private CommunityInfo.ArticlesBean articlesBean;
    //用于加载总体
    private CommunityPresenter presenter;
    //用于加载用户信息的presenter
    //控制键盘弹起落下变脸
    private boolean isOpen = false;
    private Map<Integer,CommentAdapter> commentAdapterMap = new HashMap<>();


    public CommunityRecyclerAdapter(Context context) {

        this.mContext = context;
        presenter = new CommunityPresenterImpl(this);
        inflater = LayoutInflater.from(mContext);
        //初始化时，需要进行刷新
        presenter.onRefreshData(UserUtils.getUserId());
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
        holder.userLevel.setText("");
        holder.userName.setText("");
    }

    @Override
    public void onBindViewHolder(final CommunityViewHolder holder, final int position) {
        articlesBean = data.get(position);

        //进行用户相关信息的加载
        Log.i(TAG,"加载:"+position);
        //设置占位符
        holder.userImage.setImageDrawable(null);
        //为userInfo设置tag
        presenter.loadUserInfo(holder.userImage,holder.userName,holder.userLevel,articlesBean.getUserId());

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
        holder.mContent.setText(articlesBean.getText());
        holder.imagesContent.setImages(getCommunityImages(articlesBean.getImages()));

        //设置点赞
        holder.likeView.setActivated(articlesBean.isLikeArticle());
        holder.likeView.setNumber(articlesBean.getLikeNum());
        holder.likeView.setCallback(new LikeView.SimpleCallback(){
                        @Override
                        public void activate(LikeView view) {
                            super.activate(view);
                            presenter.postLike(articlesBean.getUserId(),articlesBean.getArticleId());
                        }
                        @Override
                        public void deactivate(LikeView view) {
                            super.deactivate(view);
                            presenter.postDisLike(articlesBean.getUserId(),articlesBean.getArticleId());
                        }
                    }
        );
        //设置评论图标
        //评论区的个数用外部集合个数接口实现
        holder.comment.setNumber(articlesBean.getCommentNum());
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
        if(articlesBean.getCommentNum() > 0) {
            presenter.loadComment(holder.commentDetialLinear, articlesBean.getArticleId(), position);
        }else{
            holder.commentDetialLinear.setVisibility(View.GONE);
        }

        //设置自我评论区
        holder.commentPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"进行评论");
                KeyBoardUtils.closeKeybord(holder.commentInput,mContext);
                //请求相应
                presenter.postComment(articlesBean.getArticleId(),holder.commentInput
                ,holder.linearLayout,UserUtils.getUserId(),articlesBean.getArticleId(),position);

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
     * @param index
     * @param list
     */
    public void addItems(int index,List<CommunityInfo.ArticlesBean> list){
        data.addAll(index,list);
        notifyDataSetChanged();
    }


    @Override
    public void showSuccess(String str) {
        ToastUtil.showToast(mContext,str);
    }

    @Override
    public void showError(String str) {
        ToastUtil.showToast(mContext,str);
    }

    @Override
    public String getInputComment(EditText input) {
        return input.getText().toString();

    }

    @Override
    public void clearInput(LinearLayout linearLayout,EditText input) {
        input.setText("");
        linearLayout.setVisibility(View.GONE);
    }

    @Override
    public void addMsgArticle(List<CommunityInfo.ArticlesBean> list) {
        addItems(0,list);
    }
    @Override
    public void loadUserInfo(ImageView imageView, TextView nameText, TextView levelText, String imageUrl, String userName, String userLevel) {

       {
            CommunityViewsLoadUtils.loadUserImage(mContext,imageView,imageUrl);
            CommunityViewsLoadUtils.loadUserName(nameText,userName);
            CommunityViewsLoadUtils.loadUserLevel(levelText,userLevel);
        }

    }


    @Override
    public void addOneComment(int articleId,String userName, String text) {
        CommentItemData commentItemData = new CommentItemData(userName,text);
        commentAdapterMap.get(articleId).addItem(commentItemData);
    }

    @Override
    public void setCommentAdapter(LinearListView linearListView,int articleId,List<CommentItemData> list) {
        CommentAdapter adapter = new CommentAdapter(mContext,list);
        commentAdapterMap.put(articleId,adapter);
        linearListView.setAdapter(adapter);
    }


    /**
     * 增加点评量
     * @param pos 指定是哪个话题的评论数量
     */
    @Override
    public void addCommentNum(int pos) {
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
        LinearListView commentDetialLinear;
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
            commentDetialLinear = (LinearListView) itemView.findViewById(R.id.comment_detail_linear);
        }
    }



}
