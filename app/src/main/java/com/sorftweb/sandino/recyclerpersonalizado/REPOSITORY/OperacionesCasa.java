package com.sorftweb.sandino.recyclerpersonalizado.REPOSITORY;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sorftweb.sandino.recyclerpersonalizado.ETC.Status;
import com.sorftweb.sandino.recyclerpersonalizado.INTERFACES.ListenerCasa;
import com.sorftweb.sandino.recyclerpersonalizado.MODELS.Casa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OperacionesCasa implements ListenerCasa {
    RequestQueue peti;
    Context context;
    String respuesta = "";
    boolean error_solicitud = false;
    List<Casa> lista;
    public OperacionesCasa(Context context){
        this.context = context;
        peti = Volley.newRequestQueue( this.context );
    }
    @Override
    public List<Casa> getTodasLasCasas() {
        String url = Status.web_service+"RestController.php?code="+"all_casas";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, ""+ response , Toast.LENGTH_SHORT).show();
                        respuesta = response;
                        try {
                            lista = new ArrayList<Casa>();
                            JSONObject jsonObject = new JSONObject(respuesta);
                            JSONArray jsonArray = jsonObject.getJSONArray("status");
                            if( jsonArray.getJSONObject(0).getString("response").equals("200 OK") ){
                                Toast.makeText( context, "entro en el if", Toast.LENGTH_SHORT).show();

                                for( int i = 0 ; i < jsonArray.length(); i++ ){
                                    Toast.makeText( context, "entro en el for", Toast.LENGTH_SHORT).show();

                                    JSONObject json = jsonArray.getJSONObject( i );
                                    Casa  casa = new Casa();
                                    casa.setId( json.getInt("id_casa") );
                                    casa.setIdUsuario( json.getInt("id_user") );
                                    casa.setFechaAlta( json.getString("fecha") );
                                    casa.setCantidadCuartos( json.getInt("cuartos") );
                                    casa.setPrecio( Float.valueOf(json.getString("precio")) );
                                    casa.setDireccion( json.getString("direc") );
                                    casa.setDescripcion( json.getString("descrip") );
                                    casa.setTelefono( json.getString("tele") );
                                    casa.setEstado( json.getString("estado") );
                                    lista.add( casa );
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error_solicitud = true;
                respuesta = error.getMessage() ;
            }
        });
        peti.add(stringRequest);
        return  lista;
    }
}
