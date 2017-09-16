package com.example.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.clases.Mesa;
import com.example.restaurant.EmprezarPedido;

public class ApiMesas {
	
	
	public static String TAG = "ApiMesas";
	public static String DIRECCION_API= "/mesas";
	public static String GET_MESA= "?idMesa=";
	public static String GET_LISTADO_ESTADO_MESAS= "?estado=";
	public static String GETALL_MESAS= "";
	public static String ESTADO_MESA_LIBRE = "Libre";
	public static String ESESTADO_MESA_OCUPADA = "Ocupada";
	
	public static String OK="ok";
	public static String NO_HAY_MESAS="no hay mesas disponible";
	public static String NO_OK="Ocurrio un error";
	
	
	public static String NRO_MESA="nroMesa";
	public static String ESTADO="estado";
	public static String ID_ENTIDAD="idEntidad";
	
	public static String MESA_PADRE= "mesaPadre";
	
	
	public static ArrayList<Mesa> getAllMesas(){
		ArrayList<Mesa> mesas = new ArrayList<Mesa>();
		
		return mesas; 
	}
	
	public static String recuperarEstadoMesas(String estado) {

		ArrayList<Mesa> mesasLibres = new ArrayList<Mesa>();
		String respuesta=OK;
	
		String respuestaApi = ManagerApi.getApi(DIRECCION_API + GET_LISTADO_ESTADO_MESAS + estado);

		if (!respuestaApi.equals("")) {
			
			
			try {
				
				if (respuestaApi.contains(ManagerApi.ERROR_RESPUESTA_API)) {
					
					JSONObject jsonResponse = new JSONObject(respuestaApi);
					respuesta = jsonResponse.getString(ManagerApi.ERROR_RESPUESTA_API);
					
				} else {
					
					JSONArray jsonArray = new JSONArray(respuestaApi);

					if (jsonArray != null && jsonArray.length() > 0) {
						Mesa mesa;
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject child = jsonArray.getJSONObject(i);
							 mesa = new Mesa();
							 
							String numeroMesa = child.getString(NRO_MESA);
							String estadoMesa = child.getString(ESTADO);
							String idEntidad = child.getString(ID_ENTIDAD);
							mesa.setEstadoMesa(estadoMesa);
							mesa.setNroMesa(Integer.parseInt(numeroMesa));
							mesa.setIdMesa(Integer.parseInt(idEntidad));
							mesasLibres.add(mesa);
						}
						
						EmprezarPedido.mesasLibres.addAll(mesasLibres);
					}else {
						respuesta= NO_HAY_MESAS;
					}
					
				}

			}catch (JSONException e) {
				// TODO Auto-generated catch block
				
				respuesta=e.toString();
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				respuesta=e.toString();
				e.printStackTrace();
			}
		}else {
			
			respuesta = NO_OK;
		}

		return respuesta;
	}
	
	
	
}
