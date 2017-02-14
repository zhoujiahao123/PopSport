package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nexuslink.R;

/**
 * Created by ASUS-NB on 2017/2/13.
 */

public class AchievementRecyclerViewAdapter extends RecyclerView.Adapter<AchievementRecyclerViewAdapter.MyViewHolder> {
    private int image[]={R.drawable.km3,R.drawable.km5,R.drawable.km10,R.drawable.km50,R.drawable.km100,R.drawable.km_ban,R.drawable.km_quan,
    R.drawable.km_gava,R.drawable.km_get};
    private int imageg[]={R.drawable.km3g,R.drawable.km5g,R.drawable.km10g,R.drawable.km50g,R.drawable.km100g,R.drawable.km_bang,R.drawable.km_quang,
            R.drawable.km_gaveg,R.drawable.km_getg};
    private Context mContext;
    public AchievementRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_achievement,parent,false);
        MyViewHolder h = new MyViewHolder(view);
        return h;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.imageView.setImageResource(imageg[position]);
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }

    }

}
