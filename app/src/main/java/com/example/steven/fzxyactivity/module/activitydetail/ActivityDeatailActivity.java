package com.example.steven.fzxyactivity.module.activitydetail;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.common.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityDeatailActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_pic_activity_detail)
    ImageView ivPicActivityDetail;
    @Bind(R.id.tv_procesing)
    TextView tvProcesing;
    @Bind(R.id.tv_time_start)
    TextView tvTimeStart;
    @Bind(R.id.tv_time_end)
    TextView tvTimeEnd;
    @Bind(R.id.iv_headerPic)
    ImageView ivHeaderPic;
    @Bind(R.id.tv_host_name)
    TextView tvHostName;
    @Bind(R.id.tv_create_time)
    TextView tvCreateTime;
    @Bind(R.id.tv_host_school)
    TextView tvHostSchool;
    @Bind(R.id.tv_activity_hot)
    TextView tvActivityHot;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_activity_desc)
    TextView tvActivityDesc;
    @Bind(R.id.ll_activity_detail)
    LinearLayout llActivityDetail;
    @Bind(R.id.scroll_activity_detail)
    ScrollView scrollActivityDetail;
    @Bind(R.id.tv_taskfdes_moretask)
    TextView tvTaskfdesMoretask;
    @Bind(R.id.btn_task_join)
    Button btnTaskJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_deatail);
        ButterKnife.bind(this);
        toolbar.setTitle("活动信息");
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

    @OnClick(R.id.btn_task_join)
    public void setBtnTaskJoin(){
        ToastUtil.toast("报名中");
    }
}
