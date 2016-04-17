package com.example.steven.fzxyactivity.materialdesign.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.materialdesign.utils.MDUtils;


public class ButtonFloatSmall extends ButtonFloat {

	public ButtonFloatSmall(Context context, AttributeSet attrs) {
		super(context, attrs);
		sizeRadius = 20;
		sizeIcon = 20;
		setDefaultProperties();
		LayoutParams params = new LayoutParams(MDUtils.dpToPx(sizeIcon, getResources()), MDUtils.dpToPx(sizeIcon, getResources()));
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		icon.setLayoutParams(params);
	}
	
	protected void setDefaultProperties(){
		rippleSpeed = MDUtils.dpToPx(2, getResources());
		rippleSize = 10;		
		// Min size
		setMinimumHeight(MDUtils.dpToPx(sizeRadius*2, getResources()));
		setMinimumWidth(MDUtils.dpToPx(sizeRadius*2, getResources()));
		// Background shape
		setBackgroundResource(R.drawable.background_button_float);
//		setBackgroundColor(backgroundColor);
	}

}
