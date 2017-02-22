package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nexuslink.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WriteMsgActivity extends AppCompatActivity {

    //===============================================view
    /**
     * 这里简述加载图片思路，当第一行图片超过四个，包括添加按钮，那么就新增一个LinearLayout，在这个新的LinearLayout中有开始添加imageView
     * 直到用户停止添加
     */
    @BindView(R.id.toolbar_write_msg)
    Toolbar mToolbar;
    @BindView(R.id.msg_input_edit)
    EditText mInput;
    @BindView(R.id.add_image)
    ImageView mAddImage;
    @BindView(R.id.write_images_linear)
    LinearLayout mImagesLinears;
    @BindView(R.id.image_linear1)
    LinearLayout mImageLinear1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wirte_msg);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write_msg_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.post){
            //这里进行话题上传并进行，回调，可以使用EventBus框架，也可以用startActivityForResult方法来进行回调

        }
        return super.onOptionsItemSelected(item);
    }
}
