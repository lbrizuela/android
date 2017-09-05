package com.example.clases;

import android.view.ViewGroup;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;


public class CustomViewGroup extends ViewGroup {
	
	
	public CustomViewGroup(Context context) {
		super(context);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.v("customViewGroup", "**********Intercepted");
		return true;
	}

}
