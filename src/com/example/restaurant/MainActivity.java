package com.example.restaurant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.api.ApiMenus;
import com.example.api.ManagerApi;
import com.example.clases.Articulo;
import com.example.clases.ListaSimple;
import com.example.clases.Menu;
import com.example.clases.Util;
import com.example.clases.ViewUtilities;
import com.example.sharedpreferences.SharedPreference;

import complementos.ItemRecyclerViewAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {
	
	private SharedPreference instanciaShare;
	private Context mContext;
	protected WakeLock wakelock;
	private Views views;
    boolean vistas = true;
	public TextView fecha_hora;
	public ImageButton carroCompra , llamarMozo;
	public int request_code = 1;
	///TabHost.TabSpec spec; // Reusable TabSpec for each tab
    ////Intent intentHost; // Reusable Intent for each tab
	private FrameLayout simpleFrameLayout;
    private FrameLayout ll_contenido , ll_cargando;
    private TabLayout tabLayout;
    private ViewPager viewPager ;
    private FragmentManager manager;
    private FragmentActivity activity;
    private  PagerAdapter adapter;
    
    private android.app.ActionBar actionBar;
    public static Menu menuActivo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    
        
        mContext= getApplicationContext();
        instanciaShare= new SharedPreference(mContext);
        
        ManagerApi.setIP(instanciaShare.recuperarIPv4());
        
        if (instanciaShare.recuperarIPv4().equals("")) {
        	
        	Intent i = new Intent(mContext, IniciarRestaurant.class);
        	startActivity(i);
        	finish();
			
		}else if(!instanciaShare.recuperarLoginMozo()){
	     
	        	Intent i = new Intent(mContext, MozoLogin.class);
	        	startActivity(i);
	        	finish();
			   
	            }else{ if(!instanciaShare.recuperarInicioPedido()){
	        		
	        		Intent i = new Intent(mContext, EmprezarPedido.class);
	            	startActivity(i);
	            	finish();
	        		
			         }else {
			        	
			        	
			        	 
			        	setContentView(R.layout.menu_principal);
			        	views = Views.getInstance(mContext);
			        	manager = getSupportFragmentManager();
			        	menuActivo = new Menu();
			        	fecha_hora = (TextView) findViewById(R.id.main_tv_fecha_hora);
			        	carroCompra =(ImageButton)findViewById(R.id.img_carro_compra);
			        	llamarMozo =(ImageButton)findViewById(R.id.img_llamar_mozo);
			        	ll_contenido = (FrameLayout) findViewById(R.id.ll_mp_main);
			        	ll_cargando = (FrameLayout) findViewById(R.id.ll_mp_cargando);
			        	tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
			        	////tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

			            viewPager = (ViewPager) findViewById(R.id.pager);
			        	
			        	fechaHora();
			        	
			        	
			        	///simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
			        	
			           

			            // define ViewPager
			        	///Agrego la informacion y la mando
//			        	DummyModel[] listModel0 = createDummyListModel("tab 0");
//			            DummyModel[] listModel1 = createDummyListModel("tab 1");
//			            DummyModel[] listModel2 = createDummyListModel("tab 2");

			           ///// activity = new FragmentActivity(mContext);
			           
			            //  ViewPager need a PagerAdapter
			            
			            			            
			            new RecuperarMenu().execute();

			           

			        	 tabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
							
							@Override
							public void onTabUnselected(TabLayout.Tab tab) {
								
								// TODO Auto-generated method stub
								
								
				               
								/*
								Fragment fragment = null;
								
								FragmentManager fm = manager;
								FragmentTransaction ft = fm.beginTransaction();
								//ft.replace(R.id.simpleFrameLayout, fragment);
								ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
								ft.commit();*/
							}
							
							@Override
							public void onTabSelected(TabLayout.Tab tab) {
								// TODO Auto-generated method stub
								viewPager.setCurrentItem(tab.getPosition() );
							}
							
							@Override
							public void onTabReselected(TabLayout.Tab tab) {
								// TODO Auto-generated method stub
								///viewPager.setCurrentItem(tab.getPosition() );
							}
						});
			        	
			        	
			        	carroCompra.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								Intent i= new Intent(mContext, CarroCompra.class);
								startActivity(i);
								
								
							}
						});
			        	
			        	llamarMozo.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent i= new Intent(mContext, LlamarMozo.class);
								startActivityForResult(i, request_code);
								
								
								
							}
						});
			        	
			        	
			        	
			        	
			      }
	        }
	        	
	      
        
    }
    
    
    private class RecuperarMenu extends AsyncTask<Void, Void, Void> {

    	String respuesta="";
    	
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			ll_cargando.setVisibility(View.VISIBLE);
			ll_contenido.setVisibility(View.GONE);
		}

	

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			respuesta = ApiMenus.getMenu();
			return null;
		}
    	
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(respuesta.equals(ApiMenus.OK)){
				
				ll_cargando.setVisibility(View.GONE);
				ll_contenido.setVisibility(View.VISIBLE);
				crearTab();
				agregarDatos();
				
				
			}else{
				
				Toast.makeText(mContext, respuesta, Toast.LENGTH_LONG).show();
				instanciaShare.limpiarLoginMozo();
				instanciaShare.limpiarPedido();
				Intent i = new Intent(mContext, MozoLogin.class);
	        	startActivity(i);
	        	finish();
			}
		}
    	
    }
    
    public void agregarDatos(){
    	
    	adapter  = new PagerAdapter(manager, tabLayout.getTabCount(), menuActivo.getSecciones());
    	viewPager.setAdapter(adapter);
    	viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        
        ViewUtilities.waitForLayout(viewPager, new Runnable() {
            @Override
            public void run() {
            	viewPager.setCurrentItem(0 , false);
            }
        });
      

    }
    
    @Override
    public void onListFragmentInteraction(Articulo model) {
        // the user clicked on this item over the list
        Toast.makeText(mContext, Articulo.class.getSimpleName() + ":" + model.getNombre(), Toast.LENGTH_LONG).show();
    }

    


    
    
    public void crearTab(){
    	
    	TabLayout.Tab tab;
    	
    	for (int i = 0 ; i < menuActivo.getSecciones().size() ; i++) {
    		tab  = tabLayout.newTab();
    		tab.setText(menuActivo.getSecciones().get(i).getNombre());
    		///firstTab.setIcon(R.drawable.ic_loguin); 
    		tabLayout.addTab(tab , i);
		}
    	tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    	
    }
    
    
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		
		if(resultCode== RESULT_OK){
			int resultado = intent.getIntExtra("clave",0);

			if (resultado==Util.FINALIZAR_PEDIDO) {
				
				instanciaShare.limpiarLoginMozo();
				instanciaShare.limpiarPedido();
				Intent i = new Intent(mContext, MozoLogin.class);
	        	startActivity(i);
	        	finish();
				
			}
		}
		
	}




	




	public void fechaHora(){
    	
    	
    	new CountDownTimer(1000000000, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				Calendar c = Calendar.getInstance();
				fecha_hora.setText(new StringBuilder(String.valueOf(String.format("%02d", new Object[] { Integer.valueOf(c.get(Calendar.DAY_OF_MONTH)) })))
								.append("/")
								.append(String.format("%02d", new Object[] { Integer.valueOf(c.get(Calendar.MONTH) + 1) }))
								.append("/")
								.append(c.get(Calendar.YEAR))
								.append(" ")
								.append(String.format("%02d",new Object[] { Integer.valueOf(c.get(Calendar.HOUR_OF_DAY)) }))
								.append(":")
								.append(String.format("%02d",new Object[] { Integer.valueOf(c.get(Calendar.MINUTE)) }))
								.append(":")
								.append(String.format("%02d",new Object[] { Integer.valueOf(c.get(Calendar.SECOND)) })).toString());

			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				
			}
		}.start();
    	
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
			MainActivity.this
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

 
}
