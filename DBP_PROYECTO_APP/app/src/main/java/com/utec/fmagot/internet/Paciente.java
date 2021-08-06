package com.utec.fmagot.internet;

import java.io.Serializable;

public class Paciente implements Serializable {
    private String dni;
    private String nombre;
    private String apellido;
    private int edad;
    private String habitacion;
    private String residencia;
    private String usuario;

    public Paciente(String dni, String nombre, String apellido, int edad, String habitacion, String residencia, String user) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.habitacion = habitacion;
        this.residencia = residencia;
        this.usuario = user;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEdad() {
        return String.valueOf(edad);
    }

    public String getHabitacion() {
        return habitacion;
    }

    public String getResidencia() {
        return residencia;
    }

    public String getUser() {
        return usuario;
    }

}
