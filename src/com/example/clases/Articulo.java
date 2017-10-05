package com.example.clases;

import java.util.ArrayList;

public class Articulo {

	private String nombre;
	private String descripcion;
	private float precio;
	private String tipoArticulo;
	private float calorias;
	private int tiempoPreparacion;
	private int cantVecesPedido;
	private String urlImagen ;
	private String idEntidad;
	private ArrayList<Calificacion> calificaciones;
	
	public Articulo(){
		
		calificaciones= new ArrayList<Calificacion>();
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public String getTipoArticulo() {
		return tipoArticulo;
	}
	public void setTipoArticulo(String tipoArticulo) {
		this.tipoArticulo = tipoArticulo;
	}
	public float getCalorias() {
		return calorias;
	}
	public void setCalorias(float calorias) {
		this.calorias = calorias;
	}
	public int getTiempoPreparacion() {
		return tiempoPreparacion;
	}
	public void setTiempoPreparacion(int tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}
	public int getCantVecesPedido() {
		return cantVecesPedido;
	}
	public void setCantVecesPedido(int cantVecesPedido) {
		this.cantVecesPedido = cantVecesPedido;
	}
	public String getUrlImagen() {
		return urlImagen;
	}
	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
	public String getIdEntidad() {
		return idEntidad;
	}
	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}
	public ArrayList<Calificacion> getCalificaciones() {
		return calificaciones;
	}
	public void setCalificaciones(ArrayList<Calificacion> calificaciones) {
		this.calificaciones.addAll(calificaciones);
	}
	
	
	

  
	
}
