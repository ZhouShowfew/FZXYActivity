package com.example.steven.fzxyactivity.materialdesign.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.materialdesign.views.MaterialEditText;

/**
 * Created by SkyEyes5 on 2016/1/12.
 * 加减的自定义控件
 */
public class ProgressWidget extends LinearLayout  {
    private  float mFloat;
    private MaterialEditText edit_title;
    private LinearLayout parent;

    public ProgressWidget(Context context) {
        super(context);
    }

    public ProgressWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * 初始化界面
     * @param context
     * 上下文
     */
    private void initView(Context context) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_progress, this);
        parent= (LinearLayout) findViewById(R.id.parent);
    }
    public void setStep(int j){
        clean();
        for (int i=0;i<j;i++){
            ImageView imageView= (ImageView) parent.getChildAt(i);
            imageView.setBackgroundColor(getResources().getColor(R.color.colorRealAccent));
        }
    }
    private void clean(){
        for (int i=0;i<parent.getChildCount();i++){
            ImageView imageView= (ImageView) parent.getChildAt(i);
            imageView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    /**
     * 设置总步骤
     * @param i
     * i次  i<=6
     */
    public void setStepCount(int i){
        int j=6;
        int remove_count=j-i;
        for (int k=0;k<remove_count;k++){
            parent.removeViewAt(0);
        }
    }
}
