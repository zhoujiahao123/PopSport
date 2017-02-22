package com.nexuslink.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.nexuslink.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ASUS-NB on 2017/2/14.
 */

public class AlterPasswordDialog extends AlertDialog {
    @BindView(R.id.old_password)
    TextInputEditText oldPassword;
    @BindView(R.id.new_password)
    TextInputEditText newPassword;
    @BindView(R.id.new_password_again)
    TextInputEditText newPasswordAgain;
    @BindView(R.id.right)
    Button right;
    @BindView(R.id.left)
    Button left;
    public interface OnRightClickListener{
        void onClicked(String oldPassword,String newPassword);

        void onDismiss();
    }
    OnRightClickListener listener;
    public void setOnRightClickListener(OnRightClickListener listener){
        this.listener = listener;
    }
    public AlterPasswordDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_password);
        ButterKnife.bind(this);
        addChangeListener();
    }
    private void addChangeListener(){
        oldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(oldPassword.getText().equals("")||newPassword.getText().equals("")||newPasswordAgain.getText().equals("")){
                    right.setEnabled(false);
                }else {
                    right.setEnabled(true);
                }
            }
        });
    }

    @OnClick({R.id.right, R.id.left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right:
                listener.onClicked(oldPassword.getText().toString(),newPassword.getText().toString());
                break;
            case R.id.left:
                listener.onDismiss();
                break;
        }
    }

}
