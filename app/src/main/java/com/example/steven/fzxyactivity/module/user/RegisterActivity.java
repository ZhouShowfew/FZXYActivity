package com.example.steven.fzxyactivity.module.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.steven.fzxyactivity.Constant.Constants;
import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.common.util.LogUtil;
import com.example.steven.fzxyactivity.common.util.OkUtils;
import com.example.steven.fzxyactivity.common.util.ToastUtil;
import com.example.steven.fzxyactivity.materialdesign.views.ButtonRectangle;
import com.example.steven.fzxyactivity.module.main.View.Fragment.Activity.BottomMainActivity;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.et_ID)
    AppCompatEditText etID;
    @Bind(R.id.et_name)
    AppCompatEditText etName;
    @Bind(R.id.et_phoneNum)
    AppCompatEditText etPhoneNum;
    @Bind(R.id.et_email)
    AppCompatEditText etEmail;
    @Bind(R.id.et_password)
    AppCompatEditText etPassword;
    @Bind(R.id.btn_register)
    ButtonRectangle btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        toolbar.setTitle("注册");
        toolbar.setSubtitle("填写您的信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @OnClick(R.id.btn_register)
    public void setBtnRegister(){
        RegisterByServer();
        ToastUtil.toast("提交注册");
        startActivity(new Intent(RegisterActivity.this, BottomMainActivity.class));
        finish();
    }

    private void RegisterByServer() {
        String url = Constants.ServerUrl + "user/reg";
        Map<String, String> map = new HashMap<>();
        map.put("userId", etID.getText().toString());
        map.put("userName", etName.getText().toString());
        map.put("userPwd", etPassword.getText().toString());
        map.put("userMobile", etPhoneNum.getText().toString());
        map.put("userEmail", etEmail.getText().toString());
        OkUtils.post(url, map, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                LogUtil.log(response);
            }
        });
    }
}
