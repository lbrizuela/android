package com.example.restaurant;

import java.util.ArrayList;

import com.example.clases.CustomKeyboard;
import com.example.clases.ListaSimple;
import com.example.sharedpreferences.SharedPreference;

import complementos.AdapterListaSimple;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;


public class EmprezarPedido extends Activity {

	
	private SharedPreference instanciaShare;
	private Context mContext;
	protected WakeLock wakelock;
	private Views views;
    boolean vistas = true;
    public ListView lvListado, lvListadoDesocupadas;
    public AdapterListaSimple adapter , adapterDesocupado;
    public ArrayList<ListaSimple> misLista , misListaDesocupadas;
    public  int totFilas , totFilasDesocupado;
    public Button botonVincular;
   
    public ImageButton volver, borrarMesaPadre, btnAceptar;
    public LinearLayout llPadre;
    public TextView tvMesaPadre;
    public EditText edCantConmensales;
    public ListaSimple seleccionada;
    private CustomKeyboard mCustomKeyboard;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.empezar_pedido);
	
		mContext= getApplicationContext();
		instanciaShare= new SharedPreference(mContext);
		views = Views.getInstance(getApplicationContext());
		
		lvListadoDesocupadas=(ListView)findViewById(R.id.lv_ep_mesas_desocupadas);
		botonVincular =(Button)findViewById(R.id.btn_ep_vincular);
		lvListado=(ListView)findViewById(R.id.lv_total_mesas);
		btnAceptar =(ImageButton)findViewById(R.id.imgbtn_ep_aceptar);
		volver = (ImageButton)findViewById(R.id.imgbtn_ep_salir);
		borrarMesaPadre = (ImageButton)findViewById(R.id.imgbtn_ep_borrar_padre);
		llPadre = (LinearLayout)findViewById(R.id.ll_ep_mostrar_padre);
		tvMesaPadre = (TextView)findViewById(R.id.tv_ep_mesa_padre_seccionada);
		
		lvListadoDesocupadas.setVisibility(View.VISIBLE);
		lvListado.setVisibility(View.GONE);
		botonVincular.setVisibility(View.GONE);
		buscarListaDescupados();
		seleccionada=null;
		
		edCantConmensales = (EditText) findViewById(R.id.keyboard_texto_chico);
		mCustomKeyboard = new CustomKeyboard(this, R.id.keyboardviewchico, R.xml.qwerty_chico);

		mCustomKeyboard.registerEditText(R.id.keyboard_texto_chico);
		
		
		lvListadoDesocupadas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				Log.e("LUISINA", "Posicion " + position);
				seleccionada = misListaDesocupadas.get(position);
				lvListadoDesocupadas.setVisibility(View.GONE);
				lvListado.setVisibility(View.GONE);
				botonVincular.setVisibility(View.VISIBLE);
				llPadre.setVisibility(View.VISIBLE);
				tvMesaPadre.setText(getResources().getString(R.string.mesa_padre)+ " " + seleccionada.getDescripcion());
			}
			
			
			
		});
		
		botonVincular.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (lvListado.getVisibility() == View.GONE) {
					buscarLista();
				} else { if (lvListado.getVisibility() == View.VISIBLE) {
						lvListado.setVisibility(View.GONE);
					}

				}

			}
		});
		
		borrarMesaPadre.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				seleccionada=null;
				buscarListaDescupados();
				lvListado.setVisibility(View.GONE);
				botonVincular.setVisibility(View.GONE);
				llPadre.setVisibility(View.GONE);
			}
		});
		
		volver.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				instanciaShare.insertarIdMozo("");
				Intent i= new Intent(mContext, MainActivity.class);
				startActivity(i);
				finish();
				
				
			}
		});
		
		
		btnAceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				if (seleccionada != null){
					if(!edCantConmensales.getText().equals("")){
						
						
					}
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
			EmprezarPedido.this
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
	
	public void buscarListaDescupados(){
		
		misListaDesocupadas= new ArrayList<ListaSimple>();
		ListaSimple object= new ListaSimple(1, "mesa 1");
		misListaDesocupadas.add(object);
		object= new ListaSimple(4, "mesa 4");
		misListaDesocupadas.add(object);		
		if (misListaDesocupadas != null && misListaDesocupadas.size() > 0) {
            adapterDesocupado = new AdapterListaSimple(mContext, misListaDesocupadas);
            lvListadoDesocupadas.setVisibility(View.VISIBLE);
            lvListadoDesocupadas.setAdapter(adapterDesocupado);
            totFilasDesocupado = misListaDesocupadas.size();
        }
		
	}
	
	public void buscarLista(){
		misLista = new ArrayList<ListaSimple>();
		ListaSimple object= new ListaSimple(1, "mesa 1");
		misLista.add(object);
		object= new ListaSimple(2, "mesa 2");
		misLista.add(object);
		object= new ListaSimple(3, "mesa 3");
		misLista.add(object);
		object= new ListaSimple(4, "mesa 4");
		misLista.add(object);
		if (misLista != null && misLista.size() > 0) {
            adapter = new AdapterListaSimple(mContext, misLista);
            lvListado.setVisibility(View.VISIBLE);
            lvListado.setAdapter(adapter);
            totFilas = misLista.size();
        }
		
		
		
	}
	
	
	
	
	
	private void mantenerPantallaEncendida() {
	        this.wakelock = ((PowerManager) getSystemService("power")).newWakeLock(10, "etiqueta");
	        this.wakelock.acquire();
	    }
	

}
