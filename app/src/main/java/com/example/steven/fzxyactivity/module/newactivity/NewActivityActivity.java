package com.example.steven.fzxyactivity.module.newactivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.common.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewActivityActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_pic)
    ImageView ivPic;
    @Bind(R.id.et_title)
    AppCompatEditText etTitle;
    @Bind(R.id.et_desc)
    AppCompatEditText etDesc;
    @Bind(R.id.et_tag)
    AppCompatEditText etTag;
    @Bind(R.id.sp_school)
    Spinner spSchool;
    @Bind(R.id.btn_register)
    AppCompatButton btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);
        toolbar.setTitle("发布活动");
        toolbar.setSubtitle("填写您的活动信息");
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
        ToastUtil.toast("发布中，请稍等");
        finish();
    }
}
