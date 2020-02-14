package com.jennyfer.jenna.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jennyfer.jenna.Atributos.BuscadorAtributos;
import com.jennyfer.jenna.Atributos.HolderBuscador;
import com.jennyfer.jenna.BuzonDeCorreo.Amigos.Buscarusuario;
import com.jennyfer.jenna.R;

import java.util.List;

public class BuscadorAdapter extends RecyclerView.Adapter<HolderBuscador>  {

    private List<BuscadorAtributos> list;
    private Context context;
    private Buscarusuario bf;

    public BuscadorAdapter(List<BuscadorAtributos> list, Context context, Buscarusuario bf) {
        this.list = list;
        this.context = context;
        this.bf = bf;
    }

    @NonNull
    @Override
    public HolderBuscador onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_buscarusuario,viewGroup,false);
        return new HolderBuscador(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderBuscador holderBuscador, final int position) {
        Glide.with(context)
                .load(list.get(position).getFotoPerfil())
                .centerCrop()
                .thumbnail(0.2f)
                .into(holderBuscador.getPerfil());

        holderBuscador.getNombre().setText(list.get(position).getNombre());
        holderBuscador.getCarrera().setText(list.get(position).getCarrera());
        holderBuscador.getEstadoB().setText(String.valueOf(list.get(position).getEstado()));

        switch (list.get(position).getEstado()){
            //No se ha enviado solicitud para chatear
            case 1:
                holderBuscador.getEnviarS().setHint("Enviar solicitud");
                holderBuscador.getEnviarS().setBackgroundResource(R.drawable.boton_rojo);
                holderBuscador.getBotonTemp().setVisibility(View.GONE); //Esta vista es invisible y no ocupa espacio para el diseño.
                holderBuscador.getEnviarS().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bf.enviarSolicitud(list.get(position).getId());
                    }
                });
                holderBuscador.getCard().setOnLongClickListener(null);
                break;

            //  Solicitud pendiente a que el usuario receptor acepte
            case 2:
                holderBuscador.getEnviarS().setVisibility(View.GONE);

                holderBuscador.getBotonTemp().setHint("Cancelar");
                holderBuscador.getBotonTemp().setBackgroundResource(R.drawable.boton_gris);
                holderBuscador.getBotonTemp().setVisibility(View.VISIBLE);
                holderBuscador.getBotonTemp().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bf.cancelarSolicitud(list.get(position).getId());
                    }
                });
                holderBuscador.getCard().setOnLongClickListener(null);
                break;

             //Solicitud pendiente a aceptar
            case 3:
                holderBuscador.getEnviarS().setHint("Aceptar");
                holderBuscador.getEnviarS().setBackgroundResource(R.drawable.boton_verde);
                holderBuscador.getEnviarS().setVisibility(View.VISIBLE);
                holderBuscador.getEnviarS().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bf.aceptarSolicitud(list.get(position).getId());
                    }
                });

                holderBuscador.getBotonTemp().setHint("Cancelar");
                holderBuscador.getBotonTemp().setBackgroundResource(R.drawable.boton_gris);
                holderBuscador.getBotonTemp().setVisibility(View.VISIBLE);
                holderBuscador.getBotonTemp().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bf.cancelarSolicitud(list.get(position).getId());
                    }
                });

                holderBuscador.getCard().setOnLongClickListener(null);
                break;

             //Acepto de solicitud para chatear
            case 4:
                holderBuscador.getEnviarS().setHint("Ver perfil");
                holderBuscador.getEnviarS().setVisibility(View.VISIBLE);
                holderBuscador.getEnviarS().setBackgroundResource(R.drawable.boton_azul);
                holderBuscador.getBotonTemp().setVisibility(View.GONE);

                holderBuscador.getCard().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        new AlertDialog.Builder(context)
                                .setMessage("¿Estás seguro de eliminar a este amigo?")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        bf.eliminarUsuario(list.get(position).getId());
                                    }
                                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
                        return true;
                    }
                });
               /*holderBuscador.getCard().setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add("Eliminar").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            bf.eliminarUsuario(list.get(position).getId());
                            return true;
                        }
                    });
                }
            });*/
                break;
        }
    }

    @Override
    public int getItemCount() { return list.size(); }

}
