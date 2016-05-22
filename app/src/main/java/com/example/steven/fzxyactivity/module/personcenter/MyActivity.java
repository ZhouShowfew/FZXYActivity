package com.example.steven.fzxyactivity.module.personcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.steven.fzxyactivity.Constant.Constants;
import com.example.steven.fzxyactivity.Entity.MyActivityEntity;
import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.common.util.LoadingState;
import com.example.steven.fzxyactivity.common.util.NetWorkUtil;
import com.example.steven.fzxyactivity.common.util.OkUtils;
import com.example.steven.fzxyactivity.common.util.SnackbarUtil;
import com.example.steven.fzxyactivity.common.util.SpUtils;
import com.example.steven.fzxyactivity.common.util.ToastUtil;
import com.example.steven.fzxyactivity.common.util.customview.QQListview;
import com.example.steven.fzxyactivity.common.util.customview.XHLoadingView;
import com.example.steven.fzxyactivity.module.activitydetail.ActivityDeatailActivity;
import com.example.steven.fzxyactivity.module.main.View.Fragment.adapter.HomeRcyAdapter;
import com.example.steven.fzxyactivity.module.main.View.Fragment.adapter.MyActivityAdapter;
import com.example.steven.fzxyactivity.module.personcenter.adpter.PActivityAdapter;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MyActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listview_qq)
    QQListview listviewQq;

    PActivityAdapter adapter;
    MyActivity context;
    private XHLoadingView mLoadingView;

    private static final String LOADING_STATE="state";
    private static final String LOADING="loading";
    private static final String LOADING_EMPTY="empty";
    private static final String LOADING_NONETWORK="nonet";
    private static final String LOADING_ERROR="error";
    private String mLoadState;
    List<MyActivityEntity> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);
        context = this;
        toolbar.setTitle("我的活动");
        toolbar.setSubtitle("管理您的活动");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mLoadingView = (XHLoadingView) findViewById(R.id.lv_loading);
        mLoadingView.withLoadEmptyText("≥﹏≤ , 啥也木有 !").withEmptyIcon(R.drawable.disk_file_no_data).withBtnEmptyEnnable(false)
                .withErrorIco(R.drawable.ic_chat_empty).withLoadErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withBtnErrorText("臭狗屎!!!")
                .withLoadNoNetworkText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIcon(R.drawable.ic_chat_empty).withBtnNoNetText("网弄好了，重试")
                .withLoadingIcon(R.drawable.loading_animation).withLoadingText("加载中...").withOnRetryListener(new XHLoadingView.OnRetryListener() {
            @Override
            public void onRetry() {
                SnackbarUtil.show(mLoadingView,"已经在努力重试了",0);
            }
        }).build();

        if (!NetWorkUtil.isNetWorkConnected(this)){
            mLoadingView.setVisibility(View.VISIBLE);
            mLoadingView.setState(LoadingState.STATE_ERROR);
        }else {
            //加载中
            mLoadingView.setVisibility(View.VISIBLE);
            mLoadingView.setState(LoadingState.STATE_LOADING);
            //String url = Constants.ServerUrl + "activity/findbyactivityid/" + SpUtils.getString(this, "userId");
            String url = Constants.ServerUrl + "activity/findbyactivityid/feng";
            OkUtils.get(url, new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLoadingView.setVisibility(View.VISIBLE);
                            mLoadingView.setState(LoadingState.STATE_ERROR);
                        }
                    });
                }

                @Override
                public void onResponse(String response) {
                    try {

                        if (response.equals("null")){
                            //null  没有活动
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mLoadingView.setVisibility(View.VISIBLE);
                                    mLoadingView.setState(LoadingState.STATE_EMPTY);
                                }
                            });
                        }else {
                            final JSONObject jsonObject = new JSONObject(response);
                            final JSONArray array = jsonObject.getJSONArray("activity");
                            if (array.getJSONObject(0).getString("code").equals("1")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mLoadingView.setVisibility(View.GONE);
                                        mDatas= new ArrayList<MyActivityEntity>();
                                        for (int i=0;i<array.length();i++){
                                            try {
                                                MyActivityEntity entity=new MyActivityEntity();
                                                entity.setId("活动ID:"+array.getJSONObject(i).getString("activityId"));
                                                entity.setTime("活动名:"+array.getJSONObject(i).getString("activityTitle"));
                                                mDatas.add(entity);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        adapter=new PActivityAdapter(MyActivity.this,mDatas);
                                        listviewQq.setAdapter(adapter);
                                        listviewQq.setDelButtonClickListener(new QQListview.DelButtonClickListener() {
                                            @Override
                                            public void clickHappend(int position) {
                                                //网络请求操作删除
                                                try {
                                                    deleteMyActivity(array.getJSONObject(position).getString("activityId"),position);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        listviewQq.setEditButtonClickListener(new QQListview.EditButtonClickListener() {
                                            @Override
                                            public void clickEditHappend(int position) {
                                                //编辑操作
                                                Intent intent=new Intent(context, EditActivityActivity.class);
                                                try {
                                                    intent.putExtra("jsonObject",array.getJSONObject(position).toString());
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                context.startActivity(intent);

                                            }
                                        });
                                        listviewQq.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                        {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                            {
                                                //点击操作,跳进任务详情页
                                                Intent intent=new Intent(context, ActivityDeatailActivity.class);
                                                try {
                                                    intent.putExtra("jsonObject",array.getJSONObject(position).toString());
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                context.startActivity(intent);

                                            }
                                        });
                                    }
                                });
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });


        }



    }

    public void deleteMyActivity(String id, final int position){
        String url = Constants.ServerUrl+"activity/deleteActivity/"+id;
        OkUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    ToastUtil.toast(jsonObject.getString("msg"));
                    if (jsonObject.getString("code").equals("1")){
                        //界面删除
                        adapter.delete(position);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
