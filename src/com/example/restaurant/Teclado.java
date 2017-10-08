package com.example.restaurant;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.clases.CustomKeyboard;
import com.example.clases.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;




public class Teclado extends Activity {

	private ImageButton btnAceptar, btnVolver;
	private EditText textoIngresa;
	private TextView titulo;

	private Context mContext;

	private CustomKeyboard mCustomKeyboard;
	
	private int  modo = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState
	        ) {
		super.onCreate(savedInstanceState); 
		
		
		setContentView(R.layout.teclado);
		mContext = getApplicationContext();

		btnVolver = (ImageButton) findViewById(R.id.keyboard_btn_atras);
		btnAceptar = (ImageButton) findViewById(R.id.keyboard_btn_listo);
		textoIngresa = (EditText) findViewById(R.id.keyboard_texto);
		titulo = (TextView) findViewById(R.id.keyboard_ingrese);

		
		
		Bundle extras = getIntent().getExtras();
		modo = extras.getInt("enviar");	
		
		switch (modo) {
		case Util.ID_MOZO:
			titulo.setText("Ingrese Id Mozo:");	
			
			break;
			
		case Util.SALIR:
			titulo.setText("Ingrese clave:");
			
			break;
		
	    case Util.FINALIZAR:
			titulo.setText("Ingrese clave Finalizar Pedido:");
			
			
			break;
			
	    case Util.IPv4:
			titulo.setText("Ingresar");	
			break;
		}
		
		/*case Util.TECLADO_EMAIL_EMPRESA:
			titulo.setText("Ingrese e-mail empresa:");			
			break;
		case Util.TECLADO_MENSAJE_LIBRE:
			titulo.setText("Ingrese mensaje:");			
			break;
		case Util.TECLADO_SELECCIONA:
			titulo.setText("Buscar:");			
			break;
		case Util.TECLADO_SELECCIONA_RECYCLER:
			titulo.setText("Buscar:");			
			break;
			
		default:
			break;
		}
*/
		// Create the Keyboard
		// Keyboard mKeyboard= new Keyboard(mContext, R.xml.qwerty);
		//
		// // Lookup the KeyboardView
		// KeyboardView mKeyboardView = (KeyboardView)
		// findViewById(R.id.keyboardview);
		// // Attach the keyboard to the view
		// mKeyboardView.setKeyboard(mKeyboard);
		// // Do not show the preview balloons
		// mKeyboardView.setPreviewEnabled(false);
		//
		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		mCustomKeyboard = new CustomKeyboard(this, R.id.keyboardview, R.xml.qwerty);

		mCustomKeyboard.registerEditText(R.id.keyboard_texto);

		btnAceptar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				String textoIngresado = textoIngresa.getText().toString().trim();
				Intent i;
				switch (modo) {
				case Util.ID_MOZO:
					if (!textoIngresado.equals("")) {

						i = new Intent();
						i.putExtra("clave", Util.ID_MOZO);
						i.putExtra("resultado", textoIngresado);
						setResult(Teclado.RESULT_OK, i);
						finish();
						
					} else {

						Toast.makeText(mContext, "Por favor, Ingese un Id",
								Toast.LENGTH_LONG).show();
					}

					break;
				case Util.SALIR:
					if (!textoIngresado.equals("")) {
						 i = getIntent();
						i.putExtra("clave", Util.SALIR);
						if (verificarClaveSalir(textoIngresado)) {

							i.putExtra("resultado", true);

						} else {

							i.putExtra("resultado", false);
						}
						setResult(RESULT_OK, i);
						finish();
					} else {

						Toast.makeText(mContext, "Por favor, una clave ",
								Toast.LENGTH_LONG).show();
						/*
						 * Util.toastCustom( mContext, getResources().getString(
						 * R.string.numero_mac_invalido),
						 * Util.TOAST_MENSAJE_ADVERTENCIA);
						 */
					}

					break;
					
				case Util.FINALIZAR:
					if (!textoIngresado.equals("")) {
						 i = getIntent();
						i.putExtra("clave", Util.FINALIZAR);
						if (verificarClaveSalir(textoIngresado)) {

							i.putExtra("resultado", true);

						} else {

							i.putExtra("resultado", false);
						}
						setResult(RESULT_OK, i);
						finish();
					} else {

						Toast.makeText(mContext, "Por favor, una clave ",
								Toast.LENGTH_LONG).show();
						/*
						 * Util.toastCustom( mContext, getResources().getString(
						 * R.string.numero_mac_invalido),
						 * Util.TOAST_MENSAJE_ADVERTENCIA);
						 */
					}

					break;
					
					
				case Util.IPv4:
					if (!textoIngresado.equals("")) {

						i = new Intent();
						i.putExtra("clave", Util.IPv4);
						i.putExtra("resultado", textoIngresado);
						setResult(Teclado.RESULT_OK, i);
						finish();
						
					} else {

						Toast.makeText(mContext, "Por favor, Ingese un IPv4", Toast.LENGTH_LONG).show();
					}

					break;
				}	
				/*case Util.TECLADO_EMAIL_EMPRESA:					
					if (!textoIngresado.equals("") && isMailValido(textoIngresado)) {
						Intent i = getIntent();
						i.putExtra("ingresado", textoIngresado);
						setResult(RESULT_OK, i);
						finish();
					} else {
						Util.toastCustom(
								mContext,
								getResources().getString(
										R.string.email_invalido),
								Util.TOAST_MENSAJE_ADVERTENCIA);
					}
					break;

				default:
					Intent i = getIntent();
					i.putExtra("ingresado", textoIngresado);
					setResult(RESULT_OK, i);
					finish();

					break;
				}

			*/}
		});

		btnVolver.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i= new Intent();
				setResult(Teclado.RESULT_CANCELED, i);
				finish();
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		fullScreen();
	}

	private void fullScreen() {
		try {
			// HIDE STATUS BAR Y BOTONES VIRTUALES
			Teclado.this
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
	
	public boolean verificarClaveSalir(String textoIngresado){
		Calendar calendario= Calendar.getInstance();
		
		
		
		String dia= String.format("%02d", new Object[] { Integer.valueOf(calendario.get(Calendar.DAY_OF_MONTH) ) });
		String mes= String.format("%02d", new Object[] { Integer.valueOf(calendario.get(Calendar.MONTH) + 1) });
		
		String clave = mes+dia;
		
		if(clave.equals(textoIngresado)){
			return true;
		}else {
			return false;
		}
		
		
		
		
	}
	
	private boolean isValidMac(String mac) { 
        Pattern p = Pattern.compile("[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}");
        Matcher m = p.matcher(mac);
        return m.matches();
    }
	
	private boolean isMailValido(CharSequence target) {
		if (TextUtils.isEmpty(target)) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}

//	@Override
//	public void onBackPressed() {
		// NOTE Trap the back key: when the CustomKeyboard is still visible hide
		// it, only when it is invisible, finish activity
//		if (mCustomKeyboard.isCustomKeyboardVisible())
//			mCustomKeyboard.hideCustomKeyboard();
//		else
//			this.finish();
//	}

}