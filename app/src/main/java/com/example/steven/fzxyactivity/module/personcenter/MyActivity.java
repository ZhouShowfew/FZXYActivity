package com.example.steven.fzxyactivity.module.personcenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.steven.fzxyactivity.Constant.Constants;
import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.common.util.OkUtils;
import com.example.steven.fzxyactivity.common.util.SpUtils;
import com.example.steven.fzxyactivity.common.util.customview.QQListview;
import com.example.steven.fzxyactivity.module.main.View.Fragment.adapter.MyActivityAdapter;
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


    MyActivityAdapter adapter;
    MyActivity context;
    @Bind(R.id.listview_qq)
    QQListview listviewQq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);
        context = this;

        List<String> mDatas = new ArrayList<String>(Arrays.asList("HelloWorld", "Welcome", "Java", "Android", "Servlet", "Struts",
                "Hibernate", "Spring", "HTML5", "Javascript", "Lucene"));
        final ArrayAdapter<String> mAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        listviewQq.setAdapter(mAdapter);

        listviewQq.setDelButtonClickListener(new QQListview.DelButtonClickListener() {
            @Override
            public void clickHappend(int position) {
                //Toast.makeText(MainActivity.this, position + " : " + mAdapter.getItem(position), 1).show();
                mAdapter.remove(mAdapter.getItem(position));
            }
        });
        listviewQq.setEditButtonClickListener(new QQListview.EditButtonClickListener() {
            @Override
            public void clickEditHappend(int position) {

            }
        });
        listviewQq.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Toast.makeText(MainActivity.this, position + " : " + mAdapter.getItem(position), 1).show();
            }
        });

        String url = Constants.ServerUrl + "app/searchActivityApp/" + SpUtils.getString(this, "usrId");
        OkUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    final JSONArray array = jsonObject.getJSONArray("activityapplicant");
                    if (array.getJSONObject(0).getString("code").equals("1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


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
