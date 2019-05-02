package com.sorftweb.sandino.recyclerpersonalizado.ETC;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class Operation {
    public Operation(){}
    //nos permitira obtener coordenadas que vengan en un solo string example: "1.1,2.2"
    /*
       List<String> coor = obtenerCoordenadas("9.9,8.856");
        System.out.println("Latitud: "+coor.get(0)+" Longitud: "+coor.get(1));
    */
    public static List<String> obtenerCoordenadas(String location ){
        List<String> respuesta = new ArrayList<String>();
        char [] cara = location.toCharArray();
        StringBuilder re1 = new StringBuilder();
        StringBuilder re2 = new StringBuilder();
        boolean s1= false;
        for( int i=0 ; i < cara.length ; i++){
            if(cara[i] == ',')s1=true;
            if( (cara[i] != ',') && s1 != true ){
                re1.append( String.valueOf(cara[i]) );
            }
            if(s1){
                if( (cara[i] != ',') )
                    re2.append( String.valueOf( cara[i]));
            }
        }
        respuesta.add( re1.toString() ); respuesta.add( re2.toString() );
        return respuesta;
    }
    public static Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {
        int ancho = bitmap.getWidth();
        int alto = bitmap.getHeight();

        if (ancho > anchoNuevo || alto > altoNuevo) {
            float escalaAncho = anchoNuevo / ancho;
            float escalaAlto = altoNuevo / alto;

            Matrix matrix = new Matrix();
            matrix.postScale(escalaAncho, escalaAlto);

            return Bitmap.createBitmap(bitmap, 0, 0, ancho, alto, matrix, false);
        } else {
            return bitmap;
        }
    }
    //metodo para obtener hora y fecha actual
    public static  String obtener_fechaACTUAL(){
        StringBuilder stringBuilder = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        stringBuilder.append(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        stringBuilder.append("/");
        stringBuilder.append(String.valueOf(calendar.get(Calendar.MONTH)+1));
        stringBuilder.append("/");
        stringBuilder.append(String.valueOf(calendar.get(Calendar.YEAR)));
        return stringBuilder.toString();
    }
    //metodo que nos ayudara a saver si tenemos conexion a internet
    public static boolean verificar_conexionInternet(Context context){//veremos si tenemos conexion a internet
        boolean hay= true;
        //ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if( ( networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable() ) )
            hay = false;
        return hay;
    }
    //metodo para convertir un bitmap a string
    public static String convertirImagen_a_String(Bitmap img ){
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        img.compress( Bitmap.CompressFormat.JPEG, 100 , byteArrayOutputStream );
        byte[] imgByte= byteArrayOutputStream.toByteArray();
        String resimg = Base64.encodeToString( imgByte ,Base64.DEFAULT );
        return resimg;
    }

    public static boolean isValidEmail(String mail){
        return !TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }
    public static boolean isValidPassword( String pass){
        return pass.length() > 8;
    }
    public static boolean isValidTelefono(String tel ){
        if( tel.length() >= 9 ){
            return false;
        }else
            return ( (  (tel.length() > 7 ) && !TextUtils.isEmpty(tel) ) && Patterns.PHONE.matcher(tel).matches() );
    }
    public static boolean esSoloTexto(String nombre){
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        return patron.matcher(nombre).matches();
    }
    public static boolean esNombreValido(String nombre){
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        return patron.matcher(nombre).matches() || nombre.length() > 20;
    }
    public static boolean esCedulaValida(String nombre){
        Pattern patron = Pattern.compile("^[0-9 3]+^[a-zA-Z]+[-]");
        return patron.matcher(nombre).matches();
    }
    //extraeremos el nombre de la localidad,
    //ya sea cuando el valor de formatted_address del primer arreglo de results
    //no exista o tenga un valor como: Unnamed Road,neighborhood
    //extrae la ciudad,provincia,pueblo,comarca, nombre departamento,barrio segun las coordenadas
    public static String formatearLocalidad(String cadena ,int tipo ){
        char [] cara = cadena.toCharArray();
        int size= cara.length;
        boolean esEspacio=false;
        StringBuilder cadeRes= new StringBuilder();
        try{
            for(int i=0; i < size; i++ ){
                if( tipo == 1){// si es sacar info del campo compound_code
                    if(cara[i]==',')break;//si se llega a una coma ejemplo: C4C7+PQ León, Nicaragua nos saldremos
                    if(cara[i]==' ')esEspacio=true;
                    if(esEspacio)cadeRes.append(String.valueOf(cara[i]));
                }else if( tipo == 2){
//sacar info del campo "formatted_address" del primer(0) o penultimo(tamaño-2) objeto del arreglo "results" ejemplo: barrio el calvario,Leon
                    if(cara[i]==',')break;//si se llega a una coma ejemplo: SuperCiudad, NamePais nos saldremos
                    cadeRes.append(String.valueOf(cara[i]));
                }
            }
        }catch(Exception ex){
            System.err.println("Error de formateo de localidad: "+ex.getMessage());
            System.err.println("Error de formateo de localidad: "+ Arrays.toString(ex.getStackTrace()) );
        }finally{}
        return cadeRes.toString();
    }


}
