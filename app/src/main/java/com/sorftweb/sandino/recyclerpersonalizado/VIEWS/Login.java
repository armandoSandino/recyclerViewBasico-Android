package com.sorftweb.sandino.recyclerpersonalizado.VIEWS;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.sorftweb.sandino.recyclerpersonalizado.MainActivity;
import com.sorftweb.sandino.recyclerpersonalizado.R;

import java.util.Hashtable;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity {
    @BindView(R.id.input_correoFormlogin)
    TextInputLayout input_correoFormlogin;
    @BindView(R.id.ct_correoFormlogin)
    TextInputEditText ct_correoFormlogin;

    @BindView(R.id.input_passFormlogin)
    TextInputLayout input_passFormlogin;
    @BindView(R.id.ct_passFormlogin)
    TextInputEditText ct_passFormlogin;

    @BindView(R.id.bt_aceptarFormlogin)
    Button bt_aceptarFormlogin;
    @BindView(R.id.ch_recordar_credencialesFormlogin)
    CheckBox ch_recordar_credenciales;
    @BindView(R.id.imv_popup_listCuentasLogin)
    ImageView imv_popup_listCuentasLogin;

    @BindView(R.id.lb_crearCuentaFormlogin)
    TextView lb_crearCuentaFormlogin;

    RequestQueue peti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind( this );
        peti = Volley.newRequestQueue( this );
        bindListenerCajas();
        bt_aceptarFormlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar_datos(v );
            }
        });
        lb_crearCuentaFormlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( Login.this , RegistrarUsuario.class ));
            }
        });
    }
    private void validar_datos(View  v){
        if( ! Operation.isValidEmail(ct_correoFormlogin.getText().toString().trim() ) ){

            Snackbar.make( v , "Ingrese un correo valido." , Snackbar.LENGTH_LONG).show();
            return;
        }
        if( ! Operation.isValidPassword( ct_passFormlogin.getText().toString().trim() ) ){
            Snackbar.make( v , "Ingrese una contraseña valida." , Snackbar.LENGTH_LONG).show();
            return;
        }
        uploadDatos();
    }
    private void uploadDatos() {
        String url = Status.web_service+"UsuarioRestController.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent( Login.this , MainActivity.class );
                        startActivity(  intent );
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, ""+ error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> para = new Hashtable<>();
                para.put("email",ct_correoFormlogin.getText().toString().trim() );
                para.put("pass", ct_passFormlogin.getText().toString().trim() );
                return super.getParams();
            }
        };
        peti.add(stringRequest);
    }
    public void Mensaje(String mensaje){
        AlertDialog.Builder alertzync = new AlertDialog.Builder(this);
        alertzync.setTitle("");
        alertzync.setMessage(""+mensaje);
        alertzync.setIcon(R.drawable.logo_buscar);
        alertzync.setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialogss = alertzync.create();
        alertDialogss.show();
    }
    //controlamos lo que el usuario escribe en los TextInputEditeText
    private void bindListenerCajas() {
        ct_correoFormlogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String val=null;
                if ( !Operation.isValidEmail( ct_correoFormlogin.getText().toString().trim() ) ){
                    setMessageTextInputLayoutError( input_correoFormlogin ,"dirección de correo invalida");
                }else{
                    setMessageTextInputLayoutError( input_correoFormlogin  ,val );
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ct_passFormlogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String val=null;
                if ( !Operation.isValidPassword( ct_passFormlogin.getText().toString().trim() ) ){
                    setMessageTextInputLayoutError( input_passFormlogin ,"introduce mas de 8 caracteres");
                }else{
                    setMessageTextInputLayoutError( input_passFormlogin,val );
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
