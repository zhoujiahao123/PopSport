package com.nexuslink.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.ui.adapter.ViewImageShowAdapter;
import com.nexuslink.ui.fragment.ViewImageFragment;
import com.nexuslink.util.SaveImageListener;
import com.nexuslink.util.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewImageShowActivity extends AppCompatActivity implements SaveImageListener {

    public static final String IMAGES_DATA_LIST = "DATA_LIST";
    public static final String IMAGE_NUM = "IMAGE_NUM";
    @BindView(R.id.toolbar_view_show)
    Toolbar mToolbar;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.page)
    TextView page;
    @BindView(R.id.back_icon)
    ImageView backIcon;

    private List<String> data;
    private int position;
    private int dataLength = 0;
    private List<Fragment> fragments = new ArrayList<>();
    private Map<Integer, Bitmap> imagesMap = new HashMap<>();

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


        data = (List<String>) getIntent().getSerializableExtra(IMAGES_DATA_LIST);
        position = getIntent().getIntExtra(IMAGE_NUM, -1);
        dataLength = data.size();

        for (int i = 0; i < dataLength; i++) {
            ViewImageFragment fragment = new ViewImageFragment();
            Bundle arg = new Bundle();
            arg.putString(Constants.IMAGE_URL, data.get(i));
            arg.putInt(Constants.IMAGE_POS, i);
            fragment.setArguments(arg);
            //增加监听
            fragment.setSaveImageListener(this);
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_show_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean isSave = false;
        if (id == R.id.save_image) {
            Bitmap bitmap = imagesMap.get(mViewPager.getCurrentItem());
            if (bitmap != null) {
                //进行文件的保存
                isSave = saveImageToGallery(this, bitmap);
                if (isSave) {
                    ToastUtil.showToast(this, "图片保存成功");
                }
            } else {
                ToastUtil.showToast(this, "加载失败,无法保存");
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
        //小米手机必须这样获得public绝对路径
        String filesName = "Pop图片";
        File appDir = new File(file, filesName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File currentFile = new File(appDir, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
//         其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    currentFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(currentFile.getPath()))));
        return true;
    }

    @Override
    public void onLoadSuccess(int pos, Bitmap bitmap) {
        imagesMap.put(pos, bitmap);
    }

    @Override
    public void onLoadFailed(String str) {
        ToastUtil.showToast(this, str);
    }

    @OnClick(R.id.back_icon)
    public void onClick() {
        finish();
    }
}
