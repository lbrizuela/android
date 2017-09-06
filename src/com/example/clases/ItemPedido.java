package com.example.clases;

public class ItemPedido {
	
	private int codigo;
	private String descripcionItem;
	private int cantidadItem;
	

	public ItemPedido (int cod , String desc , int cant){
		 codigo=  cod;
		 descripcionItem = desc;
		 cantidadItem =  cant;
		
		
	}
	
	
	public int getCodigo() {
		return codigo;
	}
	public String getDescripcionItem() {
		return descripcionItem;
	}

	
	public int getCantidadItem() {
		return cantidadItem;
	}
	
	
	
	
	
	
	
	

}
