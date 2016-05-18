package com.example.steven.fzxyactivity.module.personcenter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.steven.fzxyactivity.Constant.Constants;
import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.common.util.OkUtils;
import com.example.steven.fzxyactivity.common.util.SpUtils;
import com.example.steven.fzxyactivity.module.main.View.Fragment.adapter.HomeRcyAdapter;
import com.example.steven.fzxyactivity.module.main.View.Fragment.adapter.MyActivityAdapter;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MyActivity extends AppCompatActivity {

    @Bind(R.id.include_recycle_view)
    RecyclerView includeRecycleView;
    @Bind(R.id.include_swipe_refresh)
    SwipeRefreshLayout includeSwipeRefresh;

    MyActivityAdapter adapter;
    MyActivity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);
        context=this;
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        includeRecycleView.setLayoutManager(mLinearLayoutManager);
        includeSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                includeSwipeRefresh.setRefreshing(false);
            }
        });

        String url = Constants.ServerUrl+"app/searchActivityApp/"+ SpUtils.getString(this,"usrId");
        OkUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    final JSONArray array=jsonObject.getJSONArray("activityapplicant");
                    if (array.getJSONObject(0).getString("code").equals("1")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter=new MyActivityAdapter(context,array);
                                includeRecycleView.setAdapter(adapter);

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
