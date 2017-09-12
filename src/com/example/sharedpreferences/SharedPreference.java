package com.example.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;



public class SharedPreference {
	
	private static final int MODE_PRIVATE = 1;
	private static Context context;
	private static SharedPreference instance;
	private static final String TAG = "SharedPreference";
	private final static String SHARED_PREFS_FILE = "misDatos";
	private final static String SHARED_MOZO = "datosMozo";
////	private final static String SHARED_PREFS_FILE_DATOS_REGISTRO = "datos_registro";
	///private static final int PRIVATE_MODE = 0;
	private static final String PREF_NAME = "RestauratSharedPreferences";
	
	private static final String ID_PEDIDO= "id_pedido";
	private static final String CANTIDAD_COMENSALES= "cantidad_comensales";
	//private static final String EMULADOR_ON = "emulador_on";
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

	private SharedPreferences getSettings() {
		return context.getSharedPreferences(SHARED_PREFS_FILE, MODE_PRIVATE);
	}
	
	
	

	////Mozo
	
	public void insetarLoginMozo(String codigoMozo,
			String puesto, String username, String password , String nombre,
			String apellido , String email, String rol, String idEntidad){
		
		SharedPreferences.Editor editor = getSettingsMozo().edit();
		
		editor.putString(CODIGO_MOZO, codigoMozo);
		editor.putString(PUESTO_MOZO,puesto);
		editor.putString(USUARIO_MOZO,username);
		editor.putString(CONTRASEÑA_MOZO,password);
		editor.putString(NOMBRE_MOZO,nombre);
		editor.putString(APELLIDO_MOZO,apellido);
		editor.putString(EMAIL_MOZO,email);
		editor.putString(ROL_MOZO,rol);
		editor.putString(ID_ENTIDAD_MOZO,idEntidad);
		editor.putBoolean(LOGIN_MOZO, true);
		editor.commit();
		
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
	public void insertarIdPedido(int numeroPedido) {
		SharedPreferences.Editor editor = getSettings().edit();
		editor.putInt(ID_PEDIDO, numeroPedido);
		editor.commit();
	}
	

	public int recuperarIdPedido() {
		return getSettings().getInt(ID_PEDIDO, 0 );
	}
	
	
////Pedido
	public void insertarCantidadComensales(int cantComensales) {
		SharedPreferences.Editor editor = getSettings().edit();
		editor.putInt(CANTIDAD_COMENSALES, cantComensales);
		editor.commit();
	}
	

	public int recuperarCantidadComensales() {
		return getSettings().getInt(CANTIDAD_COMENSALES, 0);
	}

	
	
	public void limpiarFinPedido(){
		
        Editor editor = getSettings().edit();
        editor.clear();
        editor.commit();
        
	}
}
