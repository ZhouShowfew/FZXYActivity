package com.example.steven.fzxyactivity.module.main.View.Fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.materialdesign.views.PEWImageView;
import com.example.steven.fzxyactivity.module.activitydetail.ActivityDeatailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Steven on 2016/4/16.
 * 主页的适配器
 */
public class HomeRcyAdapter extends RecyclerView.Adapter {
    private RecyclerView.ViewHolder holder;
    private Context context;
    private JSONArray array;
    public HomeRcyAdapter(Context context, JSONArray array) {
        this.context = context;
        this.array=array;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        holder = new AcitivityItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        AcitivityItemViewHolder viewHolder= (AcitivityItemViewHolder) holder;
        viewHolder.ivActivityPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final JSONObject object;
                try {
                    object = array.getJSONObject(position);
                    Intent intent=new Intent(context, ActivityDeatailActivity.class);
                    intent.putExtra("jsonObject",object.toString());
                    context.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            final JSONObject object=array.getJSONObject(position);
            viewHolder.tvTitle.setText(object.getString("activityTitle"));
            if (!TextUtils.isEmpty(object.getString("clickCount")))
            viewHolder.tvHot.setText("浏览量:"+object.getString("clickCount"));
            if (!TextUtils.isEmpty(object.getString("startTime")))
            viewHolder.tvTime.setText("开始时间:"+object.getString("startTime").substring(0,10));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    class AcitivityItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_activity_pic)
        PEWImageView ivActivityPic;
        @Bind(R.id.tv_procesing)
        TextView tvProcesing;
        @Bind(R.id.iv_headerPic)
        ImageView ivHeaderPic;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_hot)
        TextView tvHot;
        @Bind(R.id.tv_time)
        TextView tvTime;
        public AcitivityItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
