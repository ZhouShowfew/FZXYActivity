package com.example.steven.fzxyactivity.module.personcenter.adpter;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.steven.fzxyactivity.Entity.MyActivityEntity;
import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.materialdesign.views.PEWImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Steven on 2016/5/22.
 */
public class PActivityAdapter extends BaseAdapter {
    private Context mContext;
    List<MyActivityEntity> mDatas;
    LayoutInflater layoutInflater;
    public PActivityAdapter(Context mContext,List<MyActivityEntity> mDatas){
        this.mContext=mContext;
        this.mDatas=mDatas;
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListItemView listItemView=null;
        if (view==null){
            listItemView=new ListItemView();
            view = layoutInflater.inflate(R.layout.item_my_pacitivy,null);
            listItemView.tvId= (TextView) view.findViewById(R.id.tv_activity_id);
            listItemView.tvTime= (TextView) view.findViewById(R.id.tv_activity_time);
            view.setTag(listItemView);
        }else {
            listItemView= (ListItemView) view.getTag();
        }
        listItemView.tvId.setText(mDatas.get(i).getId());
        listItemView.tvTime.setText(mDatas.get(i).getTime());
        return view;
    }

    public void delete(int i){
        mDatas.remove(i);
        this.notifyDataSetChanged();
    }
    public final class ListItemView{
        TextView tvId;
        TextView tvTime;
    }
}
