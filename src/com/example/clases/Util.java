package com.example.clases;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.restaurant.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Util {
	

	public static final int  ID_MOZO = 1;
	public static final int  SALIR = 2;
	public static final int  FINALIZAR_PEDIDO = 3;
	public static final int  CANTIDAD_COMENSALES = 4;
	public static final int  IPv4 = 5;
	public static final int  PEDIR= 6;
	public static final int  LLAMAR_MOZO= 7;
	public static final int  FINALIZAR= 8;
	public static final int  CANTIDAD_ARTICULO = 9;
	public static final int  CANTIDAD_OFERTA = 10;
	
	public static final String FINALIZAR_MAIN = "finalizar_main";
	
	public static final String OFERTA ="Oferta";
	public static final String ARTICULO ="Articulo";
	
    public static final String TOAST = "toast";
    public static final String TOAST_MENSAJE_ADVERTENCIA = "advertencia";
    public static final String TOAST_MENSAJE_ALERTA = "alerta";
    public static final String TOAST_MENSAJE_ALERTA_MENOR = "alertaMenor";
    public static final String TOAST_MENSAJE_EXITOSO = "exitoso";
    public static final String TOAST_MENSAJE_INFO = "info";
    public static final String TOAST_MENSAJE_PREDETERMINADO = "MSJpredeterminado";
	
	
    public static void toastCustom(Context contex, String mensaje, String tipo) {
        View customToastroot;
        LayoutInflater inflater = (LayoutInflater) contex.getSystemService("layout_inflater");
        if (tipo.equals(TOAST_MENSAJE_ALERTA)) {
            customToastroot = inflater.inflate(R.layout.custom_toast_alerta, null);
        } else if (tipo.equals(TOAST_MENSAJE_ADVERTENCIA)) {
            customToastroot = inflater.inflate(R.layout.custom_toast_advertencia, null);
        } else {
            customToastroot = inflater.inflate(R.layout.custom_toast_informacion, null);
        }
        if (tipo.equals(TOAST_MENSAJE_PREDETERMINADO)) {
            customToastroot = inflater.inflate(R.layout.custon_dialogo_mensaje_predeterminado, null);
        }
        if (tipo.equals(TOAST_MENSAJE_EXITOSO)) {
            customToastroot = inflater.inflate(R.layout.custom_toast_exitoso, null);
        }
        if (tipo.equals(TOAST_MENSAJE_ALERTA_MENOR)) {
            customToastroot = inflater.inflate(R.layout.custom_toast_alerta_size_menor, null);
        }
        ((TextView) customToastroot.findViewById(R.id.toast_custom_texto)).setText(mensaje.toUpperCase());
        Toast customtoast = new Toast(contex);
        customtoast.setView(customToastroot);
        customtoast.setGravity(17, 0, 0);
        customtoast.setDuration(0);
        customtoast.show();
    }
    
    
  


	

}
