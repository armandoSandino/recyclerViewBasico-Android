package com.sorftweb.sandino.recyclerpersonalizado.VIEWS;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sorftweb.sandino.recyclerpersonalizado.ETC.Operation;
import com.sorftweb.sandino.recyclerpersonalizado.ETC.Status;
import com.sorftweb.sandino.recyclerpersonalizado.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrarUsuario extends AppCompatActivity  implements View.OnClickListener{
    @BindView(R.id.input_nombreFormUser)
    TextInputLayout input_nombreFormUser;
    @BindView(R.id.ct_nombreFormUser)
    TextInputEditText ct_nombreFormUser;

    @BindView(R.id.input_correoFormUser)
    TextInputLayout input_correoFormUser;
    @BindView(R.id.ct_correoFormUser)
    TextInputEditText ct_correoFormUser;

    @BindView(R.id.input_passFormUser)
    TextInputLayout input_passFormUser;
    @BindView(R.id.ct_passFormUser)
    TextInputEditText ct_passFormUser;

    @BindView(R.id.input_passRepeFormUser)
    TextInputLayout input_passRepeFormUser;
    @BindView(R.id.ct_passRepeFormUser)
    TextInputEditText ct_passRepeFormUser;

    @BindView(R.id.card_passRepeFormUser)
    CardView card_passRepeFormUser;
    @BindView(R.id.bt_aceptarFormUser)
    Button bt_aceptarFormUser;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        ButterKnife.bind(this );
        requestQueue = Volley.newRequestQueue( this );
        card_passRepeFormUser.setVisibility(View.GONE );
        bindListenerCajas();
        bt_aceptarFormUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                validar_informacion();
            }
        });

    }
    void validar_informacion(){
        if( !Operation.esSoloTexto( ct_nombreFormUser.getText().toString().trim() ) ){
            Toast.makeText(this, "Ingresa un nombre valido, solo texto.", Toast.LENGTH_SHORT).show();
            return;
        }
        if( !Operation.isValidEmail(ct_correoFormUser.getText().toString().trim() ) ){
            Toast.makeText(this, "Ingresa una direccion de correo valida.", Toast.LENGTH_SHORT).show();
            return;
        }

        if( !Operation.isValidPassword(ct_passFormUser.getText().toString().trim())
                || !Operation.isValidPassword(ct_passRepeFormUser.getText().toString().trim() ) ){
            Toast.makeText(this, "Ingrese una contraseña valida.", Toast.LENGTH_SHORT).show();
            return;
        }
        if( !ct_passFormUser.getText().toString().trim().equals(
                ct_passRepeFormUser.getText().toString().trim()
        ) ){
            Toast.makeText(this, "Las contraseña no son iguales.", Toast.LENGTH_SHORT).show();
            return;
        }
        uploadData();
    }

    @Override
    public void onClick(View v) {

    }
    private  void uploadData(){
        final ProgressDialog progressDialog = new ProgressDialog( this );
        progressDialog.setCancelable( false );
        progressDialog.setIndeterminate( false );
        progressDialog.setTitle("Creando cuenta");
        progressDialog.setMessage("espere por favor...");
        if( !progressDialog.isShowing() )
            progressDialog.show();
        String url = Status.web_service+"UsuarioRestController.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            JSONArray jsonArray = jsonObject.getJSONArray("status");
                            JSONObject json = jsonArray.getJSONObject(0);
                            if( json.getString("response").equals("200 OK") ){
                                Mensaje("Cuenta Creada Exitosamente.");
                            } if( json.getString("response").equals("EXISTS") ){
                                Mensaje("Existe una cuenta con esta direccion de correo.");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                System.err.println("Error"+ error.getMessage() + error.getStackTrace().toString() );
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> para = new Hashtable<>();
                para.put("code","insert");
                para.put("fecha",Operation.obtener_fechaACTUAL() );
                para.put("nombre", ct_nombreFormUser.getText().toString().trim() );
                para.put("correo", ct_correoFormUser.getText().toString().trim() );
                para.put("pass", ct_passFormUser.getText().toString().trim() );
                return  para;
            }
        };
        requestQueue.add( stringRequest );
    }
    //controlamos lo que el usuario escribe en los TextInputEditeText
    private void bindListenerCajas() {
        ct_nombreFormUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String val=null;
                if ( !Operation.esNombreValido( ct_nombreFormUser.getText().toString().trim() ) ){
                    setMessageTextInputLayoutError( input_nombreFormUser ,"Introduce solo texto");
                }else{
                    setMessageTextInputLayoutError( input_nombreFormUser ,val );
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        ct_correoFormUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String val=null;
                if ( !Operation.isValidEmail( ct_correoFormUser.getText().toString().trim() ) ){
                    setMessageTextInputLayoutError( input_correoFormUser ,"dirección de correo invalida");
                }else{
                    setMessageTextInputLayoutError( input_correoFormUser  ,val );
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ct_passFormUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String val=null;
                if ( !Operation.isValidPassword( ct_passFormUser.getText().toString().trim() ) ){
                    setMessageTextInputLayoutError( input_passFormUser ,"introduce mas de 8 caracteres");
                    card_passRepeFormUser.setVisibility( View.GONE );
                }else{
                    setMessageTextInputLayoutError( input_passFormUser,val );
                    card_passRepeFormUser.setVisibility( View.VISIBLE );
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ct_passRepeFormUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String val=null;
                if ( !Operation.isValidPassword( ct_passRepeFormUser.getText().toString().trim() ) ){
                    setMessageTextInputLayoutError( input_passRepeFormUser ,"introduce mas de 8 caracteres");
                }else{
                    setMessageTextInputLayoutError( input_passRepeFormUser ,val );
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
    //mostramos y ocultamos los mensajes de error en los TextInputLayout
    private  void setMessageTextInputLayoutError(@NonNull TextInputLayout textInputLayout, String msg) {
        textInputLayout.setError(msg);
        if (msg == null) {
            textInputLayout.setErrorEnabled(false);
        } else {
            textInputLayout.setErrorEnabled(true);
        }
    }
    public void Mensaje(String mensaje){
        AlertDialog.Builder alertzync = new AlertDialog.Builder(RegistrarUsuario.this);
        alertzync.setTitle("");
        alertzync.setMessage(""+mensaje);
        alertzync.setIcon(R.drawable.logo_buscar);
        alertzync.setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        /*Intent intent = new Intent(RegistrarUsuario.this , RegistrarUsuarioFotosTest.class);
                        intent.putExtra("ID_USER", String.valueOf(usuario.getIdUsuario()));
                        intent.putExtra("tipoCuenta",TipoDeCuenta);
                        //intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity( intent );*/
                        finish();
                    }
                });
        AlertDialog alertDialogss = alertzync.create();
        alertDialogss.show();
    }

}
