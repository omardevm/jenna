package com.jennyfer.jenna.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jennyfer.jenna.Atributos.AmigosAtributos;
import com.jennyfer.jenna.BuzonDeCorreo.Amigos.ListAmigos;
import com.jennyfer.jenna.BuzonDeCorreo.Amigos.Mensajeria;
import com.jennyfer.jenna.R;

import java.util.List;

public class AmigosAdapter extends RecyclerView.Adapter<AmigosAdapter.HolderAmigos> {
    private List<AmigosAtributos> atributosList;
    private Context context;
    private ListAmigos la;

    public AmigosAdapter (List<AmigosAtributos> atributosList, Context context, ListAmigos la){
        this.atributosList = atributosList;
        this.context = context;
        this.la = la;
    }

    @NonNull
    @Override
    public HolderAmigos onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_amigos,parent,false);
        return new HolderAmigos(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAmigos holderAmigos, final int position) {

        Glide.with(context)
                .load(atributosList.get(position).getFotoPerfil())
                .centerCrop()
                .thumbnail(0.2f)
                .into(holderAmigos.foto);

        holderAmigos.nombreA.setText(atributosList.get(position).getNombre());
        holderAmigos.mensajeA.setText(atributosList.get(position).getMensajito());
        holderAmigos.horaA.setText(atributosList.get(position).getHoramensajito());

        if (atributosList.get(position).getMensajito().equals("null")){
            holderAmigos.horaA.setVisibility(View.GONE);
            holderAmigos.mensajeA.setText("Ya puedes iniciar una platica");
        }else {
            holderAmigos.horaA.setVisibility(View.VISIBLE);
            if (atributosList.get(position).getTipoM().equals("1")){
                holderAmigos.mensajeA.setTextColor(ContextCompat.getColor(context,R.color.pantone504));
            }else {
                holderAmigos.mensajeA.setTextColor(ContextCompat.getColor(context,R.color.pantone465));
            }
        }

        holderAmigos.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Mensajeria.class); //ListAmigos.class
                i.putExtra("key_receptor",atributosList.get(position).getId());
                i.putExtra("key_nombre",atributosList.get(position).getNombre());
                context.startActivity(i);
            }
        });


        holderAmigos.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage("¿Estás seguro de eliminar a este amigo?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                la.eliminarAmigo(atributosList.get(position).getId());
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
    }

    @Override
    public int getItemCount() { return atributosList.size(); }

    static class HolderAmigos extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView foto;
        TextView nombreA;
        TextView mensajeA;
        TextView horaA;

        public HolderAmigos(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_amigos);
            foto     = itemView.findViewById(R.id.fotoAmigo);
            nombreA  = itemView.findViewById(R.id.usuarioAmigo);
            mensajeA = itemView.findViewById(R.id.mensajeAmigo);
            horaA    = itemView.findViewById(R.id.horaAmigo);
        }
    }
}
