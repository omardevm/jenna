package com.jennyfer.jenna.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jennyfer.jenna.Atributos.ActividadesAtributos;
import com.jennyfer.jenna.CentroDeActividades.ActividadesAlumno;
import com.jennyfer.jenna.CentroDeActividades.Tareas.VistaActividad;
import com.jennyfer.jenna.R;

import java.util.List;

public class ActividadesAdapter extends RecyclerView.Adapter<ActividadesAdapter.HolderActividades> {
    private List<ActividadesAtributos> atributosL;
    private Context context;

    public ActividadesAdapter (List<ActividadesAtributos> atributosL, Context context, ActividadesAlumno actividadesAlumno){
        this.atributosL = atributosL;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderActividades onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_actividades,parent,false);
        return new HolderActividades(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderActividades holder, final int position) {
        holder.titulo.setText(atributosL.get(position).getTitulo());
        holder.descripcion.setText(atributosL.get(position).getDescripcion());
       // holder.estadoAc.setText(atributosL.get(position).getEstadoA());
        holder.fechaPubli.setText(atributosL.get(position).getFechaPublicacion() );
        Glide.with(context)
                .load(R.drawable.maletin)
                .centerCrop()
                .thumbnail(0.2f)
                .into(holder.icono);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, VistaActividad.class);
                //i.putExtra("key_id",atributosL.get(position).getIdA());
                i.putExtra("key_titulo", atributosL.get(position).getTitulo());
                i.putExtra("key_descripcion", atributosL.get(position).getDescripcion());
                i.putExtra("key_fecha", atributosL.get(position).getFechaA());
                i.putExtra("key_hora", atributosL.get(position).getHoraA());
                i.putExtra("key_grupo",atributosL.get(position).getGrupo());
                i.putExtra("key_division",atributosL.get(position).getDivision());
                //i.putExtra("key_autor",atributosL.get(position).getAutor());
                //i.putExtra("key_fechapublicacion", atributosL.get(position).getFechaPublicacion());
                i.putExtra("key_archivo", atributosL.get(position).getArchivo());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return atributosL.size();
    }

    static class HolderActividades extends RecyclerView.ViewHolder{
        CardView card;
        TextView titulo;
        TextView descripcion;
        TextView estadoAc;
        TextView fechaPubli;
        ImageView icono;

        public HolderActividades(@NonNull View itemView) {
            super(itemView);

            card        = itemView.findViewById(R.id.card_actividad);
            titulo      = itemView.findViewById(R.id.tituloAct);
            descripcion = itemView.findViewById(R.id.descripcionAct);
            estadoAc    = itemView.findViewById(R.id.estadoAc);
            fechaPubli  = itemView.findViewById(R.id.fechaPubli);
            icono       = itemView.findViewById(R.id.maletin);


        }
    }
}
