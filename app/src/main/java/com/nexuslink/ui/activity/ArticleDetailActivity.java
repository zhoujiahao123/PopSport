package com.nexuslink.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.CommentInfo;
import com.nexuslink.model.data.SingleCommunityInfo;
import com.nexuslink.presenter.articlepresenter.ArticleDetailPresenter;
import com.nexuslink.presenter.articlepresenter.ArticleDetailPresenterImpl;
import com.nexuslink.ui.view.ArticleDetailView;
import com.nexuslink.ui.view.MyNineGridLayout;
import com.nexuslink.ui.view.likeview.LikeView;
import com.nexuslink.ui.view.view.headerview.LoadingView;
import com.nexuslink.util.Base64Utils;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.KeyBoardUtils;
import com.nexuslink.util.TimeUtils;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.UserUtils;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiTextView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import static com.nexuslink.app.BaseApplication.mContext;

public class ArticleDetailActivity extends SwipeBackActivity implements ArticleDetailView {

    @BindView(R.id.user_image)
    CircleImageView userImage;

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.user_level)
    TextView userLevel;

    @BindView(R.id.article_date_tv)
    TextView articleDateTv;

    @BindView(R.id.tv_content)
    EmojiTextView tvContent;

    @BindView(R.id.multi_view)
    MyNineGridLayout multiView;


    @BindView(R.id.comments_detail)
    LinearLayout commentsDetail;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.input_comment)
    EditText inputComment;
    @BindView(R.id.input_send_comment)
    Button inputSendComment;
    @BindView(R.id.progressbar)
    LoadingView progressbar;
    @BindView(R.id.like)
    ImageView likeImage;
    @BindView(R.id.like_num)
    TextView likeNum;
    @BindView(R.id.comment)
    ImageView commentImage;
    @BindView(R.id.comment_num)
    TextView commentNum;


    private LinearLayout commentLinear;
    private EmojiEditText commentInput;
    private Button postComment;


    /**
     * 数据
     */
    private int articleId;
    private SingleCommunityInfo.ArticleBean article;
    private int commentNumber;
    private boolean isOpen = false;
    /**
     * presenter
     */
    private ArticleDetailPresenter presenter;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);
        presenter = new ArticleDetailPresenterImpl(this);
        initViews();
        //得到从外部传来的articleId
        articleId = getIntent().getIntExtra("articleId", -1);
        //进行加载
        if (articleId != -1) {
            //进行网络请求并开始装载view
            presenter.loadArticle(articleId);
        } else {
            ToastUtil.showToast(this, "出现未知错误，请重试");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        int childCount = multiView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Glide.clear(multiView.getChildAt(i));
        }


    }

    private void initViews() {
        setActionBar(mToolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);
        commentLinear = (LinearLayout) findViewById(R.id.comment_linear);
        commentInput = (EmojiEditText) findViewById(R.id.input_comment);
        postComment = (Button) findViewById(R.id.input_send_comment);
        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (article != null) {
                    presenter.postComment(article.getArticleId());
                    postComment.setClickable(false);
                    postComment.setBackground(getResources().getDrawable(R.drawable.bt_unclickable));
                } else {
                    ToastUtil.showToast(ArticleDetailActivity.this, "上传时出错啦");
                }
                KeyBoardUtils.closeKeybord(commentInput, ArticleDetailActivity.this);
            }
        });
        //toolbar
        setActionBar(mToolbar);
        getActionBar().setDisplayShowTitleEnabled(false);

        //喜欢回调
        likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!article.isLikeArticle()) {
                    presenter.postLike(articleId);
                    //更新状态
                    likeImage.setImageDrawable(getDrawable(R.drawable.like));
                    article.setLikeArticle(true);
                    article.setLikeNum(article.getLikeNum()+1);
                    likeNum.setText(article.getLikeNum()+"");
                } else {
                    presenter.postDisLke(articleId);
                    //更新状态
                    likeImage.setImageDrawable(getDrawable(R.drawable.dislike));
                    article.setLikeArticle(false);
                    article.setLikeNum(article.getLikeNum()-1);
                    likeNum.setText(article.getLikeNum()+"");
                }
            }
        });
        //设置评论回调
        commentNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    isOpen = true;
                    commentLinear.setVisibility(View.VISIBLE);
                    //弹起输入框
                    commentInput.requestFocus();
                    KeyBoardUtils.openKeybord(commentInput, mContext);

                } else {
                    isOpen = false;
                    KeyBoardUtils.closeKeybord(commentInput, mContext);
                    commentLinear.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 信息的加载
     */
    private void setUpViews() {
        //设置用户个人信息
        final SingleCommunityInfo.ArticleBean.UserBeanBean user = article.getUserBean();
        //头像加载
        Glide.with(this).load(Constants.PHOTO_BASE_URL + user.getUImg()).crossFade().into(userImage);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OtherPersonActivity.class);
                intent.putExtra("uId", user.getUid());
                mContext.startActivity(intent);
            }
        });
        userName.setText(user.getUName());
        userLevel.setText("Lv." + UserUtils.getUserLevel(user.getUExp()));
        //设置发表日期
        articleDateTv.setText(article.getDate() + " " + article.getTime());
        //设置文本内容
        tvContent.setText(Base64Utils.decode(article.getText()));

        //设置图片集合
        multiView.setIsShowAll(true);
        multiView.setUrlList(getImagesUrl(article.getImages()));
        //设置点赞数目和评论数目
        if(article.isLikeArticle()){
            likeImage.setImageDrawable(getDrawable(R.drawable.like));
        }else{
            likeImage.setImageDrawable(getDrawable(R.drawable.dislike));
        }
        likeNum.setText(article.getLikeNum() + "");

        commentNumber = article.getCommentNum();
        commentNum.setText(commentNumber + "");
        //加载评论
        if (commentNumber > 0) {
            presenter.loadComments(article.getArticleId());
        }

    }

    /**
     * 拼装urls
     *
     * @param images
     */
    private List getImagesUrl(List<String> images) {
        List<String> imageUrls = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            imageUrls.add(Constants.PHOTO_BASE_URL + images.get(i));
        }
        return imageUrls;

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post("刷新");
    }

    @Override
    public void setCommentsView(List<CommentInfo.CommentsBean> list) {
        commentsDetail.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            CommentInfo.CommentsBean commentsBean = list.get(i);
            View view = inflater.inflate(R.layout.comment_detial_item, null);
            setViews(view, commentsBean);
            commentsDetail.addView(view);
            Log.i("添加view", "添加view");
        }
    }

    /**
     * 设置comment view
     *
     * @param view
     * @param commentsBean
     */
    private void setViews(View view, CommentInfo.CommentsBean commentsBean) {
        TextView commenterName = (TextView) view.findViewById(R.id.user_name);
        TextView commentDateTv = (TextView) view.findViewById(R.id.article_date_tv);
        TextView commentFloor = (TextView) view.findViewById(R.id.comment_floor);
        EmojiTextView commentText = (EmojiTextView) view.findViewById(R.id.comment_text);
        CircleImageView userImage = (CircleImageView) view.findViewById(R.id.user_image);

        CommentInfo.CommentsBean.UserBean user = commentsBean.getUser();
        Glide.with(this).load(Constants.PHOTO_BASE_URL + user.getFImg()).crossFade().into(userImage);
        commenterName.setText(user.getFName());
        commentDateTv.setText(setData(commentsBean.getDate(), commentsBean.getTime()));
        commentFloor.setText(commentsBean.getCommentFloor() + "楼");
        commentText.setText(Base64Utils.decode(commentsBean.getCommentText()));
    }

    private final int SEVEN_DAYS = 7 * 24 * 60 * 60 * 1000;
    //格式控制
    private SimpleDateFormat sdf_month = new SimpleDateFormat("MM:dd HH:mm");
    private SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");
    //日期数据
    private String datas[] = {"今天", "昨天", "前天", "3天前", "4天前", "5天前", "6天前", "7天前"};

    /**
     * 根据不同的条件，设置时间
     *
     * @param date
     * @param time
     * @return
     */
    private String setData(String date, String time) {
        Calendar c = Calendar.getInstance();
        //同步时间
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.AM_PM, 0);
        c.set(Calendar.MINUTE, 0);
        // 判断是不是今年
        if (c.get(Calendar.YEAR) == Integer.valueOf(date.split("-")[0]).intValue()) {
            //是今年，那么是否有超过7天
            long dateMills = TimeUtils.DateToMills(date + " " + time);
            if (c.getTimeInMillis() - dateMills > SEVEN_DAYS) {
                return sdf_month.format(dateMills);
            } else {
                //没有超过，就判断相几天
                Calendar c1 = Calendar.getInstance();
                //同步时间
                c1.setTimeInMillis(dateMills);
                int index = Math.abs(c.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR));
                return datas[index] + sdf_time.format(dateMills);
            }
        } else {
            return date + " " + time;
        }
    }

    @Override
    public String getCommentInput() {
        return commentInput.getText().toString();
    }

    @Override
    public void showError(String str) {
        ToastUtil.showToast(this, str);
    }

    @Override
    public void showSuccess(String str) {
        ToastUtil.showToast(this, str);
    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setUpViews(SingleCommunityInfo.ArticleBean articlesBean) {
        article = articlesBean;
        setUpViews();
    }

    @Override
    public void addCommentView(CommentInfo.CommentsBean bean) {
        View view = inflater.inflate(R.layout.comment_detial_item, null);
        setViews(view, bean);
        commentsDetail.addView(view);
    }

    @Override
    public void addCommentNum() {
        commentNum.setText(++commentNumber + "");
    }

    @Override
    public void clear() {
        commentInput.setText("");
        commentLinear.setVisibility(View.GONE);
        postComment.setClickable(true);
        postComment.setBackground(getResources().getDrawable(R.drawable.bt_run_house_normal));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
