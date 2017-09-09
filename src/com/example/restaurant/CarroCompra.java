package com.example.restaurant;

import java.util.ArrayList;

import com.example.clases.ItemPedido;
import com.example.clases.ListaSimple;
import com.example.sharedpreferences.SharedPreference;

import complementos.AdapterListaConIconos;
import complementos.AdapterListaSimple;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class CarroCompra extends Activity {

	
	 public ArrayList<ItemPedido> misListaItemPedido;
	 private SharedPreference instanciaShare;
     private Context mContext;
     protected WakeLock wakelock;
 	 
 	 public AdapterListaConIconos adapterItem;
 	public ListView lvListadoItemPedido; 
 	public Button volver; 
 	public Button aceptarPedido;
	 
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
		lvListadoItemPedido = (ListView) findViewById(R.id.lv_cc_items);
		buscarItems();
		lvListadoItemPedido.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				ItemPedido itemSeleccionado = misListaItemPedido.get(position);
				Log.e("Luisina", "POS " + position);
				
			}
		});
		
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
	
	
     
	
	public void buscarItems(){
		
		misListaItemPedido= new ArrayList<ItemPedido>();
		ItemPedido object= new ItemPedido(1, "Item 1" , 2);
		misListaItemPedido.add(object);
		object= new ItemPedido(2, "Item 2" , 1);
		misListaItemPedido.add(object);
		object= new ItemPedido(3, "Item 3" , 6);
		misListaItemPedido.add(object);		
		
		if (misListaItemPedido != null && misListaItemPedido.size() > 0) {
            adapterItem = new AdapterListaConIconos(mContext, misListaItemPedido);
            lvListadoItemPedido.setVisibility(View.VISIBLE);
            lvListadoItemPedido.setAdapter(adapterItem);
           /// totFilasDesocupado = misListaItemPedido.size();
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
	
	
	
}
