package com.sorftweb.sandino.recyclerpersonalizado.MODELS;

public class SolicitudContactar {
    int id=-421, id_casa = -421, id_user = -421;
    String descripcion="",nombre="", email="",telefono="", fecha="";
    public SolicitudContactar(){
    }

    public SolicitudContactar(int id, int id_casa, int id_user, String descripcion, String nombre, String email, String telefono, String fecha) {
        this.id = id;
        this.id_casa = id_casa;
        this.id_user = id_user;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_casa() {
        return id_casa;
    }

    public void setId_casa(int id_casa) {
        this.id_casa = id_casa;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
