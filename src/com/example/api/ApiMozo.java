package com.example.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.sharedpreferences.SharedPreference;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ApiMozo {
	
	public static String TAG = "ApiMozo";
	public static String DIRECCION_API= "/login";
	public static String CODIGO_SEGURIDAD ="codigoSeguridad";
	public static String PUESTO= "puesto";
	public static String USUARIO ="username";
	public static String CONTRASEÑA ="password";
	public static String NOMBRE= "nombre";
	public static String APELLIDO= "apellido";
	public static String EMAIL= "email";
	public static String ROL= "rol";
	public static String ID_ENTIDAD ="idEntidad";
	public static String OK= "ok";
	public static String NO_OK= "ocurrio un error";
	
	
	
	
	
	 @SuppressWarnings("deprecation")
	public static String recuperarMozo(SharedPreference instanciaShare ,String codigoSeguridad) {

		String respuesta = OK;
		String respuestaApi = "";
		

		try {

			JSONObject objetoJson = new JSONObject();
			objetoJson.accumulate(CODIGO_SEGURIDAD, codigoSeguridad);
			respuestaApi = ManagerApi.postApi(DIRECCION_API, objetoJson);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			respuesta=e.toString();
			Log.e(TAG, "ERROR JSONException" + e);
		}
		

		if (!respuestaApi.equals("")) {
			JSONObject jsonResponse;
			try {

				jsonResponse = new JSONObject(respuestaApi);
				if (respuestaApi.contains(ManagerApi.ERROR_RESPUESTA_API)) {
					respuesta = jsonResponse.getString(ManagerApi.ERROR_RESPUESTA_API);
					
					////Toast.makeText(ctx, "ERROR " + error , Toast.LENGTH_LONG).show();
				} else {
					
					codigoSeguridad = jsonResponse.getString(CODIGO_SEGURIDAD);
					String puesto = jsonResponse.getString(PUESTO);
					String usuario = jsonResponse.getString(USUARIO);
					String contraseña = jsonResponse.getString(CONTRASEÑA);
					String nombre = jsonResponse.getString(NOMBRE);
					String apellido = jsonResponse.getString(APELLIDO);
					String email = jsonResponse.getString(EMAIL);
					String rol = jsonResponse.getString(ROL);
					String idEntidad = jsonResponse.getString(ID_ENTIDAD);
					instanciaShare.insetarLoginMozo(codigoSeguridad, puesto, usuario, contraseña, nombre, apellido, email, rol, idEntidad);

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block

				respuesta=e.toString();
				Log.e(TAG, "ERROR JSONException" + e);
			}
		}else {
			respuesta = NO_OK;
		}
		return respuesta;

	}

}
