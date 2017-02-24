package com.nexuslink.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.presenter.writearticlepresenter.WriteArticlePresenterImpl;
import com.nexuslink.presenter.writearticlepresenter.WriteArtilcePresenter;
import com.nexuslink.ui.adapter.PhotoChooseResultAdapter;
import com.nexuslink.ui.view.WriteArticleView;
import com.nexuslink.ui.view.view.headerview.LoadingView;
import com.nexuslink.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.toolsfinal.BitmapUtils;

public class WriteMsgActivity extends AppCompatActivity implements WriteArticleView {

    //===============================================view
    /**
     * 直到用户停止添加
     */
    //===============================================常量
    private static final String TAG = "WriteMsgActivity";
    private static final int MAX_SIZE = 1024*100;
    //===============================================变亮
    private int MAX_CHOOSE_SIZE = 15;
    private boolean isPosting = false;
    /**
     * presenter
     */
    private WriteArtilcePresenter presenter;
    @BindView(R.id.toolbar_write_msg)
    Toolbar mToolbar;
    @BindView(R.id.msg_input_edit)
    EditText mInput;
    @BindView(R.id.post_article_tv)
    TextView postArticleTv;
    @BindView(R.id.cancel_article_tv)
    TextView cancelArticleTv;
    @BindView(R.id.photos_choose_recycler)
    RecyclerView photosChooseRecycler;
    @BindView(R.id.progressbar)
    LoadingView progressbar;

    //设置文本输入监听,从而达到改变颜色的目的
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                postArticleTv.setTextColor(getResources().getColor(R.color.gray));
                postArticleTv.setClickable(false);
            } else {
                postArticleTv.setTextColor(getResources().getColor(R.color.white));
                postArticleTv.setClickable(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    //数据
    private PhotoChooseResultAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wirte_msg);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade().setDuration(1000));
            getWindow().setExitTransition(new Explode().setDuration(1000));
        }


        presenter = new WriteArticlePresenterImpl(this);
        initDatas();
        initView();

    }

    private void initDatas() {

        adapter = new PhotoChooseResultAdapter(this);
        //设置选择图片监听
        adapter.setOnChoosePhotosListener(new PhotoChooseResultAdapter.onChoosePhotosListener() {
            @Override
            public void onClick() {
                //进行图片的选择
                if (MAX_CHOOSE_SIZE > 0) {
                    GalleryFinal.openGalleryMuti(0, MAX_CHOOSE_SIZE, new GalleryFinal.OnHanlderResultCallback() {
                        @Override
                        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                            MAX_CHOOSE_SIZE -= resultList.size();
                            for (int i = 0; i < resultList.size(); i++) {
                                PhotoInfo info = resultList.get(i);
                                adapter.addItem(BitmapUtils.compressBimap(info.getPhotoPath(),MAX_SIZE),info.getPhotoPath());
                            }
                            //最后进行刷新，提升效率，优化卡顿
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onHanlderFailure(int requestCode, String errorMsg) {
                            ToastUtil.showToast(WriteMsgActivity.this, errorMsg);
                        }
                    });
                } else {
                    ToastUtil.showToast(WriteMsgActivity.this, "选择数量已达上限咯，再发一条试试吧（*＾-＾*）");
                }

            }
        });
    }

    private void initView() {
        //设置toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //设置输入监听
        mInput.addTextChangedListener(textWatcher);
        //reyclerview
        photosChooseRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        photosChooseRecycler.setAdapter(adapter);

    }


    @OnClick({R.id.cancel_article_tv, R.id.post_article_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_article_tv:
                onBackPressed();
                break;
            case R.id.post_article_tv:
                //进行话题的上传，以及回调上一个界面的刷新
                presenter.publishArticle(adapter.getFilesPath());
                isPosting = true;
                cancelArticleTv.setClickable(false);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(!isPosting){
            finish();
        }else{
            ToastUtil.showToast(this,"正在上传，暂时不能退出哦");
        }

    }

    @Override
    public String getInputText() {
        return mInput.getText().toString();
    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.GONE);
        isPosting = false;
    }

    @Override
    public void showSuccess() {
        ToastUtil.showToast(this,"发表成功");
        //这里进行上以界面的刷新
        EventBus.getDefault().post(new String("刷新"));
        onBackPressed();
    }
}
