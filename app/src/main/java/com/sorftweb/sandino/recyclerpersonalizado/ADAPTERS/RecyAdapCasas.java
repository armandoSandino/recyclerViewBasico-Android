package com.sorftweb.sandino.recyclerpersonalizado.ADAPTERS;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sorftweb.sandino.recyclerpersonalizado.MODELS.Casa;
import com.sorftweb.sandino.recyclerpersonalizado.R;
import com.sorftweb.sandino.recyclerpersonalizado.VIEWS.Contactar;

import java.util.List;

public class RecyAdapCasas extends RecyclerView.Adapter<RecyAdapCasas.RecyAdapCasasHolder>{
    List<Casa> lista;
    int layout;
    Context context;
    RecyAdapCasas.MiClickItemListener listener;
    public RecyAdapCasas(List<Casa> lista , int layout, Context context , RecyAdapCasas.MiClickItemListener  lis ){
        this.lista = lista;
        this.layout = layout;
        this.context = context;
        this.listener = lis;
    }
    @NonNull
    @Override
    public RecyAdapCasasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() )
                .inflate( layout , parent , false);
        //RecyAdapCasasHolder holder = new RecyAdapCasas.RecyAdapCasasHolder(view);
        return new RecyAdapCasas.RecyAdapCasasHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyAdapCasasHolder holder, int position) {
        holder.volcar_datos( lista.get(position) , listener );
    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }

    public class RecyAdapCasasHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Context contextH;
        ImageView imv_item_foto_casa;
        TextView lb_item_num_habitaciones ,lb_item_precio_casa,lb_item_ubicacion_casa;
        Button bt_item_contactar , bt_item_llamar;
        public RecyAdapCasasHolder(View itemView) {
            super(itemView);
            contextH = itemView.getContext();
            imv_item_foto_casa =itemView.findViewById(R.id.imv_item_foto_casa);
            lb_item_num_habitaciones =(TextView) itemView.findViewById(R.id.lb_item_num_habitaciones);
            lb_item_precio_casa =(TextView) itemView.findViewById(R.id.lb_item_precio_casa);
            lb_item_ubicacion_casa =(TextView) itemView.findViewById(R.id.lb_item_ubicacion_casa);
            bt_item_llamar =(Button) itemView.findViewById(R.id.bt_item_llamar);
            bt_item_contactar=(Button)itemView.findViewById(R.id.bt_item_contactar);
        }
        //metodo que se encarga de quemar la informacion en cada elemento de la lista y establecer sus eventos click
         void volcar_datos( final Casa dato ,final RecyAdapCasas.MiClickItemListener escuchador ){

            //como solo tenemos tres registros, verificaremos que foto le corresponde a cada elemento, esto puede cambiar claro esta.
            switch (dato.getFoto()){
                case "uno":{
                    imv_item_foto_casa.setImageResource(R.drawable.uno);
                } break;
                case "dos":{
                    imv_item_foto_casa.setImageResource(R.drawable.dos);
                }break;
                case "tres":{
                    imv_item_foto_casa.setImageResource(R.drawable.tres);
                }break;
                default: imv_item_foto_casa.setImageResource(R.drawable.tres);
                    break;
            }
            bt_item_llamar.setOnClickListener(this);
            bt_item_contactar.setOnClickListener( this );
            lb_item_ubicacion_casa.setText(dato.getDireccion());
            lb_item_precio_casa.setText("C$ "+String.valueOf(dato.getPrecio())+" /mes");
            lb_item_num_habitaciones.setText(dato.getCantidadCuartos()+" cuartos");
            bt_item_llamar.setText(dato.getTelefono());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    escuchador.MiClickItem( dato , getAdapterPosition() );
                }
            });
        }
        @Override
        public void onClick(View v) {
            switch (v.getId() ){
                case R.id.bt_item_llamar:{
                    if ( lista.get( getAdapterPosition() ).getTelefono().length()>=8){
                        Intent llamar = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +lista.get(getAdapterPosition()).getTelefono() ) );
                        if (ActivityCompat.checkSelfPermission( contextH , Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        contextH.startActivity(llamar);
                    }else {
                        Toast.makeText(contextH, "Nùmero Indefinido", Toast.LENGTH_SHORT).show();
                    }
                }break;
                case R.id.bt_item_contactar:{
                    Intent intent = new Intent(contextH , Contactar.class);
                    intent.putExtra("id_casa",String.valueOf( lista.get(getAdapterPosition()).getId()) );
                    intent.putExtra("id_user",String.valueOf( 420 ) );
                    contextH.startActivity( intent );
                }break;
                default:break;
            }
        }
    }
    //definimos nuestra propia interfaz,para manejar el evento click
    public interface MiClickItemListener{
        void MiClickItem(Casa casa, int posicion );
    }
}
