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
import com.example.clases.Calificacion;
import com.example.clases.ItemOferta;
import com.example.clases.ItemPedido;
import com.example.clases.Mesa;
import com.example.clases.Oferta;
import com.example.clases.Restriccion;
import com.example.clases.Util;
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
	private static final String ITEMS_OFERTA = "itemsOferta";
	private static final String OFERTA = "oferta";
	
	
	private static final String ITEMS_ARTICULO_OFERTA = "itemsArticuloOferta";
	private static final String ITEM_CANTIDAD= "cantidad";
	private static final String ITEM_ARTICULO= "articulo";
	
	private static final String ITEM_ARTICULO_NOMBRE= "nombre";
	private static final String ITEM_ARTICULO_ID_ARTICULO= "idEntidad";
	
	private static final String ITEM_PRECIO= "precioUnitario";
	private static final String ID_ITEM= "idEntidad";
	private static final String ITEMS= "items";
	private static final String CANTIDAD = "cantidad";
	private static final String TIPO = "tipo";
	private static final String CANTIDAD_COMENSALES = "cantComensales";
	public static String OK= "ok";
	public static String NO_OK= "ocurrio un error";
	public static String NO_HAY_ITEM= "no hay items";
	
	public static String iniciarPedidoActual(SharedPreference instanciaShare, String idMozo , ArrayList<String> idMesa, String cantComensales){
		
		return IniciarPedido.iniciarPedido(instanciaShare,idMozo,idMesa,cantComensales);
		
	}
	
	public static String buscarItemsPedido(String idPedido, String idMozo) {

		ArrayList<ItemPedido> itemsPedido = new ArrayList<ItemPedido>();
		String respuesta = OK;

		String respuestaApi = ManagerApi.getApi(DIRECCION_API
				+ ManagerApi.GET_PARAMETROS
				+ ManagerApi.getParametros(ID_PEDIDO) + idPedido
				+ ManagerApi.UNIR_PARAMETROS
				+ ManagerApi.getParametros(ID_USUARIO) + idMozo);

		if (!respuestaApi.equals("")) {

			try {
				JSONObject jsonResponse = new JSONObject(respuestaApi);

				if (respuestaApi.contains(ManagerApi.ERROR_RESPUESTA_API)) {

					respuesta = jsonResponse
							.getString(ManagerApi.ERROR_RESPUESTA_API);

				} else {
					MainActivity.misListaItemPedidoRealizados = new ArrayList<ItemPedido>();
			
					if (respuestaApi.contains(ITEMS_ARTICULO)) {
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
							///MainActivity.misListaItemPedidoRealizados.addAll(itemsPedido);
						} 
					}
					if (respuestaApi.contains(ITEMS_OFERTA)) {

						JSONArray jsonOfertas = new JSONArray(jsonResponse.getString(ITEMS_OFERTA));
						
						if (jsonOfertas != null && jsonOfertas.length() > 0) {
							
							for (int i = 0; i < jsonOfertas.length(); i++) {
								
								JSONObject jsonItemOferta = jsonOfertas.getJSONObject(i);
								Oferta oferta;
								ItemPedido itemOferta = new ItemPedido();
								itemOferta.setIdEntidad(jsonItemOferta.getString(ID_ENTIDAD));
								itemOferta.setCantidad(Integer.parseInt(jsonItemOferta.getString(ITEM_CANTIDAD)));
								itemOferta.setPrecioUnitario(Float.parseFloat(jsonItemOferta.getString(ITEM_PRECIO)));
								oferta = new Oferta();
								JSONObject jsonOferta = new JSONObject(jsonItemOferta.getString(OFERTA));
								oferta.setIdEntidad(jsonOferta.getString(ApiOfertas.IDENTIDAD));
								oferta.setDescripcion(jsonOferta.getString(ApiOfertas.DESCRIPCION));
								oferta.setPrecio(Float.valueOf(jsonOferta.getString(ApiOfertas.PRECIO)));
								JSONObject fechasInicio = new JSONObject(jsonOferta.getString(ApiOfertas.FECHA_INICIO));
								String fechaI = fechasInicio.getString(ApiOfertas.DAY)+ "/"+ fechasInicio.getString(ApiOfertas.MONTH)+ "/"+ fechasInicio.getString(ApiOfertas.YEAR);
								oferta.setFechaInicio(fechaI);
								JSONObject fechasFin = new JSONObject(jsonOferta.getString(ApiOfertas.FECHA_FIN));
								String fechaF = fechasFin.getString(ApiOfertas.DAY)+ "/"+ fechasFin.getString(ApiOfertas.MONTH)+ "/"+ fechasFin.getString(ApiOfertas.YEAR);
								oferta.setFechaFin(fechaF);
								oferta.setNombre(jsonOferta.getString(ApiOfertas.NOMBRE));
								JSONArray jsonItemsOferta = new JSONArray(jsonOferta.getString(ITEMS_OFERTA));

								if (jsonItemsOferta != null && jsonItemsOferta.length() > 0) {
									
									ArrayList<ItemOferta> items = new ArrayList<ItemOferta>();

									for (int j = 0; j < jsonItemsOferta.length(); j++) {

										JSONObject jsonItem = jsonItemsOferta.getJSONObject(j);
										if (jsonItem != null) {

											ItemOferta item = new ItemOferta();
											item.setCantidad(Integer.parseInt(jsonItem.getString(CANTIDAD)));
											JSONObject jsonArticulo = new JSONObject(jsonItem.getString(ApiOfertas.ARTICULO));

											if (jsonArticulo != null) {

												Articulo articulo = new Articulo();
												articulo.setIdEntidad(jsonArticulo.getString(ApiOfertas.IDENTIDAD));
												articulo.setNombre(jsonArticulo.getString(ApiOfertas.NOMBRE));
												articulo.setDescripcion(jsonArticulo.getString(ApiOfertas.DESCRIPCION));
												articulo.setTipoArticulo(jsonArticulo.getString(ApiOfertas.TIPO_ARTICULO));
												articulo.setUrlImagen(jsonArticulo.getString(ApiOfertas.URL_IMAGEN));
												articulo.setPrecio(Float.valueOf(jsonArticulo.getString(ApiOfertas.PRECIO)));
												articulo.setTiempoPreparacion(Integer.parseInt(jsonArticulo.getString(ApiOfertas.TIEMPO_PREPARACION)));
												articulo.setCalorias(Float.valueOf(jsonArticulo.getString(ApiOfertas.CALORIAS)));
												articulo.setCantVecesPedido(Integer.parseInt(jsonArticulo.getString(ApiOfertas.CANTIDAD_VECES_PEDIDO)));
												
												if (jsonArticulo.toString().contains(ApiOfertas.CALIFICACIONES)) {
													JSONArray jsonCalificaciones = new JSONArray(jsonArticulo.getString(ApiOfertas.CALIFICACIONES));

													if (jsonCalificaciones != null && jsonCalificaciones.length() > 0) {

														ArrayList<Calificacion> calificaciones = new ArrayList<Calificacion>();
														for (int z = 0; z < jsonCalificaciones.length(); z++) {

															Calificacion calificacion = new Calificacion();
															JSONObject jsonCalificacion = jsonCalificaciones.getJSONObject(z);
															calificacion.setIdEntidad(jsonCalificacion.getString(ApiOfertas.IDENTIDAD));
															calificacion.setCalificacionArticulo(jsonCalificacion.getString(ApiOfertas.CALIFICACIONARTICULO));
															if (jsonCalificacion.toString().contains(ApiOfertas.COMENTARIO)) {

																calificacion.setComentario(jsonCalificacion.getString(ApiOfertas.COMENTARIO));

															} else {

																calificacion.setComentario("");
															}
															calificaciones.add(calificacion);

														}
														articulo.setCalificaciones(calificaciones);
													}
												}

												if (jsonArticulo.toString().contains(ApiOfertas.RESTRINCCIONES)) {

													
													JSONArray jsonRetricciones = new JSONArray(jsonArticulo.getString(ApiOfertas.RESTRINCCIONES));

													if (jsonRetricciones != null && jsonRetricciones.length() > 0) {

														ArrayList<Restriccion> restricciones = new ArrayList<Restriccion>();
														for (int y = 0; y < jsonRetricciones.length(); y++) {

															Restriccion restriccion = new Restriccion();
															JSONObject jsonRetriccion = jsonRetricciones.getJSONObject(y);
															restriccion.setIdEntidad(jsonRetriccion.getString(ApiOfertas.IDENTIDAD));
															restriccion.setNombre(jsonRetriccion.getString(ApiOfertas.NOMBRE));
															restriccion.setDescripcion(jsonRetriccion.getString(ApiOfertas.DESCRIPCION));

															restricciones.add(restriccion);

														}
														articulo.setRestricciones(restricciones);
													}

												}

												item.setArticulo(articulo);
											}
											items.add(item);
										}

									}
									oferta.setItem(items);
									itemOferta.setOferta(oferta);
									
								}
								
								itemsPedido.add(itemOferta);
							}
						}
						
					}
					MainActivity.misListaItemPedidoRealizados.addAll(itemsPedido);

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block

				respuesta = e.toString();
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block

				respuesta = e.toString();
				e.printStackTrace();
			}
		} else {

			respuesta = NO_OK;
		}

		return respuesta;

	}
	
	
	
	public static String realizarPedido(ArrayList<ItemPedido> itemPedir, String idMozo , String idPedido){
		
		String respuesta = OK;
		String respuestaApi = "";
		ArrayList<ItemPedido> itemsPedido = new ArrayList<ItemPedido>();

		try {

			JSONObject objetoJson = new JSONObject();
			objetoJson.accumulate(ID_USUARIO, idMozo);
			objetoJson.accumulate(ID_PEDIDO, idPedido);
			JSONArray jsonItem = new JSONArray();
			for (int i = 0; i < itemPedir.size(); i++) {
				if(itemPedir.get(i).getArticulo()!=null){
					JSONObject jsonArticulo=  new JSONObject();
					jsonArticulo.accumulate(ID_ENTIDAD,itemPedir.get(i).getArticulo().getIdEntidad() );
					jsonArticulo.accumulate(CANTIDAD,itemPedir.get(i).getCantidad() );
					jsonArticulo.accumulate(TIPO,Util.ARTICULO );
					jsonItem.put(i,jsonArticulo);
				}else {
					for (int j = 0; j < itemPedir.get(i).getCantidad(); j++) {
						
						JSONObject jsonOferta=  new JSONObject();
						jsonOferta.accumulate(ID_ENTIDAD,itemPedir.get(i).getOferta().getIdEntidad() );
						jsonOferta.accumulate(TIPO,Util.OFERTA );
						jsonItem.put(i,jsonOferta);
					}
					
				}
				
			}
			
			objetoJson.accumulate(ITEMS, jsonItem);
			
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
					MainActivity.misListaItemPedidoRealizados = new ArrayList<ItemPedido>();
					if(respuestaApi.contains(ITEMS_ARTICULO)){
						
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
								
							}
							
						///	MainActivity.misListaItemPedidoRealizados.addAll(itemsPedido);
				  }
					if(respuestaApi.contains(ITEMS_OFERTA)){
						
						JSONArray jsonOfertas = new JSONArray(jsonResponse.getString(ITEMS_OFERTA));
						
						if (jsonOfertas != null && jsonOfertas.length() > 0) {
							
							for (int i = 0; i < jsonOfertas.length(); i++) {
								
								JSONObject jsonItemOferta = jsonOfertas.getJSONObject(i);
								Oferta oferta;
								ItemPedido itemOferta = new ItemPedido();
								itemOferta.setIdEntidad( jsonItemOferta.getString(ID_ENTIDAD));
								itemOferta.setCantidad( Integer.parseInt(jsonItemOferta.getString(ITEM_CANTIDAD)));
								itemOferta.setPrecioUnitario(Float.parseFloat(jsonItemOferta.getString(ITEM_PRECIO)));
								oferta = new Oferta();
								JSONObject jsonOferta = new JSONObject(jsonItemOferta.getString(OFERTA));
								oferta.setIdEntidad(jsonOferta.getString(ApiOfertas.IDENTIDAD));
								oferta.setDescripcion(jsonOferta.getString(ApiOfertas.DESCRIPCION));
								oferta.setPrecio(Float.valueOf(jsonOferta.getString(ApiOfertas.PRECIO)));
								JSONObject fechasInicio = new JSONObject (jsonOferta.getString(ApiOfertas.FECHA_INICIO));
								String fechaI =  fechasInicio.getString(ApiOfertas.DAY ) + "/" + fechasInicio.getString(ApiOfertas.MONTH ) + "/" +fechasInicio.getString(ApiOfertas.YEAR);
								oferta.setFechaInicio(fechaI);
								JSONObject fechasFin = new JSONObject (jsonOferta.getString(ApiOfertas.FECHA_FIN));
								String fechaF =  fechasFin.getString(ApiOfertas.DAY ) + "/" + fechasFin.getString(ApiOfertas.MONTH ) + "/" +fechasFin.getString(ApiOfertas.YEAR);
								oferta.setFechaFin(fechaF);
								oferta.setNombre(jsonOferta.getString(ApiOfertas.NOMBRE));
								JSONArray jsonItemsOferta = new JSONArray(jsonOferta.getString(ITEMS_OFERTA));
								
								if(jsonItemsOferta != null && jsonItemsOferta.length() > 0){
									
									ArrayList<ItemOferta> items = new ArrayList<ItemOferta>();
									
									for (int j = 0; j < jsonItemsOferta.length(); j++) {
										
										JSONObject jsonItem = jsonItemsOferta.getJSONObject(j);
										
										if(jsonItem != null){
											
											ItemOferta item = new ItemOferta();
											item.setCantidad(Integer.parseInt(jsonItem.getString(CANTIDAD)));										
											JSONObject jsonArticulo = new JSONObject(jsonItem.getString(ApiOfertas.ARTICULO));
											
											if(jsonArticulo != null){
												
												Articulo articulo = new Articulo();
												articulo.setIdEntidad(jsonArticulo.getString(ApiOfertas.IDENTIDAD));
												articulo.setNombre(jsonArticulo.getString(ApiOfertas.NOMBRE));
												articulo.setDescripcion(jsonArticulo.getString(ApiOfertas.DESCRIPCION));
												articulo.setTipoArticulo(jsonArticulo.getString(ApiOfertas.TIPO_ARTICULO));
												articulo.setUrlImagen(jsonArticulo.getString(ApiOfertas.URL_IMAGEN));
												articulo.setPrecio(Float.valueOf(jsonArticulo.getString(ApiOfertas.PRECIO)));
												articulo.setTiempoPreparacion(Integer.parseInt(jsonArticulo.getString(ApiOfertas.TIEMPO_PREPARACION)));
												articulo.setCalorias(Float.valueOf(jsonArticulo.getString(ApiOfertas.CALORIAS)));
												articulo.setCantVecesPedido(Integer.parseInt(jsonArticulo.getString(ApiOfertas.CANTIDAD_VECES_PEDIDO)));
												if(jsonArticulo.toString().contains(ApiOfertas.CALIFICACIONES)){
													
													JSONArray jsonCalificaciones = new JSONArray(jsonArticulo.getString(ApiOfertas.CALIFICACIONES));
												
													if(jsonCalificaciones != null && jsonCalificaciones.length() > 0){
														
														ArrayList<Calificacion> calificaciones = new ArrayList<Calificacion>();
														for (int z = 0; z < jsonCalificaciones.length(); z++) {
															
															Calificacion calificacion = new Calificacion();
															JSONObject jsonCalificacion = jsonCalificaciones.getJSONObject(z);
															calificacion.setIdEntidad(jsonCalificacion.getString(ApiOfertas.IDENTIDAD));
															calificacion.setCalificacionArticulo(jsonCalificacion.getString(ApiOfertas.CALIFICACIONARTICULO));
															if(jsonCalificacion.toString().contains(ApiOfertas.COMENTARIO)){
																
																calificacion.setComentario(jsonCalificacion.getString(ApiOfertas.COMENTARIO));
																
															}else {
																
																calificacion.setComentario("");
															}
															calificaciones.add(calificacion);
															
														}
														articulo.setCalificaciones(calificaciones);
													}
												}
												
												if(jsonArticulo.toString().contains(ApiOfertas.RESTRINCCIONES)){

													JSONArray jsonRetricciones = new JSONArray(jsonArticulo.getString(ApiOfertas.RESTRINCCIONES));
												
													if(jsonRetricciones != null && jsonRetricciones.length() > 0){
														
														ArrayList<Restriccion> restricciones = new ArrayList<Restriccion>();
														
														for (int y = 0; y < jsonRetricciones.length(); y++) {
															
															Restriccion restriccion = new Restriccion();
															JSONObject jsonRetriccion = jsonRetricciones.getJSONObject(y);
															restriccion.setIdEntidad(jsonRetriccion.getString(ApiOfertas.IDENTIDAD));
															restriccion.setNombre(jsonRetriccion.getString(ApiOfertas.NOMBRE));
															restriccion.setDescripcion(jsonRetriccion.getString(ApiOfertas.DESCRIPCION));
															restricciones.add(restriccion);
															
														}
														
														articulo.setRestricciones(restricciones);
													}
												
													
												}
												
											   item.setArticulo(articulo);
											   
											  }
											
											items.add(item);
										}
										
									}
									
									oferta.setItem(items);
									itemOferta.setOferta(oferta);
									
								}
								itemsPedido.add(itemOferta);
							}
						}
						
						
					}
					MainActivity.misListaItemPedidoRealizados.addAll(itemsPedido);
					
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
	
	
	public static class FinalizarPedido{
		
		private static final String TAG="IniciarPedido";
		private static final String DIRECCION_API= ApiPedido.DIRECCION_API + "/finalizar";
		
		public static String finalizarPedido(String idPedido,  String idMozo){
			String respuesta = OK;
			String respuestaApi = "";
			
			try {
				JSONObject objetoJson = new JSONObject();
				objetoJson.accumulate(ID_USUARIO, idMozo);
				objetoJson.accumulate(ID_PEDIDO, idPedido);
				
				respuestaApi = ManagerApi.postApi(DIRECCION_API, objetoJson);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!respuestaApi.equals("")) {
				JSONObject jsonResponse;
				try {

					jsonResponse = new JSONObject(respuestaApi);
					if (respuestaApi.contains(ManagerApi.ERROR_RESPUESTA_API)) {
						respuesta = jsonResponse.getString(ManagerApi.ERROR_RESPUESTA_API);
						
						
					} else {
						
						

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
	
	
	
	public static class EnviarCalificacion {
		
		private static final String TAG="EnviarCalificacion";
		private static final String DIRECCION_API= ApiPedido.DIRECCION_API + "/calificar";
		private static final String TIEMPO_ENTREGA ="tiempoEntrega";
		private static final String SERVICIO ="servicio";
		private static final String IDITEM="idItem";
		private static final String COMENTARIO="comentario";
		private static final String CALIFICACION_ARTICULO="calificacionArticulo";
		private static final String CALIFICACION_PEDIDO="calificacionPedido";
		private static final String CALIFICACION_ITEM="calificacionItems";
		
		
		
		public static String  enviarCalificacion(String idPedido, ArrayList<String> servicios , ArrayList <ItemPedido> itemPedido, ArrayList<String> items){
			String respuesta=OK;
			String respuestaApi="";
			
			////{“tiempoEntrega”: “Bueno|Regular|Malo”, “servicio”: ”Bueno|Regular|Malo”, “comentario”: “...”}

			try {
				JSONObject objetoJson = new JSONObject();
				objetoJson.accumulate(ID_PEDIDO, idPedido);

				JSONObject objetoPedido = new JSONObject();
				objetoPedido.accumulate(TIEMPO_ENTREGA, servicios.get(0));
				objetoPedido.accumulate(SERVICIO, servicios.get(1));
				objetoPedido.accumulate(COMENTARIO, " ");
				
				objetoJson.accumulate(CALIFICACION_PEDIDO, objetoPedido);
				
				
               JSONArray jsonItems = new JSONArray();
               
				
				for (int i = 0; i < itemPedido.size(); i++) {
					
					if(itemPedido.get(i).getArticulo()!=null){
						
						JSONObject objetoItem = new JSONObject();
						objetoItem.accumulate(IDITEM, itemPedido.get(i).getIdEntidad());
						objetoItem.accumulate(CALIFICACION_ARTICULO, items.get(i));
						objetoItem.accumulate(COMENTARIO, " ");
						jsonItems.put(i, objetoItem);
					}
					
					
				}
				
				 objetoJson.accumulate(CALIFICACION_ITEM, jsonItems);
				
				
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
						
						
					} else {
						
						
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




