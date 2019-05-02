package com.sorftweb.sandino.recyclerpersonalizado.ETC;

import android.content.Context;
import android.content.SharedPreferences;

public class InfoUsuario {
    public InfoUsuario(){
    }
    //metodo para extraer el diccionario de preferencias
    public static SharedPreferences getData(Context context){
        SharedPreferences datos = context.getSharedPreferences("credenciales_logeo_cargando", Context.MODE_PRIVATE );
        return datos;
    }
    //metodo para sacar el id del usuario
    public static int getIdUsuario(Context context){
      SharedPreferences datos = getData( context );
      return  datos.getInt("Id_usuario",0);
    }
    //metodo para sacar el correo
    public static String getCorreo(Context context){
        SharedPreferences datos = getData( context );
        return  datos.getString("Correo","");
    }
}
