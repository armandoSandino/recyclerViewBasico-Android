package com.sorftweb.sandino.recyclerpersonalizado.MODELS;

public class Casa {
    int Id=-1;
    int CantidadCuartos= -1;
    int IdUsuario= -1;
    float Precio= -1;
    String Direccion="";
    String Foto="";
    String Telefono="";
    String Estado="";
    String Descripcion="";
    String FechaAlta="";

    public Casa(){
    }

    public Casa(int id, int cantidadCuartos, int idUsuario, float precio, String direccion, String foto, String telefono, String estado, String descripcion, String fechaAlta) {
        Id = id;
        CantidadCuartos = cantidadCuartos;
        IdUsuario = idUsuario;
        Precio = precio;
        Direccion = direccion;
        Foto = foto;
        Telefono = telefono;
        Estado = estado;
        Descripcion = descripcion;
        FechaAlta = fechaAlta;
    }

    public String getFechaAlta() {
        return FechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        FechaAlta = fechaAlta;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }
    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }


    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
    public String getTelefono(){
        return  this.Telefono;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCantidadCuartos() {
        return CantidadCuartos;
    }

    public void setCantidadCuartos(int cantidadCuartos) {
        CantidadCuartos = cantidadCuartos;
    }

    public float getPrecio() {
        return Precio;
    }

    public void setPrecio(float precio) {
        Precio = precio;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

}
