package com.example.restaurant;

import java.util.Calendar;

import com.example.clases.Util;
import com.example.sharedpreferences.SharedPreference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	private SharedPreference instanciaShare;
	private Context mContext;
	protected WakeLock wakelock;
	private Views views;
    boolean vistas = true;
	public TextView fecha_hora;
	public ImageButton carroCompra , llamarMozo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    
        mContext= getApplicationContext();
        instanciaShare= new SharedPreference(mContext);
        
        
        if(!instanciaShare.recuperarLoginMozo()){
     
        	Intent i = new Intent(mContext, MozoLogin.class);
        	startActivity(i);
        	finish();
        }else{ if(!instanciaShare.recuperarInicioPedido()){
        		
        		Intent i = new Intent(mContext, EmprezarPedido.class);
            	startActivity(i);
            	finish();
        		
        	}else {
        	
        	setContentView(R.layout.menu_principal);
        	views = Views.getInstance(getApplicationContext());
        	fecha_hora = (TextView) findViewById(R.id.main_tv_fecha_hora);
        	carroCompra =(ImageButton)findViewById(R.id.img_carro_compra);
        	llamarMozo =(ImageButton)findViewById(R.id.img_llamar_mozo);
        	fechaHora();
        	
        	carroCompra.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Intent i= new Intent(mContext, CarroCompra.class);
					startActivity(i);
					
					
				}
			});
        	
        	llamarMozo.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i= new Intent(mContext, LlamarMozo.class);
					startActivity(i);
					
					
					
				}
			});
        	
        	
        	
        	
        	}
        	
        }
        
    }
    
    
    
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		int resultado = intent.getIntExtra("clave",0);

		if (resultado==Util.FINALIZAR_PEDIDO) {
			
			instanciaShare.limpiarLoginMozo();
			instanciaShare.limpiarPedido();
			Intent i = new Intent(mContext, MozoLogin.class);
        	startActivity(i);
        	finish();
			
		}
		
		
	}




	




	public void fechaHora(){
    	
    	
    	new CountDownTimer(1000000000, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				Calendar c = Calendar.getInstance();
				fecha_hora.setText(new StringBuilder(String.valueOf(String.format("%02d", new Object[] { Integer.valueOf(c.get(5)) })))
								.append("/")
								.append(String.format("%02d", new Object[] { Integer.valueOf(c.get(2) + 1) }))
								.append("/")
								.append(c.get(1))
								.append(" ")
								.append(String.format("%02d",new Object[] { Integer.valueOf(c.get(11)) }))
								.append(":")
								.append(String.format("%02d",new Object[] { Integer.valueOf(c.get(12)) }))
								.append(":")
								.append(String.format("%02d",new Object[] { Integer.valueOf(c.get(13)) })).toString());

			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				
			}
		}.start();
    	
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
