package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.litao.android.lib.BaseGalleryActivity;
import com.litao.android.lib.Configuration;
import com.litao.android.lib.entity.PhotoEntry;
import com.nexuslink.R;
import com.nexuslink.model.data.EventEntry;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ASUS-NB on 2017/2/10.
 */

public class AlterPhotoActivity extends BaseGalleryActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        ButterKnife.bind(this);
        setTitle("Photo");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        attachFragment(R.id.gallery_root);
    }

    @Override
    public Configuration getConfiguration() {
        Configuration cfg = new Configuration.Builder()
                .hasCamera(true)
                .hasShade(true)
                .hasPreview(true)
                .setSpaceSize(3)
                .setPhotoMaxWidth(120)
                .setCheckBoxColor(0xFF3F51B5)
                .setDialogHeight(Configuration.DIALOG_HALF)
                .setDialogMode(Configuration.DIALOG_GRID)
                .setMaximum(1)
                .setTip(null)
                .setAblumsTitle(null)
                .build();
        return cfg;
    }

    @Override
    public List<PhotoEntry> getSelectPhotos() {
        return null;
    }

    @Override
    public void onSelectedCountChanged(int i) {

    }

    @Override
    public void onAlbumChanged(String s) {
        getSupportActionBar().setSubtitle(s);
    }

    @Override
    public void onTakePhoto(PhotoEntry photoEntry) {
        EventBus.getDefault().post(photoEntry);
        finish();
    }

    @Override
    public void onChoosePhotos(List<PhotoEntry> list) {
        EventBus.getDefault().post(new EventEntry(list));
        finish();
    }

    @Override
    public void onPhotoClick(PhotoEntry photoEntry) {

    }

    @OnClick({R.id.send_photos,R.id.album})
    public void onClick(View view) {
        if(view.getId()==R.id.send_photos){
            sendPhotos();
        }else if(view.getId()==R.id.album){
            openAlbum();
        }

    }

}
