package com.utec.fmagot.internet;

public class Residencia {
    private int id;
    private String nombre;
    private String direccion;

    public String getId() {
        return String.valueOf(id);
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getNo_habitaciones() {
        return String.valueOf(no_habitaciones);
    }

    public String getDirector() {
        return director;
    }

    private int no_habitaciones;
    private String director;

    public Residencia(int id, String nombre, String direccion, int no_habitaciones, String director) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.no_habitaciones = no_habitaciones;
        this.director = director;
    }

}
