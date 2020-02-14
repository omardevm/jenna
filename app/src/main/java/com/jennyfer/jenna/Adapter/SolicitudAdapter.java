package com.jennyfer.jenna.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jennyfer.jenna.BuzonDeCorreo.Amigos.SolicitudAmigos;
import com.jennyfer.jenna.Atributos.SolicitudAtributos;
import com.jennyfer.jenna.R;

import java.util.List;

public class SolicitudAdapter extends RecyclerView.Adapter<SolicitudAdapter.SolicitudHolder> {

    private List<SolicitudAtributos> atributosList;
    private Context context;
    private SolicitudAmigos sa;

    public SolicitudAdapter(List<SolicitudAtributos> atributosList, Context context, SolicitudAmigos sa){
        this.atributosList = atributosList;
        this.context = context;
        this.sa = sa;
    }
    @NonNull
    @Override
    public SolicitudHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_solicitud,parent,false);
        return new SolicitudHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SolicitudHolder solicitudHolder, final int position) {
        Glide.with(context)
                .load(atributosList.get(position).getFotoPerfil())
                .centerCrop()
                .thumbnail(0.2f)
                .into(solicitudHolder.foto);

        solicitudHolder.nombre.setText(atributosList.get(position).getNombre());
        solicitudHolder.carrera.setText(atributosList.get(position).getCarrera());

        switch (atributosList.get(position).getEstado()){
            case 2:
                solicitudHolder.cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sa.cancelarSolicitud(atributosList.get(position).getId());
                    }
                });
                solicitudHolder.aceptar.setVisibility(View.GONE);
                break;
            case 3:
                solicitudHolder.cancelar.setVisibility(View.VISIBLE);

                solicitudHolder.cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sa.cancelarSolicitud(atributosList.get(position).getId());
                    }
                });

                solicitudHolder.aceptar.setVisibility(View.VISIBLE);
                solicitudHolder.aceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sa.aceptarSolicitud(atributosList.get(position).getId());
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() { return atributosList.size(); }

    static class SolicitudHolder extends RecyclerView.ViewHolder{

        CardView solicitudCard;
        ImageView foto;
        TextView nombre, carrera;
        Button aceptar;
        Button cancelar;

        public SolicitudHolder(@NonNull View itemView) {
            super(itemView);

            foto     = itemView.findViewById(R.id.fotoSolicitud);
            nombre   = itemView.findViewById(R.id.nombreSolicitud);
            aceptar  = itemView.findViewById(R.id.aceptarSolicitud);
            carrera  = itemView.findViewById(R.id.carreraSolicitud);
            cancelar = itemView.findViewById(R.id.eliminarSolicitud);
        }
    }
}
