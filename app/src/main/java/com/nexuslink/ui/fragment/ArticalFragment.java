package com.nexuslink.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.nexuslink.R;
import com.nexuslink.app.BaseFragment;
import com.nexuslink.config.Constants;
import com.nexuslink.model.articalModel.AriticalModelImpl;
import com.nexuslink.model.data.AticalInfo;
import com.nexuslink.presenter.articalpresenter.ArticalPresenter;
import com.nexuslink.presenter.articalpresenter.ArticalPresenterImpl;
import com.nexuslink.ui.view.ArticalView;
import com.nexuslink.util.IdUtil;
import com.nexuslink.util.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS-NB on 2017/2/25.
 */

public class ArticalFragment extends BaseFragment implements ArticalView {

    @BindView(R.id.artical_image)
    ImageView articalImage;
    @BindView(R.id.artical_text)
    TextView articalText;
    @BindView(R.id.container)
    RelativeLayout container;
    int uId;
    ArticalPresenter presenter;
    public static ArticalFragment newInstance(int uId) {
        ArticalFragment fragment = new ArticalFragment();
        Bundle args = new Bundle();
        args.putInt("uId",uId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_atical, container, false);
        ButterKnife.bind(this, view);
        presenter = new ArticalPresenterImpl(new AriticalModelImpl(),this);
        uId = getArguments().getInt("uId");
        XLog.e("这个uid是"+uId);
        getArtical((int) IdUtil.getuId(),uId);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getArtical(int uId, int writeId) {
        presenter.getArtical(uId,writeId);
    }

    @Override
    public void showArtical(AticalInfo info) {
        XLog.e("正在展示文章");
        ImageUtil.imageDisplayHeadImage(Constants.PHOTO_BASE_URL+info.getArticles().get(0).getImages().get(0),articalImage);
        articalText.setText(info.getArticles().get(0).getText());
    }
}
