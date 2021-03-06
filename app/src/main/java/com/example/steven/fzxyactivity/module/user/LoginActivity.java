package com.example.steven.fzxyactivity.module.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.steven.fzxyactivity.App;
import com.example.steven.fzxyactivity.Constant.Constants;
import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.common.util.LogUtil;
import com.example.steven.fzxyactivity.common.util.OkUtils;
import com.example.steven.fzxyactivity.common.util.SpUtils;
import com.example.steven.fzxyactivity.common.util.ToastUtil;
import com.example.steven.fzxyactivity.materialdesign.views.ButtonRectangle;
import com.example.steven.fzxyactivity.module.BaseActivity;
import com.example.steven.fzxyactivity.module.main.View.Fragment.Activity.BottomMainActivity;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_accunt)
    AppCompatEditText etAccunt;
    @Bind(R.id.et_password)
    AppCompatEditText etPassword;
    @Bind(R.id.btn_login)
    ButtonRectangle btnLogin;
    @Bind(R.id.btn_register)
    ButtonRectangle btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setListener();
    }


    private void setListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginByServer();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), 1001);
            }
        });
    }

    private void loginByServer() {
        String url = Constants.ServerUrl + "user/login";
        Map<String, String> map = new HashMap<>();
        map.put("uid", etAccunt.getText().toString());
        map.put("pwd", etPassword.getText().toString());
        OkUtils.post(url, map, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject jsonObject=new JSONObject(response);
                    ToastUtil.toast(jsonObject.getString("msg"));
                    if (jsonObject.getString("code").equals("1")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
//                                    if (!TextUtils.isEmpty(jsonObject.getString("address")))
//                                    SpUtils.putString(App.getApp(), "address", jsonObject.getString("address"));
//                                    if (!TextUtils.isEmpty(jsonObject.getString("birthday")))
//                                    SpUtils.putString(App.getApp(), "birthday", jsonObject.getString("birthday"));
                                    if (!TextUtils.isEmpty(jsonObject.getString("email")))
                                    SpUtils.putString(App.getApp(), "email", jsonObject.getString("email"));
                                    if (!TextUtils.isEmpty(jsonObject.getString("mobile")))
                                    SpUtils.putString(App.getApp(), "mobile", jsonObject.getString("mobile"));
//                                    if (!TextUtils.isEmpty(jsonObject.getString("photo")))
//                                    SpUtils.putString(App.getApp(), "photo", jsonObject.getString("photo"));
//                                    if (!TextUtils.isEmpty(jsonObject.getString("post")))
//                                    SpUtils.putString(App.getApp(), "post", jsonObject.getString("post"));
//                                    if (!TextUtils.isEmpty(jsonObject.getString("sex")))
//                                    SpUtils.putString(App.getApp(), "sex", jsonObject.getString("sex"));
                                    if (!TextUtils.isEmpty(jsonObject.getString("userId")))
                                    SpUtils.putString(App.getApp(), "userId",jsonObject.getString("userId"));
                                    if (!TextUtils.isEmpty(jsonObject.getString("userName")))
                                    SpUtils.putString(App.getApp(), "userName", jsonObject.getString("userName"));
                                    if (!TextUtils.isEmpty(jsonObject.getString("userPwd")))
                                    SpUtils.putString(App.getApp(), "userPwd", jsonObject.getString("userPwd"));
                                    if (!TextUtils.isEmpty(jsonObject.getString("token")))
                                        SpUtils.putString(App.getApp(), "token", jsonObject.getString("token"));
                                    startActivity(new Intent(LoginActivity.this, BottomMainActivity.class));
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                this.exitApp();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private long exitTime = 0;

    /**
     * 退出程序
     */
    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 3000) {
            Toast.makeText(LoginActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            App.exit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

