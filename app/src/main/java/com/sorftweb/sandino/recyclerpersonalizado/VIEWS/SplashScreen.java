package com.sorftweb.sandino.recyclerpersonalizado.VIEWS;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



import android.database.Cursor;

import com.sorftweb.sandino.recyclerpersonalizado.R;
import com.sorftweb.sandino.recyclerpersonalizado.SQlite.DBLocal;

public class SplashScreen extends AppCompatActivity {
    DBLocal miDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen );
        //miDB =  new DBLocal(this,"DBLocal.sqlite", null , 1 );
        Thread splash=new Thread(){
            public void run(){
                try{
                    sleep(4*1000);

                    /*SQLiteDatabase dbr = miDB.getReadableDatabase();//select
                    Cursor c = dbr.rawQuery("SELECT * FROM usuario", null);
                    int cantidad = c.getCount();
                    if( cantidad > 0 ){
                        Intent splash_intent=new Intent(getApplicationContext(), Login.class );
                        //splash_intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(splash_intent);
                    }else{
                        Intent splash_intent=new Intent(getApplicationContext(), ActivityMainScreenNext.class );
                        //splash_intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(splash_intent);
                    }*/
                    Intent splash_intent=new Intent(getApplicationContext(),Login.class);
                    //splash_intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(splash_intent);
                    finish();
                }catch (Exception e){

                }
            }
        };
        splash.start();
    }
}