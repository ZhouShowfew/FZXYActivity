package com.example.steven.fzxyactivity.module.activitydetail;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.steven.fzxyactivity.App;
import com.example.steven.fzxyactivity.Constant.Constants;
import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.common.util.OkUtils;
import com.example.steven.fzxyactivity.common.util.SpUtils;
import com.example.steven.fzxyactivity.common.util.ToastUtil;
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

public class ActivityDeatailActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_activity_title)
    TextView tvAcitivityTitle;
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
        try {
            JSONObject object=new JSONObject(getIntent().getStringExtra("jsonObject"));
            getActivityDetail(object.getString("activityId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initValue(JSONObject ob)  {
        try {
            if (!TextUtils.isEmpty(ob.getString("activityTitle")))
                tvAcitivityTitle.setText(ob.getString("activityTitle"));
            if (!TextUtils.isEmpty(ob.getString("userName")))
                tvHostName.setText(ob.getString("userName"));
            if (!TextUtils.isEmpty(ob.getString("activityDesc")))
                tvActivityDesc.setText(ob.getString("activityDesc"));
            if (!TextUtils.isEmpty(ob.getString("collegeId")))
                tvHostSchool.setText("来自 "+ob.getString("collegeId"));
            if (!TextUtils.isEmpty(ob.getString("clickCount")))
                tvActivityHot.setText("浏览量:"+ob.getString("clickCount"));
            if (!TextUtils.isEmpty(ob.getString("startTime")))
                tvTimeStart.setText("开始时间:"+ob.getString("startTime").substring(0,10));
            if (!TextUtils.isEmpty(ob.getString("endTime")))
                tvTimeEnd.setText("结束时间:"+ob.getString("endTime").substring(0,10));
            if (!TextUtils.isEmpty(ob.getString("createdTime")))
                tvCreateTime.setText("创建时间:"+ob.getString("createdTime").substring(0,10));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.btn_task_join)
    public void setBtnTaskJoin() {
        String url= Constants.ServerUrl+"app/addActivityApp";
        Map<String,String> map=new HashMap<>();
        try {
            map.put("activityId",new JSONObject(getIntent().getStringExtra("jsonObject")).getString("activityId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put("userId", SpUtils.getString(App.getApp(),"userId"));
        map.put("userName", SpUtils.getString(App.getApp(),"userName"));
        map.put("activityMemberStatus","0");
        OkUtils.post(url, map, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                ToastUtil.toast("网络错误! ");
            }

            @Override
            public void onResponse(String response) {
                //已报名
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    ToastUtil.toast(jsonObject.getString("msg"));
                    if (jsonObject.getString("code").equals("1")){
                        ToastUtil.toast("报名成功!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getActivityDetail(String id){
        String url = Constants.ServerUrl+"activity/getactid/"+id;
        OkUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            ToastUtil.toast("获取活动信息"+jsonObject.getString("msg"));
                            if (jsonObject.getString("code").equals("1")){
                                initValue(jsonObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
}
