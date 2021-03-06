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
import android.text.InputType;
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
	private String cantidad;
	public int request_code = 1;
	public int modo =0;
	
	
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
		modo = extras.getInt("enviar");	
		
		
		edCantConmensales = (EditText) findViewById(R.id.keyboard_texto_chico);
		mCustomKeyboard = new CustomKeyboard(this, R.id.keyboardviewchico, R.xml.qwerty_chico);

		mCustomKeyboard.registerEditText(R.id.keyboard_texto_chico );
		
		switch (modo) {
		case Util.CANTIDAD_COMENSALES:
			titulo.setText(getResources().getString(R.string.cantidad_comensales));			
			break;
		case Util.CANTIDAD_ARTICULO:
			titulo.setText(getResources().getString(R.string.cantidad));			
			break;
		case Util.CANTIDAD_OFERTA:
			titulo.setText(getResources().getString(R.string.cantidad));			
			break;
			
		}
		
		
		aceptar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				switch (modo) {
				case Util.CANTIDAD_COMENSALES:
					cantidad = edCantConmensales.getText().toString();
					if(!cantidad.equals("")){
						if(Integer.parseInt(cantidad) < 30){
							if(!cantidad.substring(0,1).equals("0")){
								Intent i = new Intent();
								i.putExtra("respuesta", Util.CANTIDAD_COMENSALES);
								i.putExtra("cantidad", cantidad);
								setResult(Teclado.RESULT_OK, i);
								finish();
							}else {
								Util.toastCustom(mContext, " Por favor, ingresé un cantidad valida" , Util.TOAST_MENSAJE_ALERTA_MENOR);
							} 
						}else {
							Intent i = new Intent(mContext, PopUp.class);
							i.putExtra("envia", Util.CANTIDAD_COMENSALES);
							i.putExtra("comensales", Integer.parseInt(cantidad));
							startActivityForResult(i, Util.CANTIDAD_COMENSALES);
							
						}
					}else {
						Util.toastCustom(mContext, " Ingrese un valor, por favor" , Util.TOAST_MENSAJE_ALERTA_MENOR);
					}
					break;
				case Util.CANTIDAD_ARTICULO:
					
					cantidad = edCantConmensales.getText().toString();
					if(!cantidad.equals("")){
						if(Integer.parseInt(cantidad) < 10){
							if(!cantidad.substring(0,1).equals("0")){
								Intent i = new Intent();
								i.putExtra("respuesta", Util.CANTIDAD_ARTICULO);
								i.putExtra("cantidad", cantidad);
								setResult(Teclado.RESULT_OK, i);
								finish();
							}else {
								Util.toastCustom(mContext, " Por favor, ingresé un cantidad valida" , Util.TOAST_MENSAJE_ALERTA_MENOR);
							} 
						}else {
							Intent i = new Intent(mContext, PopUp.class);
							i.putExtra("envia", Util.CANTIDAD_ARTICULO);
							i.putExtra("comensales", Integer.parseInt(cantidad));
							startActivityForResult(i, Util.CANTIDAD_ARTICULO);
							
						}
					}else {
						Util.toastCustom(mContext, " Ingrese un valor, por favor" , Util.TOAST_MENSAJE_ALERTA_MENOR);
					}		
					break;
					case Util.CANTIDAD_OFERTA:
					
					cantidad = edCantConmensales.getText().toString();
					if(!cantidad.equals("")){
						if(Integer.parseInt(cantidad) < 10){
							if(!cantidad.substring(0,1).equals("0")){
								Intent i = new Intent();
								i.putExtra("respuesta", Util.CANTIDAD_OFERTA);
								i.putExtra("cantidad", cantidad);
								setResult(Teclado.RESULT_OK, i);
								finish();
							}else {
								Util.toastCustom(mContext, " Por favor, ingresé un cantidad valida" , Util.TOAST_MENSAJE_ALERTA_MENOR);
								
							} 
						}else {
							Intent i = new Intent(mContext, PopUp.class);
							i.putExtra("envia", Util.CANTIDAD_OFERTA);
							i.putExtra("comensales", Integer.parseInt(cantidad));
							startActivityForResult(i, Util.CANTIDAD_OFERTA);
							
						}
					}else {
						Util.toastCustom(mContext, " Ingrese un valor, por favor" , Util.TOAST_MENSAJE_ALERTA_MENOR);
						
					}		
					break;
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		

		if(resultCode== RESULT_OK){
			
			Intent i = new Intent();
			
			switch (modo) {
			case Util.CANTIDAD_ARTICULO:
				i.putExtra("respuesta", Util.CANTIDAD_ARTICULO);
				break;
			case Util.CANTIDAD_OFERTA:
				i.putExtra("respuesta", Util.CANTIDAD_OFERTA);
				break;
			}
			
			
			i.putExtra("cantidad", cantidad);
			setResult(Teclado.RESULT_OK, i);
			finish();
			
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
