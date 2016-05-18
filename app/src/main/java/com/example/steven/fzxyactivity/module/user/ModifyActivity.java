package com.example.steven.fzxyactivity.module.user;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.steven.fzxyactivity.App;
import com.example.steven.fzxyactivity.Constant.Constants;
import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.common.util.OkUtils;
import com.example.steven.fzxyactivity.common.util.SpUtils;
import com.example.steven.fzxyactivity.common.util.ToastUtil;
import com.example.steven.fzxyactivity.materialdesign.views.ButtonRectangle;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ModifyActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.et_ID)
    AppCompatEditText etID;
    @Bind(R.id.et_email)
    AppCompatEditText etEmail;
    @Bind(R.id.et_password)
    AppCompatEditText etPassword;
    @Bind(R.id.btn_register)
    ButtonRectangle btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        ButterKnife.bind(this);
        toolbar.setTitle("修改密码");
        toolbar.setSubtitle("请注意保管好新密码");
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
    public void modifyPassword(){
        String url = Constants.ServerUrl + "user/changePassword";
        Map<String,String> map=new HashMap<>();
        map.put("userId", SpUtils.getString(App.getApp(),"userId"));
        map.put("OldUserPwd ", etEmail.getText().toString());
        map.put("NewUserPwd ",etPassword.getText().toString());
        OkUtils.post(url,map,new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                //用户ID密码不能为空
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ToastUtil.toast(jsonObject.getString("msg"));
                    if (jsonObject.getString("code").equals("1")) {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


}
