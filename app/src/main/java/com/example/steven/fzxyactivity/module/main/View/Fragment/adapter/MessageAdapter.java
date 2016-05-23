package com.example.steven.fzxyactivity.module.main.View.Fragment.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.steven.fzxyactivity.Entity.MessageEntity;
import com.example.steven.fzxyactivity.Entity.MyActivityEntity;
import com.example.steven.fzxyactivity.R;

import java.util.List;

/**
 * Created by Steven on 2016/5/22.
 */
public class MessageAdapter extends BaseAdapter {
    private Context mContext;
    List<MessageEntity> mDatas;
    LayoutInflater layoutInflater;
    public MessageAdapter(Context mContext, List<MessageEntity> mDatas){
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
            view = layoutInflater.inflate(R.layout.item_message,null);
            listItemView.tvMsg= (TextView) view.findViewById(R.id.tv_msg);
            listItemView.tvTime= (TextView) view.findViewById(R.id.tv_msg_time);
            listItemView.tvUser= (TextView) view.findViewById(R.id.tv_msg_user);
            view.setTag(listItemView);
        }else {
            listItemView= (ListItemView) view.getTag();
        }
        if (!TextUtils.isEmpty(mDatas.get(i).getMessage()))
        listItemView.tvMsg.setText(mDatas.get(i).getMessage());
        if (!TextUtils.isEmpty(mDatas.get(i).getMessageTime()))
        listItemView.tvTime.setText(mDatas.get(i).getMessageTime());
        if (!TextUtils.isEmpty(mDatas.get(i).getUserName()))
        listItemView.tvUser.setText(mDatas.get(i).getUserName());
        return view;
    }

    public void delete(int i){
        mDatas.remove(i);
        this.notifyDataSetChanged();
    }
    public final class ListItemView{
        TextView tvMsg;
        TextView tvTime;
        TextView tvUser;
    }
}
