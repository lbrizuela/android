package com.example.restaurant;

import java.util.ArrayList;

import com.example.api.ApiMesas;
import com.example.api.ApiPedido;
import com.example.clases.ItemPedido;
import com.example.clases.ListaSimple;
import com.example.sharedpreferences.SharedPreference;

import complementos.AdaptadorItemPedido;
import complementos.AdaptadorMesasVincular;
import complementos.AdapterListaSimple;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class CarroCompra extends Activity {

	
	 public static ArrayList<ItemPedido> misListaItemPedido;
	 private SharedPreference instanciaShare;
     private Context mContext;
     protected WakeLock wakelock;
     private AdaptadorItemPedido  mAdapter;
     private FrameLayout items, cargando;
 	 

 	private Button volver; 
 	private Button aceptarPedido;
 	private RecyclerView mRecyclerView;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carro_compra);
		fullScreen();
		mContext = getApplicationContext();
		instanciaShare = new SharedPreference(mContext);
		misListaItemPedido= new ArrayList<ItemPedido>();
		items = (FrameLayout) findViewById(R.id.ll_cc_items);
		cargando = (FrameLayout) findViewById(R.id.ll_cc_cargando);
		volver = (Button)findViewById(R.id.btn_cc_negativo);
		aceptarPedido = (Button)findViewById(R.id.btn_cc_positivo);
		mRecyclerView = (RecyclerView) findViewById(R.id.rv_cc_cardList);
		new BuscarItemPedido().execute();
	
		
		
	
		volver.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		aceptarPedido.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		fullScreen();
		
		
		
	    
	}
	
	
     
	
	
	
	
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	 private void fullScreen() {
			try {
				// HIDE STATUS BAR Y BOTONES VIRTUALES
				CarroCompra.this
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
	
	 
	 private class BuscarItemPedido extends AsyncTask<Void, Void, Void> {

			String respuesta = "";
			String idPedido = "2";
			String idMozo = "1";

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				items.setVisibility(View.GONE);
				cargando.setVisibility(View.VISIBLE);
			//	idPedido= instanciaShare.recuperarIdPedido();
			//	idMozo= instanciaShare.recuperarIdMozoPedido();
			
			}

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stu

				respuesta = ApiPedido.buscarItemsPedido(idPedido, idMozo);

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);

				if (respuesta.equals(ApiPedido.OK)) {
					
					cargando.setVisibility(View.GONE);
					items.setVisibility(View.VISIBLE);
					setupRecycler();
					
				} else {

					Toast.makeText(mContext, "Error: " + respuesta,
							Toast.LENGTH_LONG).show();
					finish();
					
					
				}
			}

		}
	 
	 
	 private void setupRecycler() {
		    mRecyclerView.setVisibility(View.VISIBLE);
			mAdapter = new AdaptadorItemPedido(mContext, misListaItemPedido);
			LayoutManager layoutManager = new LinearLayoutManager(this);
			mRecyclerView.setLayoutManager(layoutManager);
	        mRecyclerView.setAdapter(mAdapter);
	     }
	
	
}
