package com.example.restaurant;

import com.example.clases.Util;
import com.example.sharedpreferences.SharedPreference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LlamarMozo extends Activity {
	
	 private SharedPreference instanciaShare;
     private Context mContext;
     protected WakeLock wakelock;
 	 private Views views;
 	public ImageButton volver , finalizarPedido; 
 	public Button aceptar;
 	public TextView texto ;
 	public int request_code = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pop_confirmacion);
		fullScreen();
		mContext = getApplicationContext();
		instanciaShare = new SharedPreference(mContext);
		
		volver = (ImageButton)findViewById(R.id.imgbtn_pc_salir);
		finalizarPedido = (ImageButton)findViewById(R.id.imgbtn_pc_finalizarPedido);
		aceptar = (Button)findViewById(R.id.btn_pc_positivo);
		texto = (TextView) findViewById(R.id.tv_pc_pregunta);
		texto.setText("Desea llamar al Mozo? ");
		aceptar.setText("SI");
		
		aceptar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent i = new Intent();
				
				setResult(LlamarMozo.RESULT_CANCELED, i);
				finish();
				
			}
		});
		
		volver.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent();
				
				setResult(LlamarMozo.RESULT_CANCELED, i);
				finish();
				
			}
		});
		finalizarPedido.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(mContext, Teclado.class);
				i.putExtra("enviar", Util.FINALIZAR_PEDIDO);
				startActivityForResult(i,request_code);
				
				
				
			}
		});
		
	}
	
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == RESULT_OK) {
			int resultado = intent.getIntExtra("clave",0);
	
			if (resultado==Util.FINALIZAR_PEDIDO) {
				
				boolean salir = intent.getBooleanExtra("resultado", false);
				if (salir) {
	
					Intent i = new Intent();
					i.putExtra("clave", Util.FINALIZAR_PEDIDO);
					setResult(LlamarMozo.RESULT_OK, i);
					finish();
				} else {
					Toast.makeText(mContext, "CODIGO INCORRECTO", Toast.LENGTH_LONG)
							.show();
				}
				
				
			}
		}
		
	}




	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		fullScreen();
	
		try {
			this.views.agregarViewNavigationBar();
		    this.views.agregarViewStatusBar();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	    
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	 private void fullScreen() {
			try {
				// HIDE STATUS BAR Y BOTONES VIRTUALES
					LlamarMozo.this
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
	
	
	
}
