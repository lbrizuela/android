package com.example.restaurant;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.example.api.ApiMesas;
import com.example.api.ApiPedido;
import com.example.clases.Calificacion;
import com.example.clases.ItemPedido;
import com.example.clases.ListaSimple;
import com.example.clases.Pedido;
import com.example.clases.Util;
import com.example.sharedpreferences.SharedPreference;

import complementos.AdaptadorItemPedido;
import complementos.AdaptadorItemPedido.AdapterCallback;
import complementos.AdaptadorItemPedidoRealizados;
import complementos.AdaptadorMesasVincular;
import complementos.AdapterListaSimple;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CarroCompra extends AppCompatActivity implements AdapterCallback{

	public ArrayList<ItemPedido> misListaItemPedidoRealizados;
	public ArrayList<ItemPedido> misListaItemPedidoActuales;
	private SharedPreference instanciaShare;
	private Context mContext;
	protected WakeLock wakelock;
	private AdaptadorItemPedido mAdapterAcuales;
	private AdaptadorItemPedidoRealizados mAdapterRealizados;
	private FrameLayout cargando;

	private float total = 0;
	private float subTotalR = 0;
	private float subTotalA = 0;

	private ImageButton volver;
	private ImageButton aceptarPedido;
	private RecyclerView mRecyclerViewAcuales, mRecyclerViewRealizados;
	private TextView totalPedido, subTotalActuales, subTotalRealizados;
	private LinearLayout llActuales, llRealizados ,items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carro_compra);
		fullScreen();
		mContext = getApplicationContext();
		instanciaShare = new SharedPreference(mContext);
		misListaItemPedidoRealizados = new ArrayList<ItemPedido>();
		misListaItemPedidoActuales = MainActivity.misListaItemPedidoActuales;
		items = (LinearLayout) findViewById(R.id.ll_cc_items);
		cargando = (FrameLayout) findViewById(R.id.ll_cc_cargando);
		volver = (ImageButton) findViewById(R.id.btn_cc_negativo);
		aceptarPedido = (ImageButton) findViewById(R.id.btn_cc_positivo);
		mRecyclerViewAcuales = (RecyclerView) findViewById(R.id.rv_cc_cardListActuales);
		mRecyclerViewRealizados = (RecyclerView) findViewById(R.id.rv_cc_cardListRealizados);
		totalPedido = (TextView) findViewById(R.id.tv_cc_totalpedido);
		subTotalActuales = (TextView) findViewById(R.id.tv_cc_subTotalcardListActuales);

		subTotalRealizados = (TextView) findViewById(R.id.tv_cc_subTotalcardListRealizados);
		llActuales = (LinearLayout) findViewById(R.id.ll_cc_cardListActuales);
		llRealizados = (LinearLayout) findViewById(R.id.ll_cc_cardListRealizados);

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
				
				Intent intent = new Intent(CarroCompra.this , PopUp.class);
				
				if(misListaItemPedidoActuales != null
						&& misListaItemPedidoActuales.size() > 0){
					Toast.makeText(mContext, " Pedir ", Toast.LENGTH_LONG).show();
					intent.putExtra("envia", Util.PEDIR);
					startActivityForResult(intent, Util.PEDIR);
					
				}else{
					Toast.makeText(mContext, "FINALIZAR PEDIDO", Toast.LENGTH_LONG).show();
					intent.putExtra("envia", Util.FINALIZAR_PEDIDO);
					startActivityForResult(intent, Util.FINALIZAR_PEDIDO);
				}
				
			
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
		String idPedido = "";
		String idMozo = "";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			items.setVisibility(View.GONE);
			cargando.setVisibility(View.VISIBLE);
			idPedido= instanciaShare.recuperarIdPedido();
			idMozo= instanciaShare.recuperarIdMozo();

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

			misListaItemPedidoRealizados = MainActivity.misListaItemPedidoRealizados;
			
			if (!respuesta.equals(ApiPedido.OK)) {

				Toast.makeText(mContext, "Error: " + respuesta,Toast.LENGTH_LONG).show();

			}

			setarValoresPantalla();
		}

	}
	
	
	private class RealizarItemPedido extends AsyncTask<Void, Void, Void> {

		String respuesta = "";
		String idPedido = "";
		String idMozo = "";
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			items.setVisibility(View.GONE);
			cargando.setVisibility(View.VISIBLE);
			idPedido= instanciaShare.recuperarIdPedido();
			idMozo= instanciaShare.recuperarIdMozo();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			respuesta = ApiPedido.realizarPedido(misListaItemPedidoActuales, idMozo, idPedido);
			
			return null;
		}

		

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			cargando.setVisibility(View.GONE);
			items.setVisibility(View.VISIBLE);
			if (!respuesta.equals(ApiPedido.OK)) {

				Util.toastCustom(mContext, "Error: " + respuesta, Util.TOAST_MENSAJE_ALERTA_MENOR);
				

			}else{
				Util.toastCustom(mContext, "El pedido fue realizado con exito!!", Util.TOAST_MENSAJE_EXITOSO);
				MainActivity.misListaItemPedidoActuales =  new ArrayList<ItemPedido>();
				aceptarPedido.setImageDrawable(getResources().getDrawable(R.drawable.icono_finalizar_pedido_negre));
				llActuales.setVisibility(View.GONE);
			}
			finish();
		}
		
		
		
	}

	public void setarValoresPantalla() {

		if (isPedido()) {
			
			cargando.setVisibility(View.GONE);
			items.setVisibility(View.VISIBLE);
		
			if (misListaItemPedidoActuales != null && misListaItemPedidoActuales.size() > 0) {

				aceptarPedido.setImageDrawable(getResources().getDrawable(R.drawable.icono_okey));
				llActuales.setVisibility(View.VISIBLE);
				setupRecyclerActuales();
				
				for (int i = 0; i < misListaItemPedidoActuales.size(); i++) {

					subTotalA = subTotalA + Float.valueOf((misListaItemPedidoActuales.get(i).getPrecioUnitario() * misListaItemPedidoActuales.get(i).getCantidad()));
				} 
				
				String preciototal = String.format("%.2f", subTotalA);

				subTotalActuales.setText(getResources().getString(R.string.subtotal_actual)+" "  + preciototal);

			} else {
				
				aceptarPedido.setImageDrawable(getResources().getDrawable(R.drawable.icono_finalizar_pedido_negre));
				llActuales.setVisibility(View.GONE);
			}

			if (misListaItemPedidoRealizados != null && misListaItemPedidoRealizados.size() > 0) {

				llRealizados.setVisibility(View.VISIBLE);
				setupRecyclerRealizados();
				
				for (int i = 0; i < misListaItemPedidoRealizados.size(); i++) {

					subTotalR = subTotalR+ Float.valueOf((misListaItemPedidoRealizados.get(i).getPrecioUnitario() * misListaItemPedidoRealizados.get(i).getCantidad()));
				}
				
				String preciototalR = String.format("%.2f", subTotalR);

				subTotalRealizados.setText(getResources().getString(R.string.subtotal_realizado)+" "  + preciototalR);

			} else {
				llRealizados.setVisibility(View.GONE);

			}

			recalcularTotal();

		} else {

			Util.toastCustom(mContext, "No item ingresado", Util.TOAST_MENSAJE_INFO);
			finish();

		}
	}

	public boolean isPedido() {

		boolean respuesta = true;

		if (misListaItemPedidoActuales == null ||  misListaItemPedidoActuales.size() == 0)
			if(misListaItemPedidoRealizados == null || misListaItemPedidoRealizados.size() == 0) 
                 
				   respuesta = false;

		if(misListaItemPedidoRealizados == null || misListaItemPedidoRealizados.size() == 0) 
			if (misListaItemPedidoActuales == null ||  misListaItemPedidoActuales.size() == 0)
				respuesta = false;

		return respuesta;
	}

	private void recalcularTotal() {

		total = subTotalR + subTotalA;
		
		String preciototal = String.format("%.2f", total);
		totalPedido.setText(getResources().getString(R.string.total_pedido)+" "+ preciototal);
	}

	/*
	 * private void setupRecycler() { mRecyclerView.setVisibility(View.VISIBLE);
	 * mAdapter = new AdaptadorItemPedido(mContext, misListaItemPedido);
	 * LayoutManager layoutManager = new LinearLayoutManager(this);
	 * mRecyclerView.setLayoutManager(layoutManager);
	 * mRecyclerView.setAdapter(mAdapter); }
	 */

	private void setupRecyclerRealizados() {
		mRecyclerViewRealizados.setVisibility(View.VISIBLE);
		mAdapterRealizados = new AdaptadorItemPedidoRealizados(mContext, misListaItemPedidoRealizados);
		LayoutManager layoutManager = new LinearLayoutManager(this);
		mRecyclerViewRealizados.setLayoutManager(layoutManager);
		mRecyclerViewRealizados.setAdapter(mAdapterRealizados);
	}

	private void setupRecyclerActuales() {

		mRecyclerViewAcuales.setVisibility(View.VISIBLE);
		mAdapterAcuales = new AdaptadorItemPedido(mContext, misListaItemPedidoActuales ,this);
		LayoutManager layoutManager = new LinearLayoutManager(this);
		mRecyclerViewAcuales.setLayoutManager(layoutManager);
		mRecyclerViewAcuales.setAdapter(mAdapterAcuales);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		
		if(resultCode== RESULT_OK){
			int resultado = data.getIntExtra("envia",0);

			if (resultado==Util.FINALIZAR_PEDIDO) {
				
				Intent i = new Intent(CarroCompra.this , AgregarCalificacion.class);
				startActivity(i);
	        	finish();
				
			}else if(resultado==Util.PEDIR){
				
				
				new RealizarItemPedido().execute();
				
			}
		}
		
		
	}
	
	@Override
	public void onMethodCallback() {
		// do something
		if (isPedido()) {
			if (misListaItemPedidoActuales != null
					&& misListaItemPedidoActuales.size() > 0) {

				aceptarPedido.setImageDrawable(getResources().getDrawable(R.drawable.icono_okey));
				llActuales.setVisibility(View.VISIBLE);
				subTotalA=0;
				for (int i = 0; i < misListaItemPedidoActuales.size(); i++) {

					subTotalA = subTotalA
							+ Float.valueOf((misListaItemPedidoActuales.get(i)
									.getPrecioUnitario() * misListaItemPedidoActuales
									.get(i).getCantidad()));
				}
			

				subTotalActuales.setText(getResources().getString(
						R.string.subtotal_actual)
						+ " " + String.valueOf(subTotalA));
				
				recalcularTotal();

			} else {
				aceptarPedido.setImageDrawable(getResources().getDrawable(
						R.drawable.icono_finalizar_pedido_negre));
				llActuales.setVisibility(View.GONE);
			}

		} else {
			finish();
		}

	}

	
	
}
