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

import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.presenter.communitypresenter.CommunityPresenter;
import com.nexuslink.presenter.communitypresenter.CommunityPresenterImpl;
import com.nexuslink.ui.view.CommunityView;
import com.nexuslink.ui.view.likeview.CommentPathAdapter;
import com.nexuslink.ui.view.likeview.LikeView;
import com.nexuslink.ui.view.view.headerview.MultiView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.CommunityViewsLoadUtils;
import com.nexuslink.util.KeyBoardUtils;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.UserUtils;

import java.util.ArrayList;
import java.util.List;


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


    public CommunityRecyclerAdapter(Context context) {

        this.mContext = context;
        presenter = new CommunityPresenterImpl(this);
        inflater = LayoutInflater.from(mContext);
        //初始化时，需要进行刷新
        presenter.onRefreshData(UserUtils.getUserId());
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
        articlesBean = data.get(position);

        //进行用户相关信息的加载
        Log.i(TAG,"加载:"+position);
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
                }
                return true;
            }
        });


        //设置自我评论区
        holder.commentPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"进行评论");
                String text = holder.commentInput.getText().toString();
                Log.i(TAG,text);
                KeyBoardUtils.closeKeybord(holder.commentInput,mContext);
                //请求相应
                View view = inflater.inflate(R.layout.comment_item,holder.commentDetialLinear,false);
                presenter.postComment(holder.commentDetialLinear,view,
                        holder.commentInput,holder.linearLayout,data.get(position).getUserId(),
                        data.get(position).getArticleId(),position);

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

        Log.i(TAG,"加载用户信息");
        CommunityViewsLoadUtils.loadUserImage(mContext,imageView,imageUrl);
        CommunityViewsLoadUtils.loadUserName(nameText,userName);
        CommunityViewsLoadUtils.loadUserLevel(levelText,userLevel);

    }

    @Override
    public void addOneComment(LinearLayout commentDetialLinear,View view,String userName, String text) {
        synchronized (this){
            Log.i(TAG,"添加评论");
            TextView userNameTv = (TextView) view.findViewById(R.id.commenter_name);
            TextView commentTv = (TextView) view.findViewById(R.id.comment);
            userNameTv.setText(userName+":");
            commentTv.setText(text);
            commentDetialLinear.addView(view);
        }

    }


    @Override
    public void addCommentNum(int pos) {
        synchronized (this){
            int num = data.get(pos).getCommentNum();
            data.get(pos).setCommentNum(++num);
            notifyDataSetChanged();
        }

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
        LinearLayout commentDetialLinear;
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
            commentDetialLinear = (LinearLayout) itemView.findViewById(R.id.comment_detail_linear);
        }
    }



}
