package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.ui.adapter.ViewImageShowAdapter;
import com.nexuslink.ui.fragment.ViewImageFragment;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewImageShowActivity extends AppCompatActivity implements ViewImageFragment.onPicClickListener {

    public static final String IMAGES_DATA_LIST = "DATA_LIST";
    public static final String IMAGE_NUM = "IMAGE_NUM";
    @BindView(R.id.toolbar_view_show)
    Toolbar mToolbar;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.page)
    TextView page;
    android.support.v7.app.ActionBar actionBar;

    //===============================================数据
    private List<String> data;
    private int position;
    private int dataLength = 0;
    private List<ViewImageFragment> fragments = new ArrayList<>();
    //===============================================辅助变量
    private TimeCount timeCount = new TimeCount(1000,1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_image_show);
        ButterKnife.bind(this);
        setUpView();

    }


    private void setUpView() {

        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);


        data = (List<String>) getIntent().getSerializableExtra(IMAGES_DATA_LIST);
        position = getIntent().getIntExtra(IMAGE_NUM, -1);
        dataLength = data.size();

        for (int i = 0; i < dataLength; i++) {
            ViewImageFragment fragment = new ViewImageFragment();
            Bundle arg = new Bundle();
            arg.putString(Constants.IMAGE_URL, data.get(i));
            arg.putInt(Constants.IMAGE_POS, i);
            fragment.setArguments(arg);
            fragment.setOnPickClickListener(this);
            fragments.add(fragment);
        }
        mViewPager.setAdapter(new ViewImageShowAdapter(getSupportFragmentManager(), fragments));
        mViewPager.setCurrentItem(position);
        page.setText(position + 1 + "/" + dataLength);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                page.setText(mViewPager.getCurrentItem() + 1 + "/" + dataLength);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        timeCount.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_show_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save_image) {
            fragments.get(mViewPager.getCurrentItem()).saveImage();
        }else if(id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean isShow = true;
    @Override
    public void onPicClick() {
        if(!isShow){
            //消失状态，在这里进行显示
            showToolbar();
        }else{
            //显示状态，在这里退出
            onBackPressed();
        }
    }

    /**
     * toolbar消失动画
     */
    private void hideToolbar(){
        ObjectAnimator.ofFloat(mToolbar,"Alpha",100f,0f).setDuration(2000).start();
        isShow = false;
    }
    /**
     * toolbar 显示动画
     */
    private void showToolbar(){
        ObjectAnimator.ofFloat(mToolbar,"Alpha",0f,100f).setDuration(100).start();
        isShow = true;
        timeCount.start();
    }


    /**
     * 计时类
     */
    class TimeCount extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            hideToolbar();
        }
    }
}
