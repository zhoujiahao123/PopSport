package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.nexuslink.R;

/**
 * Created by 猿人 on 2017/4/8.
 */

public class AchievementRecyclerAdapter extends RecyclerArrayAdapter<Boolean> {

    private Context mContext;

    public AchievementRecyclerAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new AchievementViewHolder(parent, R.layout.achievement_item);
    }

    class AchievementViewHolder extends BaseViewHolder<Boolean> {
        /**
         * 数据
         */
        private int image[] = {R.drawable.km3, R.drawable.km5, R.drawable.km10, R.drawable.km50, R.drawable.km_ban, R.drawable.km_quan,
                R.drawable.km_gava, R.drawable.km_get};
        private int unimagg[] = {R.drawable.km3g, R.drawable.km5g, R.drawable.km10g, R.drawable.km50g, R.drawable.km_bang, R.drawable.km_quang,
                R.drawable.km_gaveg, R.drawable.km_getg};
        private String textTitle[] = {"突破三公里", "突破五公里", "突破十公里", "跑步小能手", "突破半马", "突破全马", "我爱人人", "人人爱我"};
        //辅助变量

        /**
         * view
         */
        private TextView achieveTitle;
        private ImageView achieveImage;

        public AchievementViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            achieveImage = $(R.id.achieve_image);
            achieveTitle = $(R.id.achieve_title);
        }

        @Override
        public void setData(Boolean data) {
            super.setData(data);
            if (data == false) {
                achieveImage.setImageDrawable(mContext.getDrawable(unimagg[getLayoutPosition()]));
            } else {
                achieveImage.setImageDrawable(mContext.getDrawable(image[getLayoutPosition()]));
            }
            Log.i("data", "data:" + data);
            achieveTitle.setText(textTitle[getLayoutPosition()]);
        }
    }
}
