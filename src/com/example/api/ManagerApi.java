package com.example.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.springframework.http.HttpRequest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.example.clases.Mesa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ManagerApi {
	
	
	private static String TAG = "ManagerApi";
    
    public static final String FORMATO_RESPUESTA= "UTF-8";
    public static final String FORMATO_PAGINA= "http://";
    public static String IP= "";
   // public static final String IP= "192.168.0.11";
    public static final String PUERTO = "8080";
    public static final String BASE = "/RestaurantServer/api";
    public static final String HEADER_POST_ACCEPT = "Accept";
    public static final String HEADER_POST_ACCEPT_VALUE = "application/json";
    public static final String HEADER_POST_TYPE = "Content-type";
    public static final String HEADER_POST_TYPE_VALUE = "application/json";
    public static  String  ACCESO_API;
    public static final String ERROR_RESPUESTA_API= "detailMessage";
    public static final String UNIR_PARAMETROS ="&"; 
    public static final String GET_PARAMETROS ="?"; 
    public static  String  ACCESO_IMAGEN;
    public static final String DIRECCION_IMAGENES ="/RestaurantServer/images/";
    
    
    public static void setIP(String ipv6){
    	IP = ipv6;
    	ACCESO_API = FORMATO_PAGINA + IP +":"+ PUERTO + BASE;
    	ACCESO_IMAGEN= FORMATO_PAGINA + IP +":"+ PUERTO +DIRECCION_IMAGENES;
    }
    
    
    @SuppressWarnings("deprecation")
	public static String postApi (String URL , JSONObject object){
    	String respuestaPost="";
    	HttpClient  httpClient = new DefaultHttpClient();
 		String restURL = ACCESO_API + URL;
		HttpPost httpPost = new HttpPost(restURL);
 		HttpResponse responsePost = null;
 		HttpParams httpParams = null;
		
		try {
			
			String objeteJson = object.toString();
			StringEntity stringEntity = new StringEntity(objeteJson);
			httpPost.setEntity(stringEntity);
			httpPost.setHeader(HEADER_POST_ACCEPT, HEADER_POST_ACCEPT_VALUE);
			httpPost.setHeader(HEADER_POST_TYPE, HEADER_POST_TYPE_VALUE);
			httpParams = new BasicHttpParams();
		    HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
		    httpClient = new DefaultHttpClient(httpParams);
			responsePost = httpClient.execute(httpPost);
			HttpEntity httpEnty = responsePost.getEntity();
			respuestaPost = EntityUtils.toString(httpEnty, FORMATO_RESPUESTA);
			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "ERROR UnsupportedEncodingException ", e);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "ERROR ClientProtocolException ", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "ERROR IOException ", e);
		}
		
    	
    	
    	return respuestaPost;
    }
   
    
    
    
    
    
	
	@SuppressWarnings("deprecation")
	public static String getApi(String URL) {

		
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext httpContexto = new BasicHttpContext();
		String respuesta = "";
		HttpParams httpParams = null;
		
		try {
			
			String restURL = ACCESO_API + URL;
			HttpGet httpGet = new HttpGet(restURL);
			httpParams = new BasicHttpParams();
		    HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
		    httpClient = new DefaultHttpClient(httpParams);
			HttpResponse httpResponse = httpClient.execute(httpGet, httpContexto);
			HttpEntity httpEnty = httpResponse.getEntity();
			respuesta = EntityUtils.toString(httpEnty, FORMATO_RESPUESTA);

		} catch (MalformedURLException e) {
			Log.e(TAG, "ERROR MalformedURLException ", e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(TAG, "ERROR IOException ", e);
			e.printStackTrace();
		} 

		
		return respuesta;

	}
	
	public static String getParametros(String parametro){
		
		return parametro+"=";
	}

	  public static Bitmap downloadFile(String imageHttpAddress) {
	        URL imageUrl = null;
	        Bitmap loadedImage = null;
	        InputStream in = null;
	        int IO_BUFFER_SIZE = 4 * 1024;
	        BufferedOutputStream out = null;
	        String url = ACCESO_IMAGEN + imageHttpAddress ;
	        try {
	            
	        	InputStream is = (InputStream) new URL(url).getContent();
	            Drawable d = Drawable.createFromStream(is, "src name");
	            //options.inSampleSize = 1;

	  ///          loadedImage = BitmapFactory.decodeByteArray(data, 0, data.length,options);
	        } catch (IOException e) {
	            e.printStackTrace();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return loadedImage;
	    }

}
