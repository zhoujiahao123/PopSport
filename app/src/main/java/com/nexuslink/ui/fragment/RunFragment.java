package com.nexuslink.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nexuslink.R;
import com.nexuslink.ui.activity.RunActivity;

/**
 * Created by 猿人 on 2017/1/15.
 */

public class RunFragment extends Fragment implements View.OnClickListener {
    private Button startBt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.run_fragment,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        startBt = (Button) view.findViewById(R.id.bt_run_start);
        startBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), RunActivity.class);
        startActivity(intent);
    }
}
