package com.example.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;



public class SharedPreference {
	
	private static final int MODE_PRIVATE = 1;
	private static Context context;
	private static SharedPreference instance;
	private static final String PREF_NAME = "RestauratSharedPreferences";
	private static final String TAG = "SharedPreference";
	private final static String SHARED_PREFS_FILE = "misDatos";
	private final static String SHARED_MOZO = "datosMozo";
	private final static String SHARED_PEDIDO = "datosPedido";

	


	private static final String LOGIN_MOZO= "login_mozo";
	private static final String CODIGO_MOZO= "codigo_mozo";
	private static final String PUESTO_MOZO= "puesto_mozo";
	private static final String USUARIO_MOZO= "usuario_mozo";
	private static final String CONTRASEÑA_MOZO= "contrasenia_mozo";
	private static final String NOMBRE_MOZO= "nombre_mozo";
	private static final String APELLIDO_MOZO= "apellido_mozo";
	private static final String EMAIL_MOZO= "email_mozo";
	private static final String ROL_MOZO= "rol_mozo";
	private static final String ID_ENTIDAD_MOZO= "id_entidad_mozo";
	//////Pedido
	private static final String PEDIDO_INICIADO= "pedido_iniciado";
	private static final String ID_PEDIDO= "id_pedido";
	private static final String CANTIDAD_COMENSALES= "cantidad_comensales";
	////private static final String ID_MOZO_PEDIDO= "id_mozo_pedido";
	private static final String ID_MEZA_PADRE_PEDIDO= "id_mesa_padre_pedido";
	
	private static final String IPv4= "ipv6";
	
	
	
	

	// --------------------------------------------

	static public void init(Context ctx) {
		if (null == instance)
			instance = new SharedPreference(ctx);
	}

	public SharedPreference(Context ctx) {
		context = ctx;
	}

	static public SharedPreference getInstance() {
		return instance;
	}
	
	private SharedPreferences getSettingsMozo() {
		return context.getSharedPreferences(SHARED_MOZO, MODE_PRIVATE);
	}
	
	private SharedPreferences getSettingsPedido() {
		return context.getSharedPreferences(SHARED_PEDIDO, MODE_PRIVATE);
	}


    private SharedPreferences getSettings() {
		return context.getSharedPreferences(SHARED_PREFS_FILE, MODE_PRIVATE);
	}
	
	
    
    
    ///IPV4
    public void ingresarIPv4(String ipv4){
    	
    	SharedPreferences.Editor editor = getSettings().edit();
    	editor.putString(IPv4, ipv4);
    	editor.commit();
    }
    
    
 public String recuperarIPv4(){
    	
    	return getSettings().getString(IPv4, "");
    }
    
    public void limpiarIPv6(){
    	
    	SharedPreferences.Editor editor = getSettings().edit();
    	editor.clear();
    	editor.commit();
    }
	

	////Mozo
	
	public void insetarLoginMozo(String codigoMozo,
			String puesto, String nombre,
			String apellido , String email, String rol, String idEntidad){
		
		SharedPreferences.Editor editor = getSettingsMozo().edit();
		
		editor.putString(CODIGO_MOZO, codigoMozo);
		editor.putString(PUESTO_MOZO,puesto);
		///editor.putString(USUARIO_MOZO,username);
		////.putString(CONTRASEÑA_MOZO,password);
		editor.putString(NOMBRE_MOZO,nombre);
		editor.putString(APELLIDO_MOZO,apellido);
		editor.putString(EMAIL_MOZO,email);
		editor.putString(ROL_MOZO,rol);
		editor.putString(ID_ENTIDAD_MOZO,idEntidad);
		editor.putBoolean(LOGIN_MOZO, true);
		editor.commit();
		
	}
	
	public String recuperarIdMozo(){
		return getSettingsMozo().getString(ID_ENTIDAD_MOZO, "");
		
	}
	
	public void limpiarLoginMozo(){
		
		SharedPreferences.Editor editor = getSettingsMozo().edit();
		editor.clear();
		editor.commit();
	}
	
	public boolean recuperarLoginMozo() {
		return getSettingsMozo().getBoolean(LOGIN_MOZO,false);
	}

	////Pedido
	public void insetarPedido(String idPedido,
			  String idMezaPadre, int cantidadComensales ){
		
		SharedPreferences.Editor editor = getSettingsPedido().edit();
		
		editor.putString(ID_PEDIDO,idPedido);
		editor.putInt(CANTIDAD_COMENSALES,cantidadComensales);
		////editor.putString(ID_MOZO_PEDIDO,idMozo);
		editor.putString(ID_MEZA_PADRE_PEDIDO,idMezaPadre);
		editor.putBoolean(PEDIDO_INICIADO, true);
		editor.commit();
		
	}
	
	public String recuperarIdPedido(){
		return getSettingsPedido().getString(ID_PEDIDO, "0");
		
	}
	

	
	public void limpiarPedido(){
		
		SharedPreferences.Editor editor = getSettingsPedido().edit();
		editor.clear();
		editor.commit();
	}
	
	public boolean recuperarInicioPedido() {
		return getSettingsPedido().getBoolean(PEDIDO_INICIADO,false);
	}
}
