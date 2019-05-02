package com.sorftweb.sandino.recyclerpersonalizado.ETC;



public class Status {
    //public static Usuario USUARIO= new Usuario();
    public static String UserName="";
    public static String Password="";
    public static String CiudadActual="INDEFINIDO";
    public static String PaisActual="INDEFINIDO";
    public static int estado=0; // nos servira para  saver si esta permitido el acceso a ala app por parte del usuario
    public static int session; // verificamos si ya ha iniciado session
    public static  boolean statusDetAuto=false ;// verificara si ya fueron volcados los datos del vehiculo en la activityDeTalMap
    public static double longitud = 0.0;
    public static  double latitud = 0.0;
    public static  double altitud=0.0;
    public static  boolean gps_vivo=false;
    //public static String web_service="http://10.0.2.2/cargandoservice1.1/appclient/";
    //public static String web_service="http://192.168.137.1/casanica/";
    public static String  web_service="http://192.168.42.96/casanica/"; //red compartida por usb
    //public static String web_service="http://169.254.245.43/cargandoservice1.1/appclient/";
    //public static String web_service="http://juanballadares421.000webhostapp.com/casanica/appclient/";
    //public static int vista_lista_inicio=0; // nos ayudara a  ahcer que la lista del fragmento del inicio no cresca  a cada  momento

    //Mensajeria e Notification con Firebase, MySQL
    public static String TOKEN="INDEFINIDO";
    public static String ACCION = "accion";
    public static String ESTADO = "";
    public static String PUBLICA = "";
    public static String PRIVADA = "";

}
