package com.sorftweb.sandino.recyclerpersonalizado.VIEWS;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.sorftweb.sandino.recyclerpersonalizado.ETC.Operation;
import com.sorftweb.sandino.recyclerpersonalizado.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Contactar extends AppCompatActivity  implements View.OnClickListener{
    @BindView(R.id.lb_terminosLegales)
    TextView lb_terminosLegales;
    @BindView(R.id.ch_avisos_legales)
    CheckBox ch_avisos_legales;
    @BindView(R.id.card_contactar)
    CardView card_contactar;
    @BindView(R.id.bt_contactar)
    Button bt_contactar;

    @BindView(R.id.ct_infoSolic_registrarSolic)
    TextInputEditText ct_infoSolic_registrarSolic;

    @BindView(R.id.input_nombre_registrarSolic)
    TextInputLayout input_nombre_registrarSolic;
    @BindView(R.id.ct_nombre_registrarSolic)
    TextInputEditText ct_nombre_registrarSolic;

    @BindView(R.id.input_correo_registrarSolic)
    TextInputLayout input_correo_registrarSolic;
    @BindView(R.id.ct_correo_registrarSolic)
    TextInputEditText ct_correo_registrarSolic;

    @BindView(R.id.input_telefono_registrarSolic)
    TextInputLayout input_telefono_registrarSolic;
    @BindView(R.id.ct_telefono_registrarSolic)
    TextInputEditText ct_telefono_registrarSolic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactar);
        ButterKnife.bind( this );
        Intent intent = this.getIntent();
        bindListenerCajas();

        bt_contactar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !ch_avisos_legales.isChecked() ){
                    Snackbar.make( v , "Debes aceptar los terminos  y condiciones.",Snackbar.LENGTH_LONG).show();
                    return;
                }
                validar_datos();
            }
        });
    }
    private void validar_datos() {
        if( ct_infoSolic_registrarSolic.getText().toString().trim().isEmpty()
             || !Operation.esSoloTexto(ct_infoSolic_registrarSolic.getText().toString().trim() )   ){
            Toast.makeText(this, "ingresa una descripcion breve a tu solicitud", Toast.LENGTH_SHORT).show();
            return;
        }if( ct_nombre_registrarSolic.getText().toString().trim().isEmpty()
                || !Operation.esNombreValido( ct_nombre_registrarSolic.getText().toString().trim() ) ){
            Toast.makeText(this, "ingresa un nombre valido ", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( ct_correo_registrarSolic.getText().toString().trim().isEmpty()
                || !Operation.isValidEmail(ct_correo_registrarSolic.getText().toString().trim() ) ){
            Toast.makeText(this, "ingresa un correo valido.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ct_telefono_registrarSolic.getText().toString().trim().isEmpty()
                || !Operation.isValidTelefono(ct_telefono_registrarSolic.getText().toString().trim() ) ){
            Toast.makeText(this, "ingresa un numero valido", Toast.LENGTH_SHORT).show();
            return;
        }
        finish();
    }

    //controlamos lo que el usuario escribe en los TextInputEditeText
    private void bindListenerCajas() {
        ct_nombre_registrarSolic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String val=null;
                if ( !Operation.esNombreValido( ct_nombre_registrarSolic.getText().toString().trim() ) ){
                    setMessageTextInputLayoutError( input_nombre_registrarSolic ,"Introduce solo texto");
                }else{
                    setMessageTextInputLayoutError( input_nombre_registrarSolic ,val );
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        ct_correo_registrarSolic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String val=null;
                if ( !Operation.isValidEmail(ct_correo_registrarSolic.getText().toString().trim() ) ){
                    setMessageTextInputLayoutError( input_correo_registrarSolic ,"dirección de correo invalida");
                }else{
                    setMessageTextInputLayoutError( input_correo_registrarSolic  ,val );
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
            ct_telefono_registrarSolic.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String val=null;
                    if ( !Operation.isValidTelefono( ct_telefono_registrarSolic.getText().toString().trim() ) ){
                        setMessageTextInputLayoutError( input_telefono_registrarSolic  ,"número invalido");
                    }else{
                        setMessageTextInputLayoutError( input_telefono_registrarSolic  ,val );
                    }
                }
                @Override
                public void afterTextChanged(Editable s) { }
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
    public void onClick(View v) {

    }

}
