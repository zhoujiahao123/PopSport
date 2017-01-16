package com.nexuslink.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.EditText;

import com.nexuslink.R;
import com.nexuslink.app.BaseActivity;
import com.nexuslink.ui.view.FriendView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS-NB on 2017/1/14.
 */

public class FriendActivity extends BaseActivity implements FriendView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_searchfriend)
    EditText edSearchfriend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void searchUser() {

    }

    @Override
    public void showfragment() {

    }

    private void initView(){
    }
}
