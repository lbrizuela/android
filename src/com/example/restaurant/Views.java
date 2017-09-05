package com.example.restaurant;
import com.example.clases.CustomViewGroup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

public class Views {
	
		private static Views instance = null;
//		private Context context;
		private WindowManager manager;
		private CustomViewGroup viewStatusBar;
		private CustomViewGroup viewNavigationBar;
		WindowManager.LayoutParams localLayoutParamsStatusBar;
		WindowManager.LayoutParams localLayoutParamsBotonVirtual;

		private Views(Context context) {
//			this.context = context;
			manager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
			
			
			//STATUS BAR
			viewStatusBar = new CustomViewGroup(context);		
			
			localLayoutParamsStatusBar = new WindowManager.LayoutParams();
			localLayoutParamsStatusBar.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
			localLayoutParamsStatusBar.gravity = Gravity.TOP;
			localLayoutParamsStatusBar.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
					|

					// this is to enable the notification to recieve touch
					// events
					WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |

					// Draws over status bar
					WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

			localLayoutParamsStatusBar.width = WindowManager.LayoutParams.MATCH_PARENT;
			localLayoutParamsStatusBar.height = (int) (25 * context.getResources()
					.getDisplayMetrics().scaledDensity);
			localLayoutParamsStatusBar.format = PixelFormat.TRANSPARENT;//RGB_565;
			
			
			//NAVIGATION BAR
			viewNavigationBar = new CustomViewGroup(context);
			
			localLayoutParamsBotonVirtual = new WindowManager.LayoutParams();
			localLayoutParamsBotonVirtual.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
			localLayoutParamsBotonVirtual.gravity = Gravity.BOTTOM;
			localLayoutParamsBotonVirtual.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
					|

					// this is to enable the notification to recieve touch
					// events
					WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |

					// Draws over status bar
					WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

			localLayoutParamsBotonVirtual.width = WindowManager.LayoutParams.MATCH_PARENT;
			localLayoutParamsBotonVirtual.height = (int) (48 * context
					.getResources().getDisplayMetrics().scaledDensity);
			localLayoutParamsBotonVirtual.format = PixelFormat.TRANSPARENT;		
		}

		public static Views getInstance(Context context) {
			if (instance == null) {
				instance = new Views(context);
			}
			return instance;
		}

		public void agregarViewStatusBar() {
			if (!viewStatusBar.isAttachedToWindow()) {
				manager.addView(viewStatusBar, localLayoutParamsStatusBar);
			}
		}

		public void agregarViewNavigationBar() {
			if (!viewNavigationBar.isAttachedToWindow()) {
				manager.addView(viewNavigationBar, localLayoutParamsBotonVirtual);
			}		
		}
		
		public void removerViewStatusBar() {
			if (viewStatusBar.isAttachedToWindow()) {
				manager.removeView(viewStatusBar);
			}		
		}
		
		public void removerViewNavegationBar() {
			if (viewNavigationBar.isAttachedToWindow()) {
				manager.removeView(viewNavigationBar);
			}		
		}

	}



