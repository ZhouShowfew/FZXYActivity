package com.example.steven.fzxyactivity.materialdesign.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.materialdesign.views.MaterialEditText;


/**
 * Created by SkyEyes5 on 2016/1/12.
 * 加减的自定义控件
 */
public class LinearLayoutAddDelete extends LinearLayout implements View.OnClickListener {
    private  float mFloat;
    private MaterialEditText edit_title;

    public LinearLayoutAddDelete(Context context) {
        super(context);
    }

    public LinearLayoutAddDelete(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LinearLayoutAddDelete);
        mFloat = a.getFloat(R.styleable.LinearLayoutAddDelete_txt_num, 0);//自定义他的参数
        initView(context,mFloat);
    }

    /**
     * 初始化界面
     * @param context
     * 上下文
     * @param mFloat
     */
    private void initView(Context context, float mFloat) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.linear_widget, this);
        ImageView basic_money_delete= (ImageView) findViewById(R.id.basic_money_delete);
        basic_money_delete.setOnClickListener(this);
        ImageView basic_money_add= (ImageView) findViewById(R.id.basic_money_add);
        basic_money_add.setOnClickListener(this);
        edit_title= (MaterialEditText) findViewById(R.id.edit_title);
        edit_title.setText(""+mFloat);
        edit_title.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        edit_title.setText(s);
                        edit_title.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    edit_title.setText(s);
                    edit_title.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        edit_title.setText(s.subSequence(0, 1));
                        edit_title.setSelection(1);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.basic_money_add:
                add();//加1
                break;
            case R.id.basic_money_delete:
                delete();//减1
                break;
        }
    }
    /**
     * 添加
     */
    private void add() {
        float i;
        if (!edit_title.getText().toString().equals("")){
            i= Float.parseFloat(edit_title.getText().toString());
        }else {
            i=0;
        }
        ++i;
        edit_title.setText(""+i);
    }
    /**
     * 减1
     */
    private void delete() {
        float j;
        if (!edit_title.getText().toString().equals("")){
            j= Float.parseFloat(edit_title.getText().toString());
        }else {
            j=0;
        }
        --j;
        if (j<0){
            edit_title.setError("金额不能小于0");
        }else {
            edit_title.setText(""+j);
        }
    }
    /**
     * 获取文本数据
     */
    public void setEditViewText(String money){
        edit_title.setText(money);
    }
    public String getEditViewText(){
        String num;
        if (edit_title.getText()!=null){
            num=edit_title.getText().toString();
            if (num.equals("")) {
                num = "0";
            }
            if (Float.valueOf(num)==0){
                num="0";
            }
        }else {
            num="0";
        }
        return num;
    }
    public MaterialEditText getEdit_Text(){
        return edit_title;
    }
    public void clearFocus(){
        edit_title.clearFocus();
    }
}
