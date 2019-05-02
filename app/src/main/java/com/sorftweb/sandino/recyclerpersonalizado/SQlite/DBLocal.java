package com.sorftweb.sandino.recyclerpersonalizado.SQlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBLocal extends SQLiteOpenHelper{
    String tablaUsuario="CREATE TABLE  usuario(Id_usuario INTEGER PRIMARY KEY ,"+
            "FechaAlta TEXT,Nombres TEXT,Apellidos TEXT,NombreUsuario TEXT,Sexo TEXT ,DNI TEXT,FechaNacimiento TEXT,Correo TEXT, Direccion TEXT," +
            "Departamento TEXT,Sobre_mi TEXT,Contraseña TEXT,Token TEXT,TipoCuenta TEXT,Estado INTEGER,Activo INTEGER,Licencia TEXT,Avatar TEXT," +
            "AvatarCode TEXT,Telefono1 TEXT,TipoTel1 TEXT,Telefono2 TEXT,TipoTel2 TEXT,Telefono3 TEXT,TipoTel3 TEXT)";

    String tablaClaves="CREATE TABLE claves(Id_key INTEGER PRIMARY KEY AUTOINCREMENT, clave_publica TEXT,clave_privada TEXT)";

    String tablaDepartamento="CREATE TABLE departamento(Id_departamento INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT,Pais TEXT,PaisCode TEXT)";

    String tablaPublicacion="CREATE TABLE publicacion(Id_publicacion INTEGER PRIMARY KEY, IdUsuario INTEGER," +
            "Descripcion TEXT,Estado TEXT,TipoCarga TEXT,FechaPublicacion TEXT)";


    public DBLocal(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tablaUsuario);
        db.execSQL(tablaClaves);
        db.execSQL(tablaDepartamento);
        db.execSQL(tablaPublicacion);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //se elimina la version anterior de la tablas
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS claves");
        db.execSQL("DROP TABLE IF EXISTS departamento");
        db.execSQL("DROP TABLE IF EXISTS publicacion");
        //creasmo la nueva version de las tablas
        db.execSQL(tablaUsuario);
        db.execSQL(tablaClaves);
        db.execSQL(tablaDepartamento);
        db.execSQL(tablaPublicacion);

    }
}
