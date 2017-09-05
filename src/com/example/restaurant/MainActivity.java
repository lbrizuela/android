package com.example.restaurant;

import com.example.clases.Util;
import com.example.sharedpreferences.SharedPreference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {
	
	private SharedPreference instanciaShare;
	private Context mContext;
	protected WakeLock wakelock;
	private Views views;
    boolean vistas = true;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    
        mContext= getApplicationContext();
        instanciaShare= new SharedPreference(mContext);
        
        
        if(instanciaShare.recuperarIdMozo().equals("")){
     
        	Intent i = new Intent(mContext, MozoLogin.class);
        	startActivity(i);
        	finish();
        }else{ if(instanciaShare.recuperarIdPedido().equals("")){
        		
        		Intent i = new Intent(mContext, EmprezarPedido.class);
            	startActivity(i);
            	finish();
        		
        	}else {
        	
        	setContentView(R.layout.pantalla_principal);
        	views = Views.getInstance(getApplicationContext());
        	
        	}
        	
        }
        
    }
    
    
    
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		fullScreen();
		mantenerPantallaEncendida();
		try {
			this.views.agregarViewNavigationBar();
		    this.views.agregarViewStatusBar();
		} catch (Exception e) {
			// TODO: handle exception
		}
        this.wakelock.acquire();
	}



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}



	private void fullScreen() {
		try {
			// HIDE STATUS BAR Y BOTONES VIRTUALES
			MainActivity.this
					.getWindow()
					.getDecorView()
					.setSystemUiVisibility(
							View.SYSTEM_UI_FLAG_LAYOUT_STABLE
									| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
									| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
									| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
									| View.SYSTEM_UI_FLAG_FULLSCREEN
									| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 private void mantenerPantallaEncendida() {
	        this.wakelock = ((PowerManager) getSystemService("power")).newWakeLock(10, "etiqueta");
	        this.wakelock.acquire();
	    }

 
}
