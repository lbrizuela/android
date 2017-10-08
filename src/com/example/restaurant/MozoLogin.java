package com.example.restaurant;

import com.example.api.ApiMozo;
import com.example.api.ManagerApi;
import com.example.clases.CustomKeyboard;
import com.example.clases.Util;
import com.example.sharedpreferences.SharedPreference;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MozoLogin extends Activity {
	
	private EditText idMozo;
	private TextView clikeableTeclado;
	private Context mContext;
	private ImageButton botonOk, botonBlack;
	private SharedPreference instanciaShare;
	protected WakeLock wakelock;
	private Views views;
    private boolean vistas = true;
    public int request_code = 1;
    public String codigoSeguridad="";
    private ProgressBar pgDone, pgVolver;
    private Keyboard mKeyboard;
    private CustomKeyboard mCustomKeyboard;
    private LinearLayout ll_teclado , ll_teclado_completo;
    private View view;
    

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mozo_loguin);
		
		mContext= getApplicationContext();
		view = new View(mContext);
		
		
		
		instanciaShare= new SharedPreference(mContext);
		views = Views.getInstance(getApplicationContext());
		idMozo = (EditText) findViewById(R.id.keyboard_ml_texto);
		botonOk= (ImageButton)findViewById(R.id.mozo_btn_aceptar);
		botonBlack= (ImageButton) findViewById(R.id.mozo_btn_volver);
		pgVolver = (ProgressBar) findViewById(R.id.mozo_pg_volver);
		pgDone = (ProgressBar) findViewById(R.id.mozo_pg_aceptar);
		ll_teclado = (LinearLayout)findViewById(R.id.ll_teclado);
		ll_teclado_completo = (LinearLayout)findViewById(R.id.ll_teclado);
		
		
		idMozo.setShowSoftInputOnFocus(false);
		
		
		idMozo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ll_teclado_completo.setGravity(Gravity.BOTTOM);
				
				ll_teclado.setVisibility(View.VISIBLE);
				mCustomKeyboard = new CustomKeyboard(MozoLogin.this, R.id.keyboardview_ml, R.xml.qwert2);

				mCustomKeyboard.registerEditText(R.id.keyboard_ml_texto);

				
			}
		});
		botonBlack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent i = new Intent(mContext, Teclado.class);
				i.putExtra("enviar", Util.SALIR);
				startActivityForResult(i,request_code);
				
			}
		});
		
		botonOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!idMozo.getText().toString().equals("")) {
					codigoSeguridad = idMozo.getText().toString();
					bloquearPantalla(true);
					new RestMozo().execute();

				}
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
			views.agregarViewNavigationBar();
		    views.agregarViewStatusBar();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	

    
    
    
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		int resultado = 0;
		if (resultCode == RESULT_OK) {
			resultado = intent.getIntExtra("clave", 0);

			if (resultado == Util.ID_MOZO) {
				String id = intent.getStringExtra("resultado");
				codigoSeguridad = id;
				idMozo.setText(id);
			} else if (resultado == Util.SALIR) {

				boolean salir = intent.getBooleanExtra("resultado", false);
				if (salir) {
					
					instanciaShare.limpiarIPv6();

					Views views = Views.getInstance(mContext);
					views.removerViewStatusBar();
					views.removerViewNavegationBar();

					finish();

				} else {
					Toast.makeText(mContext, "CODIGO INCORRECTO",
							Toast.LENGTH_LONG).show();
				}

			}
		}

		
	}
	
	public void bloquearPantalla(boolean bloqueo){
		
		if(bloqueo){
			pgDone.setVisibility(View.VISIBLE);
			pgVolver.setVisibility(View.VISIBLE);
			idMozo.setEnabled(false);
			botonOk.setVisibility(View.GONE);
			botonBlack.setVisibility(View.GONE);
		}else {
			botonBlack.setVisibility(View.VISIBLE);
			botonOk.setVisibility(View.VISIBLE);
			idMozo.setEnabled(true);
			pgVolver.setVisibility(View.GONE);
			pgDone.setVisibility(View.GONE);
		}
		
		
	}
	
	
	
	private class RestMozo extends AsyncTask<Void, Void, Void> {

		
		public String respuesta=ApiMozo.OK; 
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stu
			respuesta = ApiMozo.recuperarMozo(instanciaShare,codigoSeguridad);
			
			
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(respuesta.equals(ApiMozo.OK)){
				
				Intent intent = new Intent(mContext, MainActivity.class);
				startActivity(intent);
				finish();
			}else {
				Toast.makeText(mContext, "OCURRIO UN ERROR: " + respuesta, Toast.LENGTH_LONG).show();;
				bloquearPantalla(false);
			}
		}

		
	}



	
	private void fullScreen() {
		try {
			// HIDE STATUS BAR Y BOTONES VIRTUALES
			MozoLogin.this
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




	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
	
	

}
