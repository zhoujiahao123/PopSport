package com.nexuslink.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.nexuslink.DaoSession;
import com.nexuslink.R;
import com.nexuslink.Run;
import com.nexuslink.RunDao;
import com.nexuslink.User;
import com.nexuslink.UserDao;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.config.Constants;
import com.nexuslink.model.achievementmodel.OnCallBackListener;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.util.IdUtil;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ASUS-NB on 2017/2/13.
 */

public class AchievementRecyclerViewAdapter extends RecyclerView.Adapter<AchievementRecyclerViewAdapter.MyViewHolder> {
    private UserDao userDao = BaseApplication.getDaosession().getUserDao();
    private int image[]={R.drawable.km3,R.drawable.km5,R.drawable.km10,R.drawable.km50,R.drawable.km_ban,R.drawable.km_quan,
    R.drawable.km_gava,R.drawable.km_get};
    private int imageg[]={R.drawable.km3g,R.drawable.km5g,R.drawable.km10g,R.drawable.km50g,R.drawable.km_bang,R.drawable.km_quang,
            R.drawable.km_gaveg,R.drawable.km_getg};
    private int[] displayImage={R.drawable.km3g,R.drawable.km5g,R.drawable.km10g,R.drawable.km50g,R.drawable.km_bang,R.drawable.km_quang,
            R.drawable.km_gaveg,R.drawable.km_getg};
    private String textContent[]={"跑步总里程达3公里","跑步总里程达5公里","跑步总里程达10公里","跑步总里程达50公里","一次性跑步21.09公里"
    ,"一次性跑步42.16公里","关注达到100次","粉丝达到100位"};
    private String textTitle[]={"突破三公里","突破五公里","突破十公里","跑步小能手","突破半马","突破全马","我爱人人","人人爱我"};
    private Context mContext;
    OkHttpClient okHttpClient= new OkHttpClient();
    public AchievementRecyclerViewAdapter(Context context,OnCallBackListener listener) {
        mContext = context;
        checkAchieve(listener);
    }

    private void checkAchieve(final OnCallBackListener listener) {
        RequestBody body =new FormBody.Builder().add("uId", String.valueOf(IdUtil.getuId())).build();
        Request request = new Request.Builder().post(body).url(Constants.BASE_URL+"user/getInfo").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content = response.body().string();
                Gson gson = new Gson();
                UserInfo userInfo = gson.fromJson(content,UserInfo.class);
                UserDao userDao = BaseApplication.getDaosession().getUserDao();
                User user = userDao.queryBuilder().where(UserDao.Properties.Already.eq(1)).unique();
                String achievement = Arrays.toString(userInfo.getUser().getUAchievements());
                String achievementImageString = achievement.substring(1,achievement.length()-1);
                String[] achievementImage =achievementImageString.split(",");
                XLog.e(achievementImage);
                user.setUAchievements(achievementImageString);
                userDao.update(user);
                for(int i=0;i<8;i++){
                    if(achievementImage[i].equals("false")){
                        displayImage[i]=imageg[i];
                    }else {
                        displayImage[i]=image[i];
                    }
                }
                if(userInfo.getUser().getUFansNum()>=100){
                    displayImage[7] = image[7];
                }
                listener.onSucceed();
            }
        });
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
        holder.textTitle.setText(textTitle[position]);
        holder.textContent.setText(textContent[position]);
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        TextView textTitle,textContent;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            textContent = (TextView) itemView.findViewById(R.id.text_content);
        }

    }

}
