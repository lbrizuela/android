package com.example.restaurant;

import com.example.clases.CustomKeyboard;
import com.example.clases.Mesa;
import com.example.clases.Util;
import com.example.sharedpreferences.SharedPreference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TecladoChico extends Activity {

	
	
	private static final String TAG = "TecladoChico";
	private SharedPreference instanciaShare;
	private Context mContext;
	protected WakeLock wakelock;
	private Views views;
	private EditText edCantConmensales;
	private CustomKeyboard mCustomKeyboard;
	private TextView titulo;
	private Button aceptar, volver;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teclado_chico);
		
		mContext = getApplicationContext();
		instanciaShare = new SharedPreference(mContext);
		views = Views.getInstance(getApplicationContext());
		
		titulo = (TextView) findViewById(R.id.tv_tc_texto);
		aceptar = (Button) findViewById(R.id.btn_tc_aceptar);
		volver = (Button) findViewById(R.id.btn_tc_salir);
		
		Bundle extras = getIntent().getExtras();
		int modo = extras.getInt("enviar");	
		
		
		edCantConmensales = (EditText) findViewById(R.id.keyboard_texto_chico);
		mCustomKeyboard = new CustomKeyboard(this, R.id.keyboardviewchico, R.xml.qwerty_chico);

		mCustomKeyboard.registerEditText(R.id.keyboard_texto_chico);
		
		switch (modo) {
		case Util.CANTIDAD_COMENSALES:
			titulo.setText(getResources().getString(R.string.cantidad_comensales));			
			break;
		}
		
		
		aceptar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String cantidad = edCantConmensales.getText().toString();
				if(!cantidad.equals("")){
					if(!cantidad.substring(0,1).equals("0")){
						Intent i = new Intent();
						i.putExtra("clave", Util.CANTIDAD_COMENSALES);
						i.putExtra("resultado", cantidad);
						setResult(Teclado.RESULT_OK, i);
						finish();
					}else {
						Toast.makeText(mContext, "Por favor ingrese un cantidad valida", Toast.LENGTH_LONG).show();
					}
				}else {
					Toast.makeText(mContext, "Debera ingresar un valor o salir", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		volver.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				setResult(Teclado.RESULT_CANCELED, i);
				finish();
			}
		});
		
		
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
	private void fullScreen() {
		try {
			// HIDE STATUS BAR Y BOTONES VIRTUALES
			TecladoChico.this
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
		this.wakelock = ((PowerManager) getSystemService("power")).newWakeLock(
				10, "etiqueta");
		this.wakelock.acquire();
	}
	
	
	
}
