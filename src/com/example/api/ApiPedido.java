package com.example.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.hardware.ConsumerIrManager.CarrierFrequencyRange;
import android.text.NoCopySpan.Concrete;
import android.util.Log;

import com.example.clases.Articulo;
import com.example.clases.ItemPedido;
import com.example.clases.Mesa;
import com.example.restaurant.CarroCompra;
import com.example.restaurant.EmprezarPedido;
import com.example.restaurant.MainActivity;
import com.example.sharedpreferences.SharedPreference;

public class ApiPedido {
	
	
	private static final String DIRECCION_API= "/pedidos";
	private static final String TAG="Pedido";
	private static final String ID_ENTIDAD="idEntidad";
	private static final String ID_PEDIDO="idPedido";
	
	private static final String ID_USUARIO = "idUsuario";
	private static final String ID_MESA = "idMesa";
	private static final String IDS_MESAS = "idsMesas";
	private static final String ID_MESA_PADRE="mesa";
	private static final String ITEMS_ARTICULO = "itemsArticulo";
	private static final String ITEMS_ARTICULO_OFERTA = "itemsArticuloOferta";
	private static final String ITEM_CANTIDAD= "cantidad";
	private static final String ITEM_ARTICULO= "articulo";
	
	private static final String ITEM_ARTICULO_NOMBRE= "nombre";
	private static final String ITEM_ARTICULO_ID_ARTICULO= "idEntidad";
	
	private static final String ITEM_PRECIO= "precioUnitario";
	private static final String ID_ITEM= "idEntidad";
	
	private static final String CANTIDAD_COMENSALES = "cantComensales";
	public static String OK= "ok";
	public static String NO_OK= "ocurrio un error";
	public static String NO_HAY_ITEM= "no hay items";
	
	public static String iniciarPedidoActual(SharedPreference instanciaShare, String idMozo , ArrayList<String> idMesa, String cantComensales){
		
		return IniciarPedido.iniciarPedido(instanciaShare,idMozo,idMesa,cantComensales);
		
	}
	
	public static String buscarItemsPedido( String idPedido, String idMozo){
		
		ArrayList<ItemPedido> itemsPedido = new ArrayList<ItemPedido>();
		String respuesta=OK;
	
		String respuestaApi = ManagerApi.getApi(DIRECCION_API + ManagerApi.GET_PARAMETROS + ManagerApi.getParametros(ID_PEDIDO) + idPedido + ManagerApi.UNIR_PARAMETROS + ManagerApi.getParametros(ID_USUARIO) + idMozo);

		if (!respuestaApi.equals("")) {
			
			
			try {
				JSONObject jsonResponse = new JSONObject(respuestaApi);
				
				if (respuestaApi.contains(ManagerApi.ERROR_RESPUESTA_API)) {
					
					
					respuesta = jsonResponse.getString(ManagerApi.ERROR_RESPUESTA_API);
					
				} else {
					
					JSONArray jsonArray = new JSONArray(jsonResponse.getString(ITEMS_ARTICULO));

					if (jsonArray != null && jsonArray.length() > 0) {
						ItemPedido itemPedido;
						Articulo articulo;
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i);
							itemPedido = new ItemPedido();
							articulo = new Articulo();
							 
							
							String idEntidadItem = item.getString(ID_ENTIDAD);
							itemPedido.setIdEntidad(idEntidadItem);
							String cantidad = item.getString(ITEM_CANTIDAD);
							itemPedido.setCantidad(Integer.parseInt(cantidad));
							JSONObject art = item.getJSONObject(ITEM_ARTICULO);
							String idArticulo = art.getString(ITEM_ARTICULO_ID_ARTICULO);
							articulo.setIdEntidad(idArticulo);
							String nombreArticulo = art.getString(ITEM_ARTICULO_NOMBRE);
							articulo.setNombre(nombreArticulo);
							itemPedido.setArticulo(articulo);
							String precio = item.getString(ITEM_PRECIO);
							itemPedido.setPrecioUnitario(Float.parseFloat(precio));
							itemsPedido.add(itemPedido);
						}
						
						MainActivity.misListaItemPedidoRealizados.addAll(itemsPedido);
					}else {
						respuesta= NO_HAY_ITEM;
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
	
	
	
	
	public static class IniciarPedido{
		
		private static final String TAG="IniciarPedido";
		private static final String DIRECCION_API= ApiPedido.DIRECCION_API + "/iniciar";
		
		
		
		public static String iniciarPedido(SharedPreference instanciaShare, String idMozo , ArrayList<String> idMesa, String cantComensales){
			


			String respuesta = OK;
			String respuestaApi = "";
			

			try {

				JSONObject objetoJson = new JSONObject();
				objetoJson.accumulate(ID_USUARIO, idMozo);
				objetoJson.accumulate(CANTIDAD_COMENSALES, cantComensales);
				JSONArray jsonMesas = new JSONArray();
				for (int i = 0; i < idMesa.size(); i++) {
					jsonMesas.put(i, idMesa.get(i));
					
				}
				
				objetoJson.accumulate(IDS_MESAS, jsonMesas);
				
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
						
						String idEntidad = jsonResponse.getString(ID_ENTIDAD);
						JSONObject mesaPadre = new JSONObject(jsonResponse.getString(ID_MESA_PADRE));
						String id_Mesa = mesaPadre.getString(ID_ENTIDAD);
						String cantidadComensales = jsonResponse.getString(CANTIDAD_COMENSALES);
						instanciaShare.insetarPedido(idEntidad,id_Mesa,Integer.parseInt(cantidadComensales));

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

}




