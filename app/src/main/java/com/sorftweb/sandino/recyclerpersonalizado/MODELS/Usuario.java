package com.sorftweb.sandino.recyclerpersonalizado.MODELS;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

public class Usuario {

    private int id_user = 0, estado = -420, activo = -420;
    private String fechaAl = "", nombre = "",sexo="", cedula = "", FechaNacimiento = "",
            telefono = "", correo = "", contraseña = "", tipo = "",
            avatar = "", url_avatar = "", token = "";

    Bitmap imgAvatar;
    public Usuario() {
    }

    public Usuario(int id_user, int estado, int activo, String fechaAl, String nombre, String sexo, String cedula, String fechaNacimiento, String telefono, String correo, String contraseña, String tipo, String avatar, String url_avatar, String token, Bitmap imgAvatar) {
        this.id_user = id_user;
        this.estado = estado;
        this.activo = activo;
        this.fechaAl = fechaAl;
        this.nombre = nombre;
        this.sexo = sexo;
        this.cedula = cedula;
        FechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        this.contraseña = contraseña;
        this.tipo = tipo;
        this.avatar = avatar;
        this.url_avatar = url_avatar;
        this.token = token;
        this.imgAvatar = imgAvatar;
    }

    public void setToken(String to) {
        this.token = to;
    }
    public String getToken() {
        return this.token;
    }

    public void setUrlAvatar(String ca) {
        this.url_avatar = ca;
    }

    public String getUrlAvatar() {
        return this.url_avatar;
    }


    public void setIdUsuario(int id) {
        this.id_user = id;
    }

    public int getIdUsuario() {
        return this.id_user;
    }

    public void setFechaAlta(String fecha) {
        this.fechaAl = fecha;
    }

    public String getFechaAlta() {
        return this.fechaAl;
    }

    public void setNombre(String nom) {
        this.nombre = nom;
    }

    public String getNombre() {
        return this.nombre;
    }


    public void setSexo(String nom) {
        this.sexo = nom;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setCedula(String nom) {
        this.cedula = nom;
    }

    public String getCedula() {
        return this.cedula;
    }

    public void setFechaNacimiento(String fe) {
        this.FechaNacimiento = fe;
    }

    public String getFechaNacimiento() {
        return this.FechaNacimiento;
    }

    public void setTelefono(String te) {
        this.telefono = te;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setCorreo(String co) {
        this.correo = co;
    }

    public String getCorreo() {
        return this.correo;
    }


    public void setContraseña(String con) {
        this.contraseña = con;
    }

    public String getContraseña() {
        return this.contraseña;
    }

    public void setTipoCuenta(String ti) {
        this.tipo = ti;
    }

    public String getTipoCuenta() {
        return this.tipo;
    }

    public void setEstado(int es) {
        this.estado = es;
    }

    public int getEstado() {
        return this.estado;
    }

    public void setActivo(int es) {
        this.activo = es;
    }

    public int getActivo() {
        return this.activo;
    }

    public void setAvatar(String dato) {
        try {
            this.avatar = dato;
            byte[] bytes = Base64.decode(dato, Base64.DEFAULT);
            this.imgAvatar = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);//obtenemos la imgen con su resolucion original

            // modificaremo la resolucion de la imagen por si tenemos problemas(errores) de resolucion
            // Bajar Resolucin Imagen (Solucin OutOfMemoryError)
            //int alto = 100;
            //int ancho =150;
            //Bitmap foto = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            //this.imgAvatar = Bitmap.createScaledBitmap( foto , alto , ancho ,true);
        } catch (Exception ex) {
            Log.e("", ex.getMessage());
            Log.e("", ex.getStackTrace().toString());
            ex.printStackTrace();
        }
    }

    public String getAvatar() {
        return this.avatar;
    }

    public Bitmap getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(Bitmap imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

}
