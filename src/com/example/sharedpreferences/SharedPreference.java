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
////	private final static String SHARED_PREFS_FILE_DATOS_REGISTRO = "datos_registro";
	///private static final int PRIVATE_MODE = 0;
	private static final String PREF_NAME = "RestauratSharedPreferences";
	private static final String ID_MOZO= "id_mozo";
	private static final String ID_PEDIDO= "id_pedido";
	//private static final String EMULADOR_ON = "emulador_on";
	
	
	
	
	

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
	
	

	private SharedPreferences getSettings() {
		return context.getSharedPreferences(SHARED_PREFS_FILE, MODE_PRIVATE);
	}
	
	
	

	////Mozo
	public void insertarIdMozo(String idMozo) {
		SharedPreferences.Editor editor = getSettings().edit();
		editor.putString(ID_MOZO, idMozo);
		editor.commit();
	}
	

	public String recuperarIdMozo() {
		return getSettings().getString(ID_MOZO,"");
	}

	////Pedido
	public void insertarIdPedido(String idMozo) {
		SharedPreferences.Editor editor = getSettings().edit();
		editor.putString(ID_PEDIDO, idMozo);
		editor.commit();
	}
	

	public String recuperarIdPedido() {
		return getSettings().getString(ID_PEDIDO,"");
	}

	
}
