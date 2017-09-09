package com.example.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
import android.util.Log;

public class ManagerApi {
	
	
	private static String TAG = "ManagerApi";
    private static Context context;

    public ManagerApi(Context context) {
        setContext(context);
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        context = context;
    }

	
    public static void insertarMesas(){
    	 HttpClient  httpClient = new DefaultHttpClient();
 		HttpContext contexto= new BasicHttpContext();
 		String restURL = "http://192.168.223.113:8080/RestaurantServer/api/mesas";
 		HttpPost post = new HttpPost();
 		
		HttpResponse response = null;
		
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("idMesa", "1"));
		try {
			post.setEntity(new UrlEncodedFormEntity(param));
			response = httpClient.execute(post,contexto);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 		String error ="", data;
 		String respuesta="";
    	
    	
    }
    
    
    
    
    
	
	public static ArrayList<Mesa>  recuperarMesas() {
		 
		ArrayList<Mesa> allMesa= new ArrayList<Mesa>();
		@SuppressWarnings("deprecation")
		 HttpClient  httpClient = new DefaultHttpClient();
		HttpContext contexto= new BasicHttpContext();
		String error ="", data;
		String respuesta="";

		URL url;
		try {
			////String restURL = "http://www.androidexample.com/media/webservice/JsonReturn.php";
			String restURL = "http://192.168.223.113:8080/RestaurantServer/api/mesas";
			
			HttpGet get = new HttpGet(restURL);
			
			HttpResponse response = httpClient.execute(get,contexto);
			HttpEntity enty= response.getEntity();
			 respuesta =		EntityUtils.toString(enty,"UTF-8");



		} catch (MalformedURLException e) {
			error = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			error = e.getMessage();
			e.printStackTrace();
		} finally {
			
		}
		
		
		if(!error.equals("")) {
		    	
		   Log.e(TAG, "Error "+ error);
		} else {
			
			
			String output = "";
			JSONObject jsonResponse;
			
			try {
				jsonResponse = new JSONObject(respuesta);

				JSONArray jsonArray = jsonResponse.optJSONArray("Android");
				
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject child = jsonArray.getJSONObject(i);
					
					String name = child.getString("idMesa");
					String number = child.getString("nroMesa");
					String time = child.getString("estadoMesa");
					
					output = "Name = " + name + System.getProperty("line.separator") + number + System.getProperty("line.separator") + time;
					output += System.getProperty("line.separator");
					
				}
				
				data = output;
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
		return allMesa;
		
		
    }


}
