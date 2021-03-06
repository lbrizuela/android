package com.example.restaurant;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.api.ApiMenus;
import com.example.api.ApiOfertas;
import com.example.api.ApiPedido;
import com.example.api.ManagerApi;
import com.example.clases.Articulo;
import com.example.clases.ItemPedido;
import com.example.clases.ListaSimple;
import com.example.clases.Menu;
import com.example.clases.Oferta;
import com.example.clases.Util;
import com.example.clases.ViewUtilities;
import com.example.sharedpreferences.SharedPreference;
import com.squareup.picasso.Picasso;

import complementos.AdaptadorItemOferta;
import complementos.AdaptadorItemPedido;
import complementos.ItemRecyclerViewAdapter;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener, ItemFragmentOferta.OnListFragmentInteractionListenerOferta {
	
	private String TAG ="MainActivity";
	private SharedPreference instanciaShare;
	private Context mContext;
	protected WakeLock wakelock;
    private Bitmap loadedImage;
	private Views views;
    boolean vistas = true;
    private TextView fecha_hora , nombre_menu , tiempo , ingredientes , precio , calorias, nombre_plato , restricciones, notificacion;
    private TextView nombre_oferta , tiempo_oferta, descripcion_oferta , precio_oferta , tiempo_inicio , tiempo_fin;
	private ImageView imagen_plato;
	private RatingBar calificacion_plato;
	private Button agregar_carro , agregar_carro_oferta;
	private ImageButton carroCompra , llamarMozo;
	private int request_code = 1;
	private RelativeLayout progresoImagen;
	///TabHost.TabSpec spec; // Reusable TabSpec for each tab
    ////Intent intentHost; // Reusable Intent for each tab
	private FrameLayout simpleFrameLayout;
    private FrameLayout ll_contenido , ll_cargando;
    private TabLayout tabLayout;
    private ViewPager viewPager ;
    private FragmentManager manager;
    private FragmentActivity activity;
    private PagerAdapter adapter;
    private LinearLayout ll_detalle_articulo, ll_detalle_oferta , ll_tiempo_articulo, ll_tiempo_oferta  ,ll_restricion_articulo ;
    private Articulo articuloPedido;
    private Oferta ofertaPedido;
    private RecyclerView listaOfertas;
    private static MainActivity instance;
    
    
    public static  ArrayList<ItemPedido> misListaItemPedidoActuales;
    public static ArrayList<ItemPedido> misListaItemPedidoRealizados;
    
    private android.app.ActionBar actionBar;
    public static Menu menuActivo ;
    
    public BroadcastReceiver finalizarPedido = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			new FinalizarPedido().execute();
		}
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    
        instance = this;
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
			   
	            }else{ 
	            	
	            	if(!instanciaShare.recuperarInicioPedido()){
	        		
	        		Intent i = new Intent(mContext, EmprezarPedido.class);
	            	startActivity(i);
	            	finish();
	        		
			         }else
			        	 
			        	 if(!instanciaShare.recuperarIsCalificacion()){
			        		
			        	  Intent i = new Intent(this, PopUp.class);
			 			  i.putExtra("envia", Util.FINALIZAR);
			 			  startActivity(i);
			 			  
			        		
				         }      	
	            	    setContentView(R.layout.menu_principal);
			        	views = Views.getInstance(mContext);
			        	manager = getSupportFragmentManager();
			        	menuActivo = new Menu();
			        	
			        	fecha_hora = (TextView) findViewById(R.id.main_tv_fecha_hora);
			        	nombre_menu = (TextView) findViewById(R.id.main_tv_nombre_menu);
			        	
			        	carroCompra =(ImageButton)findViewById(R.id.img_carro_compra);
			        	llamarMozo =(ImageButton)findViewById(R.id.img_llamar_mozo);
			        	ll_contenido = (FrameLayout) findViewById(R.id.ll_mp_main);
			        	ll_detalle_articulo = (LinearLayout) findViewById(R.id.ll_descripcion_articulo);
			        	ll_detalle_oferta = (LinearLayout) findViewById(R.id.ll_descripcion_oferta);
			        	ll_tiempo_articulo = (LinearLayout) findViewById(R.id.ll_tiempo_articulo);
			        	ll_tiempo_oferta = (LinearLayout) findViewById(R.id.ll_tiempo_espera);
			        	ll_restricion_articulo = (LinearLayout) findViewById(R.id.restriccion_articulo);
			        	notificacion =(TextView) findViewById(R.id.tv_carro_notification);
			        	ll_cargando = (FrameLayout) findViewById(R.id.ll_mp_cargando);
			        	tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
			        	
			        	nombre_plato = (TextView) findViewById(R.id.nombre_articulo);
			        	ingredientes = (TextView) findViewById(R.id.ingredientes);
			        	tiempo = (TextView) findViewById(R.id.tiempo);
			        	precio = (TextView) findViewById(R.id.precio);
			        	calorias = (TextView) findViewById(R.id.calorias);
			        	calificacion_plato = (RatingBar) findViewById(R.id.estrellas_plato);
			        	imagen_plato = (ImageView) findViewById(R.id.imagen_plato);
			        	progresoImagen = (RelativeLayout) findViewById(R.id.rl_mp_imagen);
			        	agregar_carro =(Button) findViewById(R.id.btn_mp_agregar_al_carro);
			        	restricciones = (TextView) findViewById(R.id.restricciones);
			        	
			        	agregar_carro_oferta =(Button) findViewById(R.id.btn_mp_agregar_al_carro_oferta);
			        	
			        	nombre_oferta =(TextView) findViewById(R.id.nombre_oferta);
			        	tiempo_oferta =(TextView) findViewById(R.id.tiempo_espera_oferta);
			        	descripcion_oferta =(TextView) findViewById(R.id.descripcion_oferta);
			        	precio_oferta =(TextView) findViewById(R.id.precio_oferta);
			        	tiempo_inicio =(TextView) findViewById(R.id.fecha_inicio);
			        	tiempo_fin =(TextView) findViewById(R.id.fecha_fin);
			        	listaOfertas = (RecyclerView) findViewById(R.id.rv_mp_cardOferta);
			        	
			        	////tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

			            viewPager = (ViewPager) findViewById(R.id.pager);
			            misListaItemPedidoActuales= new ArrayList<ItemPedido>();
			            misListaItemPedidoRealizados= new ArrayList<ItemPedido>();
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

			            llamarMozo.setVisibility(View.GONE);

			        	 /*tabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
							
							@Override
							public void onTabUnselected(TabLayout.Tab tab) {
								
								// TODO Auto-generated method stub
								
								
				               
								
								
								Fragment fragment = null;
								
								FragmentManager fm = manager;
								FragmentTransaction ft = fm.beginTransaction();
								//ft.replace(R.id.simpleFrameLayout, fragment);
								ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
								ft.commit();
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
			        	*/
			        	
			        	carroCompra.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								Intent i= new Intent(mContext, CarroCompra.class);
								startActivity(i);
								ll_detalle_articulo.setVisibility(View.GONE);
								///notificacion.setVisibility(View.GONE);
								
								
							}
						});
			        	
			        	llamarMozo.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent i= new Intent(mContext, PopUp.class);
								i.putExtra("envia", Util.LLAMAR_MOZO);
								startActivityForResult(i, Util.LLAMAR_MOZO);
								ll_detalle_articulo.setVisibility(View.GONE);
								
								
							}
						});
			        	
			        	agregar_carro.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								Intent i = new Intent(MainActivity.this, TecladoChico.class);
								i.putExtra("enviar", Util.CANTIDAD_ARTICULO);
								startActivityForResult(i,  Util.CANTIDAD_ARTICULO);
							}
						});
			        	agregar_carro_oferta.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent i = new Intent(MainActivity.this, TecladoChico.class);
								i.putExtra("enviar", Util.CANTIDAD_OFERTA);
								startActivityForResult(i,  Util.CANTIDAD_OFERTA);
							}
						});
						        	
							        	
							        	
							      
						        }
	        	
	      
        
    }
    
    
    public static void limpiarListas(){
    	
    	misListaItemPedidoActuales= new ArrayList<ItemPedido>();
		misListaItemPedidoRealizados= new ArrayList<ItemPedido>();
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
			
			respuesta = ApiMenus.getMenu();
			if(respuesta.equals(ApiMenus.OK)){
				ApiOfertas.getOferta();
				
			}
			return null;
		}
    	
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(respuesta.equals(ApiMenus.OK)){
				
				ll_cargando.setVisibility(View.GONE);
				ll_contenido.setVisibility(View.VISIBLE);
				nombre_menu.setText(menuActivo.getNombre().toString());
				crearTab();
				agregarDatos();
				
				
			}else{
				Util.toastCustom(mContext, "Ocurrio un Error: " + respuesta, Util.TOAST_MENSAJE_ALERTA_MENOR);
				new FinalizarPedido().execute();
			}
		}
    	
    }
    
    public void agregarDatos(){
    	
    	adapter  = new PagerAdapter(manager, tabLayout.getTabCount(),menuActivo.getOfertas() ,menuActivo.getSecciones());
    	viewPager.setAdapter(adapter);
    	viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        ///tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(onTabSelectedListener(viewPager));
      

    }
    
    
    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager viewPager) {

        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            	Log.e(TAG, "onTabSelected: "+tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            	Log.e(TAG, "onTabUnselected: "+tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            	Log.e(TAG, "onTabReselected: "+tab.getPosition());
            }
        };
    }
    
    @Override
    public void onListFragmentInteraction(Articulo model) {
        // the user clicked on this item over the list
    	ll_detalle_oferta.setVisibility(View.GONE);
    	articuloPedido = model;
    	progresoImagen.setVisibility(View.VISIBLE);
        imagen_plato.setVisibility(View.GONE);
     ///   Toast.makeText(mContext, Articulo.class.getSimpleName() + ":" + model.getNombre(), Toast.LENGTH_LONG).show();
        ll_detalle_articulo.setVisibility(View.VISIBLE);
        nombre_plato.setText(model.getNombre().toString());
        
        if(model.getTiempoPreparacion()!=0){
        	ll_tiempo_articulo.setVisibility(View.VISIBLE);
        	tiempo.setText(String.valueOf(model.getTiempoPreparacion())+" minutos");
        }else {
        	ll_tiempo_articulo.setVisibility(View.GONE);
        }
        calorias.setText(String.valueOf(model.getCalorias()));
        ingredientes.setText(model.getDescripcion().toString());
        
        String preciototal= String.format("%.2f", model.getPrecio());
        precio.setText(getResources().getString(R.string.precio)+" "+ preciototal);
        calificacion_plato.setEnabled(false);
        
        
        
        calificacion_plato.setRating(recuperarCalificacion(model));
        calificacion_plato.setIsIndicator(true);
        
        String restriccion="";
        
        if(model.getRestricciones() !=null && model.getRestricciones().size() > 0) {
        	ll_restricion_articulo.setVisibility(View.VISIBLE);
			for (int i = 0; i < model.getRestricciones().size(); i++) {
				restriccion = restriccion + model.getRestricciones().get(i).getNombre().toString();
				if((i + 1) < model.getRestricciones().size()){
					restriccion= restriccion + ", ";
				}
			}
			
		}else  {
			ll_restricion_articulo.setVisibility(View.GONE);
		}
        restricciones.setText(restriccion);
        
        progresoImagen.setVisibility(View.GONE);
        
    	imagen_plato.setVisibility(View.VISIBLE);
    	String url = ManagerApi.ACCESO_IMAGEN + model.getUrlImagen().replace(" ", "%20");
        Picasso.with(mContext).load(url).placeholder(R.drawable.cargando).error(R.drawable.error).noFade().resize(200, 200).into(imagen_plato);
       
         
       
        
        
    }
    
    
    public float recuperarCalificacion(Articulo model){
    	float respuesta = Float.parseFloat("0.0");
    	if(model.getCalificaciones() != null && model.getCalificaciones().size() >0){
	    	for (int i = 0; i < model.getCalificaciones().size(); i++) {
	    		
	    		respuesta = respuesta +  Util.recuperarCalificacion(model.getCalificaciones().get(i).getCalificacionArticulo());
			}
	    	respuesta = respuesta / model.getCalificaciones().size();
    	}
    	
    	return respuesta;
    }
    
    private class FinalizarPedido extends AsyncTask<Void, Void, Void> {
    	
    	

    	String respuesta="";
    	String idPedido, idMozo="";
    	
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			ll_cargando.setVisibility(View.VISIBLE);
			ll_contenido.setVisibility(View.GONE);
			idPedido= instanciaShare.recuperarIdPedido();
			idMozo = instanciaShare.recuperarIdMozo();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			respuesta= ApiPedido.FinalizarPedido.finalizarPedido(idPedido, idMozo);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			
			Util.toastCustom(mContext, "Se finalizo el pedido", Util.TOAST_MENSAJE_INFO);
			
			instanciaShare.limpiarLoginMozo();
			instanciaShare.limpiarPedido();
			Intent i = new Intent(mContext, MozoLogin.class);
        	startActivity(i);
        	finish();
		}
    	
    }
    
   
    @Override
    public void OnListFragmentInteractionListenerOferta(Oferta oferta) {
    	
    	
        // the user clicked on this item over the list
    	ll_detalle_articulo.setVisibility(View.GONE);
    	ll_detalle_oferta.setVisibility(View.VISIBLE);
    	ofertaPedido =oferta;
    	nombre_oferta.setText(oferta.getNombre().toString());
    	descripcion_oferta.setText(oferta.getDescripcion().toString());
    	String preciototal= String.format("%.2f", oferta.getPrecio());
    	precio_oferta.setText(getResources().getString(R.string.precio)+" "+ preciototal);
    	tiempo_inicio.setText(oferta.getFechaInicio().toString());
    	tiempo_fin.setText(oferta.getFechaFin().toString());
    	
		if (oferta.getItem() != null && oferta.getItem().size() > 0) {
			ll_tiempo_oferta.setVisibility(View.VISIBLE);
			int tiempo = 0;
			for (int i = 0; i < oferta.getItem().size(); i++) {
				tiempo = tiempo + oferta.getItem().get(i).getArticulo().getTiempoPreparacion();

			}
			if(tiempo!=0){
				tiempo_oferta.setText(String.valueOf(tiempo) + " minutos");
			}else {
				ll_tiempo_oferta.setVisibility(View.GONE);
			}
			setupRecyclerOferta(oferta);

		}else{
			ll_tiempo_oferta.setVisibility(View.GONE);
		}
    }
    
    
    private AdaptadorItemOferta mAdapterOferta;

	private void setupRecyclerOferta(Oferta oferta) {

		listaOfertas.setVisibility(View.VISIBLE);
		mAdapterOferta = new AdaptadorItemOferta(mContext, oferta.getItem());
		LayoutManager layoutManager = new LinearLayoutManager(this);
		listaOfertas.setLayoutManager(layoutManager);
		listaOfertas.setAdapter(mAdapterOferta);
	}

   
    


    
    
	public void crearTab() {

		TabLayout.Tab tab;
		int j = 0;
		if (menuActivo.getOfertas() != null && menuActivo.getOfertas().size() > 0) {
			
			tab = tabLayout.newTab();
			tab.setText("Oferta");
			tab.setIcon(setTabImagen("Oferta"));
			tabLayout.addTab(tab, j);
			j++;

		}
		if (menuActivo.getSecciones() != null && menuActivo.getSecciones().size() > 0) {

			for (int i=0; i < menuActivo.getSecciones().size(); i++) {
				tab = tabLayout.newTab();
				tab.setText(menuActivo.getSecciones().get(i).getNombre());
				tab.setIcon(setTabImagen(menuActivo.getSecciones().get(i).getNombre()));
				tabLayout.addTab(tab, j);
				j++;
			}
		}
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

	}
    
    
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		
		if (resultCode == RESULT_OK) {
			int respuesta = intent.getIntExtra("respuesta", 0);
			
			if (respuesta == Util.CANTIDAD_ARTICULO) {

				ll_detalle_articulo.setVisibility(View.GONE);
				
				
				int cantidad = Integer.valueOf(intent.getStringExtra("cantidad"));
				boolean respuestaBusqueda = buscarArticulo(cantidad);
				if(!respuestaBusqueda){
					ItemPedido item = new ItemPedido();
					item.setArticulo(articuloPedido);
					item.setCantidad(cantidad);
					item.setPrecioUnitario(articuloPedido.getPrecio());
					misListaItemPedidoActuales.add(item);
				}
				
				
			}else if(respuesta == Util.CANTIDAD_OFERTA){
				
				ll_detalle_oferta.setVisibility(View.GONE);
				int cantidad = Integer.valueOf(intent.getStringExtra("cantidad"));
				boolean respuestaBusqueda = buscarOferta(cantidad);
				
				if(!respuestaBusqueda){
					
					ItemPedido item = new ItemPedido();
					item.setOferta(ofertaPedido);
					item.setCantidad(cantidad);
					item.setPrecioUnitario(ofertaPedido.getPrecio());
					misListaItemPedidoActuales.add(item);
				}

				
			}
			
			notificacion.setVisibility(View.VISIBLE);
			notificacion.setText(String.valueOf(misListaItemPedidoActuales.size()));
			
			Util.toastCustom(mContext, "Item Agregado ", Util.TOAST_MENSAJE_EXITOSO);

		}
		
	}



    public boolean buscarArticulo(int cantidadPedido){
    	
    	boolean encontrado = false;
        for (int i = 0; i <misListaItemPedidoActuales.size(); i++) {
		   if(misListaItemPedidoActuales.get(i).getArticulo()!=null){
			   if(misListaItemPedidoActuales.get(i).getArticulo().getIdEntidad().equals(articuloPedido.getIdEntidad())){
				   
				   int cantidad = misListaItemPedidoActuales.get(i).getCantidad() + cantidadPedido;
				   misListaItemPedidoActuales.get(i).setCantidad(cantidad);
				   encontrado=true;
			   }
		   }
		
	   }
       return encontrado;
    }
    
    
 public boolean buscarOferta(int cantidadPedido){
    	
    	boolean encontrado = false;
        for (int i = 0; i <misListaItemPedidoActuales.size(); i++) {
        	
		   if(misListaItemPedidoActuales.get(i).getOferta()!=null){
			   
			   if(misListaItemPedidoActuales.get(i).getOferta().getIdEntidad().equals(ofertaPedido.getIdEntidad())){
				   
				   int cantidad = misListaItemPedidoActuales.get(i).getCantidad() + cantidadPedido;
				   misListaItemPedidoActuales.get(i).setCantidad(cantidad);
				   encontrado=true;
			   }
		   }
		
	   }
       return encontrado;
    }
    
    public static MainActivity getInstance() {
        return instance;
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
		try {
			registerReceiver(finalizarPedido, new IntentFilter(Util.FINALIZAR_PEDIDO_MAIN));
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, e.toString());
		}
		mantenerPantallaEncendida();
		try {
			this.views.agregarViewNavigationBar();
			this.views.agregarViewStatusBar();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, e.toString());
		}
		this.wakelock.acquire();
		
		if(misListaItemPedidoActuales!=null && misListaItemPedidoActuales.size()>0){
			notificacion.setVisibility(View.VISIBLE);
			notificacion.setText(String.valueOf(misListaItemPedidoActuales.size()));
		}else{
			notificacion.setVisibility(View.GONE);
		}

	}
    
    
    public Drawable setTabImagen(String nombre){
    	
    	Drawable drawable = getResources().getDrawable(R.drawable.ic_food_black_48dp);
    	
    	if(nombre.equalsIgnoreCase("MINUTA")){
    		drawable= getResources().getDrawable(R.drawable.ic_food_black_48dp);
    	}else if(nombre.equalsIgnoreCase("GASEOSAS")){
    		drawable= getResources().getDrawable(R.drawable.icono_gaseosa);
    	}else if(nombre.equalsIgnoreCase("Bebidas alcohólicas")){
    		
    		drawable= getResources().getDrawable(R.drawable.icono_botellas);
    	}else if(nombre.equalsIgnoreCase("VINO")){
    		
    		drawable= getResources().getDrawable(R.drawable.icono_vino);
    	}else if(nombre.equalsIgnoreCase("Oferta")){
    		
    		drawable= getResources().getDrawable(R.drawable.icono_oferta);
    	}else if(nombre.equalsIgnoreCase("Nuevo")){
    		
    		drawable= getResources().getDrawable(R.drawable.icono_nuevo);
    	}else if(nombre.equalsIgnoreCase("CERVEZA")){
    		
    		drawable= getResources().getDrawable(R.drawable.icono_cerbeza);
    	}else if(nombre.equalsIgnoreCase("POSTRES")){
    		
    		drawable= getResources().getDrawable(R.drawable.icono_postres);
    	}else if(nombre.equalsIgnoreCase("ENSALADAS")){
    		
    		drawable= getResources().getDrawable(R.drawable.icono_ensaladas);
    	}else if(nombre.equalsIgnoreCase("CAFETERÍA")){
    		
    		drawable= getResources().getDrawable(R.drawable.ic_food_croissant_black_24dp);
    	}else if(nombre.equalsIgnoreCase("INFUSI")){
    		
    		drawable= getResources().getDrawable(R.drawable.icono_cafes);
    	}
    	return drawable;
    	
    	
    	
    }



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		try {
			unregisterReceiver(finalizarPedido);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, e.toString());
		}
		
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
