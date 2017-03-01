package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.presenter.articlepresenter.ArticleDetailPresenter;
import com.nexuslink.presenter.articlepresenter.ArticleDetailPresenterImpl;
import com.nexuslink.ui.view.ArticleDetailView;
import com.nexuslink.ui.view.likeview.CommentPathAdapter;
import com.nexuslink.ui.view.likeview.LikeView;
import com.nexuslink.ui.view.view.headerview.MultiView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.KeyBoardUtils;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.UserUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    TextView tvContent;

    @BindView(R.id.multi_view)
    MultiView multiView;

    @BindView(R.id.like_num)
    LikeView likeNum;

    @BindView(R.id.comment_num)
    LikeView commentNum;

    @BindView(R.id.comments_detail)
    LinearLayout commentsDetail;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.input_comment)
    EditText inputComment;
    @BindView(R.id.input_send_comment)
    Button inputSendComment;



    private LinearLayout commentLinear;
    private EditText commentInput;
    private Button postComment;


    /**
     * 数据
     */
    private CommunityInfo.ArticlesBean article;
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
        //得到从外部传来的article信息
        article = getIntent().getParcelableExtra("article");

        if (article != null) {
            //进行view的装载和评论的请求以及装载
            setUpViews();
        } else {
            ToastUtil.showToast(this, "出现未知错误，请重试");
            onBackPressed();
        }
    }

    private void initViews() {
        commentLinear = (LinearLayout) findViewById(R.id.comment_linear);
        commentInput = (EditText) findViewById(R.id.input_comment);
        postComment = (Button) findViewById(R.id.input_send_comment);
        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (article != null) {
                    presenter.postComment(article.getArticleId());
                } else {
                    ToastUtil.showToast(ArticleDetailActivity.this, "上传时出错啦");
                }
                KeyBoardUtils.closeKeybord(commentInput, ArticleDetailActivity.this);
            }
        });
        //toolbar

        setActionBar(mToolbar);
        getActionBar().setDisplayShowTitleEnabled(false);

    }

    /**
     * 如果article不为空，那么就进行设置相关的信息
     */
    private void setUpViews() {
        //设置用户个人信息
        CommunityInfo.ArticlesBean.UserBeanBean user = article.getUserBean();
        //头像加载
        Glide.with(this).load(Constants.PHOTO_BASE_URL + user.getUImg()).crossFade().into(userImage);
        userName.setText(user.getUName());
        userLevel.setText(UserUtils.getUserLevel(user.getUExp()));
        //设置发表日期
        articleDateTv.setText(article.getDate() + " " + article.getTime());
        //设置文本内容
        tvContent.setText(article.getText());
        //设置图片集合
        multiView.setImages(getImagesUrl(article.getImages()));
        //设置点赞数目和评论数目
        likeNum.setActivated(article.isLikeArticle());
        likeNum.setNumber(article.getLikeNum());
        likeNum.setCallback(new LikeView.SimpleCallback() {
            @Override
            public void activate(LikeView view) {
                super.activate(view);
                presenter.postLike();
            }

            @Override
            public void deactivate(LikeView view) {
                super.deactivate(view);
            }
        });
        commentNumber = article.getCommentNum();
        commentNum.setGraphAdapter(CommentPathAdapter.getInstance());
        commentNum.setNumber(commentNumber);
        commentNum.setCallback(new LikeView.SimpleCallback() {
            @Override
            public boolean onClick(LikeView view) {
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
                return true;
            }

            @Override
            public void activate(LikeView view) {
            }

            @Override
            public void deactivate(LikeView view) {
            }
        });
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
    private String[] getImagesUrl(List<String> images) {
        String[] urls = new String[images.size()];
        for (int i = 0; i < images.size(); i++) {
            urls[i] = Constants.PHOTO_BASE_URL + images.get(i);
        }
        return urls;
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
        TextView commentText = (TextView) view.findViewById(R.id.comment_text);
        commenterName.setText(commentsBean.getUserName());
        commentDateTv.setText(commentsBean.getDate() + " " + commentsBean.getTime());
        commentFloor.setText(commentsBean.getCommentFloor() + "楼");
        commentText.setText(commentsBean.getCommentText());
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
    public void addCommentView(CommentInfo.CommentsBean bean) {
        View view = inflater.inflate(R.layout.comment_detial_item, null);
        setViews(view, bean);
        commentsDetail.addView(view);
    }

    @Override
    public void addCommentNum() {
        commentNum.setNumber(++commentNumber);
    }

    @Override
    public void clear() {
        commentInput.setText("");
        commentLinear.setVisibility(View.GONE);
    }

    @OnClick(R.id.back_image)
    public void onClick() {
        onBackPressed();
    }
}
