package com.nexuslink.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.app.BaseActivity;
import com.nexuslink.model.data.WeatherInfo;
import com.nexuslink.presenter.weatherpresenter.WeatherPresenter;
import com.nexuslink.presenter.weatherpresenter.WeatherPresenterImpl;
import com.nexuslink.ui.view.WeatherView;
import com.nexuslink.util.BlurDrawable;
import com.nexuslink.util.MyScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ASUS-NB on 2017/1/17.
 */

public class WeatherActivity extends BaseActivity implements WeatherView, MyScrollView.OnScrollListener {

    @BindView(R.id.turn_back)
    ImageView turnBack;
    @BindView(R.id.city_name)
    TextView cityName;
    @BindView(R.id.air_quality_image)
    ImageView airQualityImage;
    @BindView(R.id.air_quality_text)
    TextView airQualityText;
    @BindView(R.id.weather_des)
    TextView weatherDes;
    @BindView(R.id.wind_degree)
    TextView windDegree;
    @BindView(R.id.remind_des)
    TextView remindDes;
    @BindView(R.id.air_quality_left)
    TextView airQualityLeft;
    @BindView(R.id.temp)
    TextView temp;
    @BindView(R.id.information_text_left)
    TextView informationTextLeft;
    @BindView(R.id.information_image_left)
    ImageView informationImageLeft;
    @BindView(R.id.left)
    RelativeLayout left;
    @BindView(R.id.air_quality_right)
    TextView airQualityRight;
    @BindView(R.id.temp_right)
    TextView tempRight;
    @BindView(R.id.information_text_right)
    TextView informationTextRight;
    @BindView(R.id.information_image_right)
    ImageView informationImageRight;
    @BindView(R.id.right)
    RelativeLayout right;
    @BindView(R.id.weather_temp)
    ImageView weatherTemp;
    @BindView(R.id.weather_temp_left)
    ImageView weatherTempLeft;
    @BindView(R.id.air_quality)
    LinearLayout airQuality;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.forecast_day_1)
    TextView forecastDay1;
    @BindView(R.id.forecast_day_2)
    TextView forecastDay2;
    @BindView(R.id.forecast_day_3)
    TextView forecastDay3;
    @BindView(R.id.forecast_day_4)
    TextView forecastDay4;
    @BindView(R.id.forecast_day_5)
    TextView forecastDay5;
    @BindView(R.id.forecast_image_1)
    ImageView forecastImage1;
    @BindView(R.id.forecast_image_2)
    ImageView forecastImage2;
    @BindView(R.id.forecast_image_3)
    ImageView forecastImage3;
    @BindView(R.id.forecast_image_4)
    ImageView forecastImage4;
    @BindView(R.id.forecast_image_5)
    ImageView forecastImage5;
    @BindView(R.id.forecast_weather1)
    TextView forecastWeather1;
    @BindView(R.id.forecast_weather2)
    TextView forecastWeather2;
    @BindView(R.id.forecast_weather3)
    TextView forecastWeather3;
    @BindView(R.id.forecast_weather4)
    TextView forecastWeather4;
    @BindView(R.id.forecast_weather5)
    TextView forecastWeather5;
    @BindView(R.id.forecast_temp_1)
    TextView forecastTemp1;
    @BindView(R.id.forecast_temp_2)
    TextView forecastTemp2;
    @BindView(R.id.forecast_temp_3)
    TextView forecastTemp3;
    @BindView(R.id.forecast_temp_4)
    TextView forecastTemp4;
    @BindView(R.id.forecast_temp_5)
    TextView forecastTemp5;
    @BindView(R.id.weather_life_des_sport)
    TextView weatherLifeDesSport;
    @BindView(R.id.weather_life_des_cloth)
    TextView weatherLifeDesCloth;
    @BindView(R.id.weather_life_des_cold)
    TextView weatherLifeDesCold;
    @BindView(R.id.weather_life_des_air)
    TextView weatherLifeDesAir;
    @BindView(R.id.weather_life_des_wash_car)
    TextView weatherLifeDesWashCar;
    @BindView(R.id.weather_life_des_ray)
    TextView weatherLifeDesRay;
    private WeatherPresenter weatherPresenter;
    //一个imageview作为整天背景
    private ImageView backgroundImage;
    //背景图片的ID
    int backgroundId;
    //判断网络是否存在
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null||!networkInfo.isAvailable()){
            setContentView(R.layout.weather_activity_failed);
        }else {
            setContentView(R.layout.weather_activity);
            ButterKnife.bind(this);
            setView();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showWeather(WeatherInfo weatherInfo) {
        //设置空气质量指数
        airQualityText.setText(weatherInfo.getResult().getData().getPm25().getPm25().getCurPm() + " " + weatherInfo.getResult()
                .getData().getPm25().getPm25().getQuality());
        //设置空气质量图片
        setAirQualityImage(weatherInfo);
        //设置今日提醒话语
        remindDes.setText(weatherInfo.getResult().getData().getPm25().getPm25().getDes());
        //设置天气状态
        weatherDes.setText(weatherInfo.getResult().getData().getRealtime().getWeather().getInfo());
        //设置风力级数
        windDegree.setText(weatherInfo.getResult().getData().getRealtime().getWind().getPower());
        //设置湿度等
        airQualityLeft.setText(weatherInfo.getResult().getData().getWeather().get(0).getInfo().getDay().get(4));
        airQualityRight.setText(weatherInfo.getResult().getData().getWeather().get(1).getInfo().getDay().get(4));
        //设置温度区间
        temp.setText(weatherInfo.getResult().getData().getWeather().get(0).getInfo().getNight().get(2) + "~" +
                weatherInfo.getResult().getData().getWeather().get(0).getInfo().getDay().get(2)+"°C");
        tempRight.setText(weatherInfo.getResult().getData().getWeather().get(1).getInfo().getNight().get(2) + "~" +
                weatherInfo.getResult().getData().getWeather().get(1).getInfo().getDay().get(2)+"°C");
        //设置多云转晴等
        informationTextLeft.setText(weatherInfo.getResult().getData().getWeather().get(0).getInfo().getDay().get(1));
        informationTextRight.setText(weatherInfo.getResult().getData().getWeather().get(1).getInfo().getDay().get(1));
        //设置图片
        setWeatherImage(weatherInfo, 0, informationImageLeft);
        setWeatherImage(weatherInfo, 1, informationImageRight);
        setWeatherTempImage(weatherInfo);
        setForecastDay(weatherInfo);
        setWeatherImageForecast(weatherInfo, 0, forecastImage1);
        setWeatherImageForecast(weatherInfo, 1, forecastImage2);
        setWeatherImageForecast(weatherInfo, 2, forecastImage3);
        setWeatherImageForecast(weatherInfo, 3, forecastImage4);
        setWeatherImageForecast(weatherInfo, 4, forecastImage5);
        setForecastWeather(weatherInfo, forecastWeather1, 0);
        setForecastWeather(weatherInfo, forecastWeather2, 1);
        setForecastWeather(weatherInfo, forecastWeather3, 2);
        setForecastWeather(weatherInfo, forecastWeather4, 3);
        setForecastWeather(weatherInfo, forecastWeather5, 4);
        setForecastTemp(weatherInfo, forecastTemp1, 0);
        setForecastTemp(weatherInfo, forecastTemp2, 1);
        setForecastTemp(weatherInfo, forecastTemp3, 2);
        setForecastTemp(weatherInfo, forecastTemp4, 3);
        setForecastTemp(weatherInfo, forecastTemp5, 4);
        setWeatherLifeDes(weatherInfo);
    }

    /**
     * 设置生活指数
     */
    private void setWeatherLifeDes(WeatherInfo weatherInfo){
        weatherLifeDesSport.setText(weatherInfo.getResult().getData().getLife().getInfo().getYundong().get(0));
        weatherLifeDesAir.setText(weatherInfo.getResult().getData().getLife().getInfo().getKongtiao().get(0));
        weatherLifeDesCloth.setText(weatherInfo.getResult().getData().getLife().getInfo().getChuanyi().get(0));
        weatherLifeDesCold.setText(weatherInfo.getResult().getData().getLife().getInfo().getGanmao().get(0));
        weatherLifeDesRay.setText(weatherInfo.getResult().getData().getLife().getInfo().getZiwaixian().get(0));
        weatherLifeDesWashCar.setText(weatherInfo.getResult().getData().getLife().getInfo().getXiche().get(0));
    }
    /**
     * 设置预报的天气温度
     */
    private void setForecastTemp(WeatherInfo weatherInfo, TextView textView, int i) {
        textView.setText(weatherInfo.getResult().getData().getWeather().get(i).getInfo().getNight().get(2) + "~" +
                weatherInfo.getResult().getData().getWeather().get(i).getInfo().getDay().get(2)+"°C");
    }

    /**
     * 设置预报的天气情况如（阴，晴）等
     */
    private void setForecastWeather(WeatherInfo weatherInfo, TextView textView, int i) {
        textView.setText(weatherInfo.getResult().getData().getWeather().get(i).getInfo().getDay().get(1));
    }

    /**
     *
     */
    private void setForecastDay(WeatherInfo weatherInfo) {
        forecastDay3.setText(String.format("周%s", weatherInfo.getResult().getData().getWeather().get(2).getWeek()));
        forecastDay4.setText(String.format("周%s", weatherInfo.getResult().getData().getWeather().get(3).getWeek()));
        forecastDay5.setText(String.format("周%s", weatherInfo.getResult().getData().getWeather().get(4).getWeek()));
    }

    /**
     * 设置温度的图片
     */
    private void setWeatherTempImage(WeatherInfo weatherInfo) {
        int i, j;
        i = Integer.valueOf(weatherInfo.getResult().getData().getRealtime().getWeather().getTemperature()) / 10;
        j = Integer.valueOf(weatherInfo.getResult().getData().getRealtime().getWeather().getTemperature()) % 10;
        if (i != 0) {
            switch (i) {
                case 1:
                    weatherTempLeft.setImageResource(R.drawable.digit_1);
                    break;
                case 2:
                    weatherTempLeft.setImageResource(R.drawable.digit_2);
                    break;
                case 3:
                    weatherTempLeft.setImageResource(R.drawable.digit_3);
                    break;
                case 4:
                    weatherTempLeft.setImageResource(R.drawable.digit_4);
                    break;
                default:
                    new Exception("该温度并不正常");
                    break;
            }
        }
        switch (j) {
            case 0:
                weatherTemp.setImageResource(R.drawable.digit_0);
                break;
            case 1:
                weatherTemp.setImageResource(R.drawable.digit_1);
                break;
            case 2:
                weatherTemp.setImageResource(R.drawable.digit_2);
                break;
            case 3:
                weatherTemp.setImageResource(R.drawable.digit_3);
                break;
            case 4:
                weatherTemp.setImageResource(R.drawable.digit_4);
                break;
            case 5:
                weatherTemp.setImageResource(R.drawable.digit_5);
                break;
            case 6:
                weatherTemp.setImageResource(R.drawable.digit_6);
                break;
            case 7:
                weatherTemp.setImageResource(R.drawable.digit_7);
                break;
            case 8:
                weatherTemp.setImageResource(R.drawable.digit_8);
                break;
            case 9:
                weatherTemp.setImageResource(R.drawable.digit_9);
                break;
            default:
                new Exception("该温度并不正常");
                break;

        }
    }

    /**
     * 设置天气图片小图标
     */
    private void setWeatherImage(WeatherInfo weatherInfo, int i, ImageView imageView) {
        if (weatherInfo.getResult().getData().getWeather().get(i).getInfo().getDay().get(1).equals("晴")) {
            imageView.setImageResource(R.drawable.ic_sunny);
            backgroundId = R.drawable.bg_fine_day;
            container.setBackgroundResource(R.drawable.bg_fine_day);
        } else if (weatherInfo.getResult().getData().getWeather().get(i).getInfo().getDay().get(1).equals("多云")) {
            imageView.setImageResource(R.drawable.ic_cloudy);
            backgroundId = R.drawable.bg_cloudy_day;
            container.setBackgroundResource(R.drawable.bg_cloudy_day);
        } else if (weatherInfo.getResult().getData().getWeather().get(i).getInfo().getDay().get(1).equals("阴")) {
            imageView.setImageResource(R.drawable.ic_overcast);
            backgroundId = R.drawable.bg_overcast;
            container.setBackgroundResource(R.drawable.bg_overcast);
        } else if (weatherInfo.getResult().getData().getWeather().get(i).getInfo().getDay().get(1).equals("小雨")) {
            imageView.setImageResource(R.drawable.ic_littlerain);
            backgroundId = R.drawable.bg_rain;
            container.setBackgroundResource(R.drawable.bg_rain);
        } else if (weatherInfo.getResult().getData().getWeather().get(i).getInfo().getDay().get(1).equals("中雨")) {
            imageView.setImageResource(R.drawable.ic_midrain);
            backgroundId = R.drawable.bg_rain;
            container.setBackgroundResource(R.drawable.bg_rain);
        } else if (weatherInfo.getResult().getData().getWeather().get(i).getInfo().getDay().get(1).equals("阵雨")) {
            imageView.setImageResource(R.drawable.ic_bigrain);
            backgroundId = R.drawable.bg_rain;
            container.setBackgroundResource(R.drawable.bg_rain);
        }
    }

    /**
     *
     */
    private void setWeatherImageForecast(WeatherInfo weatherInfo, int i, ImageView imageView) {
        if (weatherInfo.getResult().getData().getWeather().get(i).getInfo().getDay().get(1).equals("晴")) {
            imageView.setImageResource(R.drawable.ic_sunny);
        } else if (weatherInfo.getResult().getData().getWeather().get(i).getInfo().getDay().get(1).equals("多云")) {
            imageView.setImageResource(R.drawable.ic_cloudy);
        } else if (weatherInfo.getResult().getData().getWeather().get(i).getInfo().getDay().get(1).equals("阴")) {
            imageView.setImageResource(R.drawable.ic_overcast);
        } else if (weatherInfo.getResult().getData().getWeather().get(i).getInfo().getDay().get(1).equals("小雨")) {
            imageView.setImageResource(R.drawable.ic_littlerain);
        } else if (weatherInfo.getResult().getData().getWeather().get(i).getInfo().getDay().get(1).equals("中雨")) {
            imageView.setImageResource(R.drawable.ic_midrain);
        } else if (weatherInfo.getResult().getData().getWeather().get(i).getInfo().getDay().get(1).equals("阵雨")) {
            imageView.setImageResource(R.drawable.ic_bigrain);
        }
    }

    /**
     * 设置空气质量的叶子图片颜色
     *
     * @param weatherInfo
     */
    private void setAirQualityImage(WeatherInfo weatherInfo) {
        if (weatherInfo.getResult().getData().getPm25().getPm25().getQuality().equals("良")) {
            airQualityImage.setImageResource(R.drawable.ic_pm25_02);
        } else if (weatherInfo.getResult().getData().getPm25().getPm25().getQuality().equals("优秀")) {
            airQualityImage.setImageResource(R.drawable.ic_pm25_01);
        } else if (weatherInfo.getResult().getData().getPm25().getPm25().getQuality().equals("轻度污染")) {
            airQualityImage.setImageResource(R.drawable.ic_pm25_03);
        }
    }

    /**
     * 设置一些控件等
     */
    private void setView() {
        weatherPresenter = new WeatherPresenterImpl(this);
        weatherPresenter.getWeatherInfo("重庆");
        left.getBackground().setAlpha(20);
        right.getBackground().setAlpha(20);
        airQuality.getBackground().setAlpha(50);
        MyScrollView.setScrollListener(this);
        backgroundImage = new ImageView(this);
    }

    @OnClick(R.id.turn_back)
    public void onClick() {
        onBackPressed();
    }

    /**
     * 模糊效果的实现
     */
    private void doBlur(float i) {
        final Bitmap bp = BitmapFactory.decodeResource(getResources(), backgroundId);
        final BlurDrawable blurDrawable = new BlurDrawable(this, getResources(), bp);
        int alpha = (int) (i);
        if (alpha > 255) {
            alpha = 255;
        } else if (alpha < 0) {
            alpha = 0;
        }
        Log.e("TAG",alpha+"");
        blurDrawable.setBlur(alpha);
        backgroundImage.setImageDrawable(blurDrawable.getBlurDrawable());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            container.setBackground(backgroundImage.getDrawable());
        }
    }

    @Override
    public void onChangeBackground(float scaleRadio) {
        doBlur(scaleRadio);
    }
}
