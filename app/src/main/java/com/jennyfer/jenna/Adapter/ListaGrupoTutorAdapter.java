package com.jennyfer.jenna.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jennyfer.jenna.Atributos.ActividadesAtributos;
import com.jennyfer.jenna.CentroDeActividades.ActividadProfesor;
import com.jennyfer.jenna.CentroDeActividades.Tareas.VistaActividad;
import com.jennyfer.jenna.ListAlumnos;
import com.jennyfer.jenna.R;

import java.util.List;

public class ListaGrupoTutorAdapter extends RecyclerView.Adapter <ListaGrupoTutorAdapter.HolderAdapter> {
    private List<ActividadesAtributos> atributos;
    private Context context;

    public ListaGrupoTutorAdapter (List<ActividadesAtributos> atributos, Context context, ActividadProfesor profesor){
        this.atributos = atributos;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_capitulos,parent,false);
        return new HolderAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAdapter holder, final int position) {
        holder.carrera.setText(atributos.get(position).getDivision());
        holder.grupo.setText(atributos.get(position).getGrupo());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ListAlumnos.class);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() { return atributos.size(); }

    static class HolderAdapter extends RecyclerView.ViewHolder{
        CardView card;
        TextView carrera;
        TextView grupo;

        public HolderAdapter(@NonNull View itemView) {
            super(itemView);

            card     = itemView.findViewById(R.id.card);
            carrera  = itemView.findViewById(R.id.TCarrera);
            grupo    = itemView.findViewById(R.id.Tgrupo);



        }
    }
}
