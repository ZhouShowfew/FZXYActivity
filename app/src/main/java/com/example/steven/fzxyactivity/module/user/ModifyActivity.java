package com.example.steven.fzxyactivity.module.user;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.steven.fzxyactivity.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ModifyActivity extends AppCompatActivity {

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
    AppCompatButton btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        ButterKnife.bind(this);
        toolbar.setTitle("修改个人信息");
        toolbar.setSubtitle("填写您要修改的信息");
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
}
