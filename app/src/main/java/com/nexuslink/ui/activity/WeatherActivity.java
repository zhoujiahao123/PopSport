package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nexuslink.app.BaseActivity;
import com.nexuslink.model.data.WeatherInfo;
import com.nexuslink.ui.view.WeatherView;

/**
 * Created by ASUS-NB on 2017/1/17.
 */

public class WeatherActivity extends BaseActivity implements WeatherView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showWeather(WeatherInfo weatherInfo) {

    }
}
