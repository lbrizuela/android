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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class CarroCompra extends Activity {

	
	 public static ArrayList<ItemPedido> misListaItemPedido;
	 private SharedPreference instanciaShare;
     private Context mContext;
     protected WakeLock wakelock;
     private AdaptadorItemPedido  mAdapter;
 	 

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
		
		volver = (Button)findViewById(R.id.btn_cc_negativo);
		aceptarPedido = (Button)findViewById(R.id.btn_cc_positivo);
		mRecyclerView = (RecyclerView) findViewById(R.id.rv_cc_cardList);
	
		
		
	/*	lvListadoItemPedido.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				ItemPedido itemSeleccionado = misListaItemPedido.get(position);
				Log.e("Luisina", "POS " + position);
				
			}
		});
		*/
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
			String idPedido;
			String idMozo;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				
			
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
					
					setupRecycler();
					
				} else {

					Toast.makeText(mContext, "Error: " + respuesta,
							Toast.LENGTH_LONG).show();
					
					
				}
			}

		}
	 
	 
	 private void setupRecycler() {
			
			mAdapter = new AdaptadorItemPedido(mContext, misListaItemPedido);
			LayoutManager layoutManager = new LinearLayoutManager(this);
			mRecyclerView.setLayoutManager(layoutManager);
	        mRecyclerView.setAdapter(mAdapter);
	     }
	
	
}
