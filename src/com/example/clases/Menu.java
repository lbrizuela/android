package com.example.clases;

import java.util.ArrayList;

public class Menu {
	
	private String nombre;
	private String idEntidad;
	private ArrayList<Seccion> secciones;
	
	public Menu (){
		secciones= new ArrayList<Seccion>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public ArrayList<Seccion> getSecciones() {
		return secciones;
	}

	public void setSecciones(ArrayList<Seccion> secciones) {
		this.secciones.addAll(secciones);
	}
	
}
