package com.example.restaurant;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.api.ApiMesas;
import com.example.api.ApiMozo;
import com.example.api.ApiPedido;
import com.example.api.ManagerApi;
import com.example.clases.CustomKeyboard;
import com.example.clases.ListaSimple;
import com.example.clases.Mesa;
import com.example.sharedpreferences.SharedPreference;

import complementos.AdaptadorMesasVincular;
import complementos.AdapterListaSimple;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class EmprezarPedido extends Activity {

	private static final String TAG = "EmprezarPedido";
	private SharedPreference instanciaShare;
	private Context mContext;
	protected WakeLock wakelock;
	private Views views;
	private boolean vistas = true;
	private ListView  lvListadoDesocupadas;

	private AdapterListaSimple adapterDesocupado;
	private ArrayList<ListaSimple> misLista;
	private ArrayList<Mesa> misListaSinSeleccionado;
	private int totFilas, totFilasDesocupado;
	private Button botonVincular;

	private ImageButton volver, borrarMesaPadre, btnAceptar;
	private LinearLayout llPadre;
	private TextView tvMesaPadre;
	private EditText edCantConmensales;
	private Mesa seleccionada;
	private CustomKeyboard mCustomKeyboard;
	private FrameLayout flCargando, flEmpezarPedido;
	private ProgressBar progressAceptar;
	

	public static ArrayList<Mesa> mesasLibres;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empezar_pedido);

		mContext = getApplicationContext();
		instanciaShare = new SharedPreference(mContext);
		views = Views.getInstance(getApplicationContext());
		mesasLibres = new ArrayList<Mesa>();
		
		flCargando = (FrameLayout) findViewById(R.id.fl_cargando);
		flEmpezarPedido = (FrameLayout) findViewById(R.id.fl_empeza_perdido);
		
		flEmpezarPedido.setVisibility(View.GONE);
		flCargando.setVisibility(View.VISIBLE);
		new RestMesas().execute();

		lvListadoDesocupadas = (ListView) findViewById(R.id.lv_ep_mesas_desocupadas);
		botonVincular = (Button) findViewById(R.id.btn_ep_vincular);
		mRecyclerView = (RecyclerView) findViewById(R.id.rv_ep_cardList);
		btnAceptar = (ImageButton) findViewById(R.id.imgbtn_ep_aceptar);
		volver = (ImageButton) findViewById(R.id.imgbtn_ep_salir);
		borrarMesaPadre = (ImageButton) findViewById(R.id.imgbtn_ep_borrar_padre);
		llPadre = (LinearLayout) findViewById(R.id.ll_ep_mostrar_padre);
		tvMesaPadre = (TextView) findViewById(R.id.tv_ep_mesa_padre_seccionada);
		progressAceptar =(ProgressBar) findViewById(R.id.progres_ep_aceptar);
		

		// /buscarListaDescupados();
		seleccionada = null;

		edCantConmensales = (EditText) findViewById(R.id.keyboard_texto_chico);
		mCustomKeyboard = new CustomKeyboard(this, R.id.keyboardviewchico, R.xml.qwerty_chico);

		mCustomKeyboard.registerEditText(R.id.keyboard_texto_chico);

		lvListadoDesocupadas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Log.e("LUISINA", "Posicion " + position);
				seleccionada = mesasLibres.get(position);
				lvListadoDesocupadas.setVisibility(View.GONE);
				mRecyclerView.setVisibility(View.GONE);
				botonVincular.setVisibility(View.VISIBLE);
				llPadre.setVisibility(View.VISIBLE);
				tvMesaPadre.setText(getResources().getString(
						R.string.mesa_padre)
						+ " " + seleccionada.getNroMesa());
			}

		});

		botonVincular.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (mRecyclerView.getVisibility() == View.GONE) {
					listarMesasRestantes();
				} else if (mRecyclerView.getVisibility() == View.VISIBLE) {
					mRecyclerView.setVisibility(View.GONE);

				}

			}
		});

		borrarMesaPadre.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				seleccionada = null;
				listarMesas();
				mRecyclerView.setVisibility(View.GONE);
				botonVincular.setVisibility(View.GONE);
				llPadre.setVisibility(View.GONE);
			}
		});

		volver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				instanciaShare.limpiarLoginMozo();
				Intent i = new Intent(mContext, MainActivity.class);
				startActivity(i);
				finish();

			}
		});

		btnAceptar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (seleccionada != null) {
					if (!edCantConmensales.getText().equals("")) {

						
						ArrayList<Mesa> marcados = mAdapter.obtenerSeleccionados();
			            String contenidoMarcados = "Marcados: ";
			            for (Mesa os : marcados){
			                    contenidoMarcados += os.getNroMesa() + ", ";
			            }
			            Log.e(TAG, contenidoMarcados);   
						
						/*instanciaShare.insertarIdPedido(1);
						instanciaShare
								.insertarCantidadComensales(cantidadComensales);*/
			            
			            
			            new IniciarPedido().execute();
			           

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

	public void listarMesasRestantes() {

		misListaSinSeleccionado = new ArrayList<Mesa>();
		for (int i = 0; i < mesasLibres.size(); i++) {
			if (!mesasLibres.get(i).equals(seleccionada)) {
				
				misListaSinSeleccionado.add(mesasLibres.get(i));
			}
		}

		if (misListaSinSeleccionado != null && misListaSinSeleccionado.size() > 0) {
			 setupRecycler();
		}

	}

	public void listarMesas() {

		misLista = new ArrayList<ListaSimple>();
		ListaSimple object;
		for (int i = 0; i < mesasLibres.size(); i++) {
			object = new ListaSimple(mesasLibres.get(i).getIdMesa(),
					"Numero Mesa: "
							+ String.valueOf(mesasLibres.get(i).getNroMesa()));
			misLista.add(object);
		}

		if (misLista != null && misLista.size() > 0) {
			adapterDesocupado = new AdapterListaSimple(mContext, misLista);
			lvListadoDesocupadas.setVisibility(View.VISIBLE);
			lvListadoDesocupadas.setAdapter(adapterDesocupado);
			totFilasDesocupado = misLista.size();
		}

	}

	private class RestMesas extends AsyncTask<Void, Void, Void> {

		String respuesta = "";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
		
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stu

			respuesta = ApiMesas.recuperarEstadoMesas(ApiMesas.ESTADO_MESA_LIBRE);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (respuesta.equals(ApiMesas.OK)) {
				
				flEmpezarPedido.setVisibility(View.VISIBLE);
				flCargando.setVisibility(View.GONE);
				lvListadoDesocupadas.setVisibility(View.VISIBLE);
				mRecyclerView.setVisibility(View.GONE);
				botonVincular.setVisibility(View.GONE);
				listarMesas();
				
			} else {

				Toast.makeText(mContext, "Error: " + respuesta,
						Toast.LENGTH_LONG).show();
				instanciaShare.limpiarLoginMozo();
				Intent i = new Intent(mContext, MainActivity.class);
				startActivity(i);
				finish();
			}
		}

	}
	
	private class IniciarPedido extends AsyncTask<Void, Void, Void> {

		String cantidadComensales;
		String idMesaPadre;
		String idMozo;
		String respuesta = "";
		
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			cantidadComensales =  edCantConmensales.getText().toString();
			idMesaPadre = String.valueOf(seleccionada.getIdMesa());
			idMozo = instanciaShare.recuperarIdMozo();
			flEmpezarPedido.setVisibility(View.GONE);
			flCargando.setVisibility(View.VISIBLE);
			
			
		}


		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			//respuesta = ApiPedido.iniciarPedidoActual(instanciaShare, idMozo, idMesaPadre, cantidadComensales);
			
			instanciaShare.insetarPedido("2", "1", "7", 7);
			respuesta= ApiPedido.OK;
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if (respuesta.equals(ApiPedido.OK)) {
				
				Intent i = new Intent(mContext, MainActivity.class);
				startActivity(i);
				finish();
				
			} else {

				Toast.makeText(mContext, "Error: " + respuesta,
						Toast.LENGTH_LONG).show();
				flEmpezarPedido.setVisibility(View.VISIBLE);
				flCargando.setVisibility(View.GONE);
			}
		}
		
		
	}
	
	
	private RecyclerView mRecyclerView;

	private AdaptadorMesasVincular mAdapter;
	
	private void setupRecycler() {
		mRecyclerView.setVisibility(View.VISIBLE);
		mAdapter = new AdaptadorMesasVincular(mContext, misListaSinSeleccionado);
		LayoutManager layoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
     }

	private void mantenerPantallaEncendida() {
		this.wakelock = ((PowerManager) getSystemService("power")).newWakeLock(
				10, "etiqueta");
		this.wakelock.acquire();
	}

}
