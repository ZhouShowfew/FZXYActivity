package com.example.steven.fzxyactivity.module.main.View.Fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.materialdesign.views.PEWImageView;
import com.example.steven.fzxyactivity.module.activitydetail.ActivityDeatailActivity;

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

    public HomeRcyAdapter(Context context) {
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        holder = new AcitivityItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AcitivityItemViewHolder viewHolder= (AcitivityItemViewHolder) holder;
        viewHolder.ivActivityPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ActivityDeatailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 20;
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
