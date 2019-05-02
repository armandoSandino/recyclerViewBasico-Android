package com.sorftweb.sandino.recyclerpersonalizado.FRAGMENTOS;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sorftweb.sandino.recyclerpersonalizado.ADAPTERS.DecoracionLineaDivisoria;
import com.sorftweb.sandino.recyclerpersonalizado.ADAPTERS.RecyAdapCasas;
import com.sorftweb.sandino.recyclerpersonalizado.ETC.BaseVolleyFragment;
import com.sorftweb.sandino.recyclerpersonalizado.ETC.Status;
import com.sorftweb.sandino.recyclerpersonalizado.MODELS.Casa;
import com.sorftweb.sandino.recyclerpersonalizado.MainActivity;
import com.sorftweb.sandino.recyclerpersonalizado.R;
import com.sorftweb.sandino.recyclerpersonalizado.REPOSITORY.OperacionesCasa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class FragmentoInicio extends BaseVolleyFragment {
    ProgressDialog progressDialog;
    List<Casa>lista;
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    BroadcastReceiver fragmentUpdateDatos;
    Context contextFragInicio;
    OperacionesCasa operacionesCasa;
    TextView lb_nohaycasas , lb_casasDisponibles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext() )
                .inflate( R.layout.fragmento_inicio , container , false );
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_listar_casas );
        lb_nohaycasas = view.findViewById(R.id.lb_nohaycasas);
        lb_casasDisponibles = view.findViewById(R.id.lb_casasDisponibles);
        lb_nohaycasas.setVisibility( View.GONE );
        contextFragInicio = getActivity();

        init_component();

        /* preparamo al fragmento para escuchar el brodcast*/
        fragmentUpdateDatos = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String mensaje = intent.getExtras().getString("clave");
                if (mensaje.equals("420")){
                    Toast.makeText(context, "actualizar interfaz ", Toast.LENGTH_SHORT).show();
                }

            }
        };
        return  view;
    }
    private void init_component() {

        //lista = quemar_datos();
        String url = Status.web_service+"RestController.php?code="+"all_casas";
        getTodasLasCasas( url );
    }
    protected Dialog onCreateDialog(int code, String contenido ) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext() );
        if (code == 4) {
            builder = builder.setTitle("CasaNica.com");
            builder = builder.setIcon(R.drawable.logo_buscar);
            builder = builder.setMessage("CasaNica es una aplicación mediante la cual podremos encontrar y contratar un lugar de alquiler.\n" +
                    "\n" + "\n" +
                    "Descripción: Encontrar lo que buscas es facil con CasaNica.\n" +
                    "\n" + "\n" +
                    "Mantenido por: Sorftweb \n" +
                    "Derechos Reservados by Sorftweb@Coorp. 2009-2019 ");
        }else{
            builder.setIcon(R.drawable.logo_buscar);
            builder.setMessage( contenido );
        }
        dialog = builder.create();
        return dialog;
    }
    /*actualizar los puntos en el fragment mapa
    Intent  intent = new Intent("LLAVE");
                        intent.putExtra("actualiza","update_map");
    getContext().sendBroadcast(intent); con este codigo disparo el brodcas  */

    @Override
    public void onResume() {
        super.onResume();
        contextFragInicio.registerReceiver( fragmentUpdateDatos ,new IntentFilter("mostrar_mensaje"));
    }
    @Override
    public void onPause(){
        super.onPause();
        contextFragInicio.unregisterReceiver(  fragmentUpdateDatos );
    }
    private void getTodasLasCasas(String url ) {
        progressDialog = new ProgressDialog( getContext() );
        progressDialog.setCancelable( false );
        progressDialog.setIndeterminate( false );
        progressDialog.setTitle("Cargando..");
        progressDialog.setMessage("espere un momento");
        if( !progressDialog.isShowing() )
            progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            lista = new ArrayList<Casa>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("status");
                            if( jsonArray.getJSONObject(0).getString("response").equals("200 OK") ){

                                for( int i = 0 ; i < jsonArray.length(); i++ ){
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
                                layoutManager = new LinearLayoutManager( getContext() );
                                if( lista.size() <= 0 ){
                                    lb_nohaycasas.setVisibility(View.VISIBLE );
                                    lb_casasDisponibles.setVisibility( View.GONE );
                                    progressDialog.hide();
                                    return;
                                }
                                lb_nohaycasas.setVisibility(View.GONE );
                                lb_casasDisponibles.setVisibility( View.VISIBLE);
                                adapter = new RecyAdapCasas(lista, R.layout.item_listar_casas, getContext() , new RecyAdapCasas.MiClickItemListener() {
                                    @Override
                                    public void MiClickItem(Casa casa, int posicion) {
                                        onCreateDialog(4, "").show();
                                    }
                                });
                                recyclerView.setItemAnimator( new DefaultItemAnimator() );
                                recyclerView.addItemDecoration( new DecoracionLineaDivisoria(getContext() ));
                                recyclerView.setLayoutManager( layoutManager );
                                recyclerView.setAdapter( adapter );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lb_nohaycasas.setVisibility(View.VISIBLE );
                lb_casasDisponibles.setVisibility( View.GONE );

                System.err.println("Eror de Volley : "+ error.getMessage() );
                Log.e("ErrVoll",error.getMessage());
                error.printStackTrace();
                error.getStackTrace();
            }
        });
        addToQueue( stringRequest );
        progressDialog.dismiss();
    }
    private List<Casa> quemar_datos() {
        List<Casa> list = new ArrayList<Casa>();
        /*Casa casa1 = new Casa(1,2,150,"uno","Guadalupe","234345344");
        Casa casa2 = new Casa(2,3,200,"dos","San Felipe","34125678");
        Casa casa3 = new Casa(3,2,180,"tres","Sutiava","78458923");
        list.add(casa1); list.add(casa2); list.add(casa3);*/
        return list;
    }

}
