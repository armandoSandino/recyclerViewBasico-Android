package com.sorftweb.sandino.recyclerpersonalizado;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.sorftweb.sandino.recyclerpersonalizado.FRAGMENTOS.FragmentoInicio;
import com.sorftweb.sandino.recyclerpersonalizado.FRAGMENTOS.FragmentoPerfil;
import com.sorftweb.sandino.recyclerpersonalizado.SQlite.DBLocal;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import android.support.v4.app.Fragment;

public class MainActivity extends AppCompatActivity{

    private DrawerLayout drawerLayout;
    String mi_ubicacion="0.0,0.0";
    CircleImageView imv_iconoAvatarUsuario;
    //ImageView imv_iconoAvatarUsuario;
    //SimpleDraweeView imv_iconoAvatarUsuario;
    TextView lb_CredencialesDrawable, lb_CredencialesCorreoDrawable;
    public static String user="Sarah Sandino",
            pass = "sandino", email="sarah@sorftweb.com";
    private static final String TAG = MainActivity.class.getSimpleName();
    DBLocal miDB;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind( this );
        SolicitarPermisos();
        init_component();
    }
    void init_component( ){
        requestQueue = Volley.newRequestQueue(this);
        Fresco.initialize( this );
        miDB =  new DBLocal(this,"DBLocal.sqlite", null , 1 );

        agregarToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            View view = navigationView.getHeaderView(0);
            //imv_iconoAvatarUsuario = (ImageView) view.findViewById(R.id.imv_iconoAvatarUsuario);
            imv_iconoAvatarUsuario = (CircleImageView ) view.findViewById(R.id.imv_iconoAvatarUsuario);
            //imv_iconoAvatarUsuario = (SimpleDraweeView)view.findViewById(R.id.imv_iconoAvatarUsuario);

            lb_CredencialesDrawable = (TextView) view.findViewById(R.id.lb_CredencialesDrawable);
            lb_CredencialesCorreoDrawable = (TextView) view.findViewById(R.id.lb_CredencialesCorreoDrawable);
            /*Bundle bundle = getIntent().getExtras();
            user = bundle.getString("usuario");
            pass = bundle.getString("contrasena");
            email = bundle.getString("email");
            lb_CredencialesDrawable.setText(bundle.getString("usuario"));
            lb_CredencialesCorreoDrawable.setText(bundle.getString("email"));
            if( USUARIO != null) {
                if( !USUARIO.getUrlAvatar().equals("NADA") ){
                    if( USUARIO.getAvatar() != null && !USUARIO.getAvatar().isEmpty() ){
                        imv_iconoAvatarUsuario.setImageBitmap( USUARIO.getImgAvatar() );
                    }
                }
            }*/
        }
        prepararDrawer(navigationView);
        // Seleccionar item por defecto
        seleccionarItem(navigationView.getMenu().getItem(0));
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
    private void seleccionarItem(MenuItem itemDrawer){
        try {
            String data ="no";

            Fragment fragmentoGenerico = null;
            FragmentManager fragmentManager = getSupportFragmentManager();

            switch (itemDrawer.getItemId()) {
                case R.id.item_menu_drawer_inicio: {
                    fragmentoGenerico = new FragmentoInicio();
                }break;
                case R.id.item_menu_drawer_cuenta:{
                    fragmentoGenerico = new FragmentoPerfil();
                }break;
                case R.id.item_menu_drawer_acercaDe:{
                    onCreateDialog(4).show();
                }break;
                case R.id.item_menu_drawer_chats:{
                    data = "si";
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT,"Prueba la nuava APP para contrataciones de vehiculos de transporte de carga, Descargala en " +
                            "http://juanballadares421.000webhostapp.com/cargandoservice1.1/appclient/cargando.apk");
                    //intent.setPackage("com.facLite.katana");
                    startActivity(intent);

                }break;
               default:break;
            }
            if (fragmentoGenerico != null) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.contenedor_principal, fragmentoGenerico)
                        .commit();
            }
            // Setear título actual
            if (data.equals("no")){
                setTitle(itemDrawer.getTitle());
            }
        }catch(Exception ex){
            ex.printStackTrace();ex.getStackTrace(); ex.getMessage();
            Log.e("ERROR:",ex.getStackTrace().toString() );
        }finally { }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (item.getItemId()) {
            case android.R.id.home: { // esta opcion pone a correr en navigation drawer
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }case R.id.action_acerca: {
                onCreateDialog(4).show();
                return true;
            }
            case R.id.action_salir:{
                Intent  intent = new Intent("mostrar_mensaje");
                intent.putExtra("clave","420");
                sendBroadcast(intent);
            }
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }


    //solicitar permisos si fueron negados
    private void SolicitarPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE }, 421 );
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch ( requestCode ){
            case 421:{// permisos de llamada

                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {//si no hay permiso, los solicitaremos de nuevo
                    SolicitarPermisos();
                }

            }break;
            default:break;
        }
    }
    protected Dialog onCreateDialog(int code) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (code == 4) {
            builder = builder.setTitle("CasaNica.com");
            builder = builder.setIcon(R.drawable.logo_buscar);
            builder = builder.setMessage("CasaNica es una aplicación mediante la cual podremos encontrar y contratar un lugar de alquiler.\n" +
                    "\n" + "\n" +
                    "Descripción: Encontrar lo que buscas es facil con CasaNica.\n" +
                    "\n" + "\n" +
                    "Mantenido por: Sorftweb \n" +
                    "Derechos Reservados by Sorftweb@Coorp. 2009-2019 ");
        }
        dialog = builder.create();
        return dialog;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();

    }
}
