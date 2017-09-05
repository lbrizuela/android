package com.example.clases;



public class ListaSimple {
    private int codigo;
    private String cuarto;
    private String descripcion;
    private String otro;

    public ListaSimple(int codigo, String descripcion, String otro) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.otro = otro;
    }

    public ListaSimple(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public ListaSimple(int codigo, String descripcion, String otro, String cuarto) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.otro = otro;
        this.cuarto = cuarto;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOtro() {
        return this.otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }

    public String getCuarto() {
        return this.cuarto;
    }

    public void setCuarto(String cuarto) {
        this.cuarto = cuarto;
    }
}
