package com.jennyfer.jenna.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jennyfer.jenna.Atributos.MensajeTexto;
import com.jennyfer.jenna.R;

import java.util.List;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder> {
    private List<MensajeTexto> mensajeTextos;
    private Context c;

    public MensajeAdapter(List<MensajeTexto> mensajeTextos, Context c) {
        this.mensajeTextos = mensajeTextos;
        this.c = c;
    }

    @NonNull
    @Override
    public MensajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mensajes,parent,false);
        return new MensajeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MensajeViewHolder holder, int i) {
        RelativeLayout.LayoutParams rl  = (RelativeLayout.LayoutParams) holder.cardView.getLayoutParams();
        FrameLayout.LayoutParams fl     = (FrameLayout.LayoutParams) holder.BG.getLayoutParams();
        FrameLayout.LayoutParams fla     = (FrameLayout.LayoutParams) holder.BGA.getLayoutParams();
        LinearLayout.LayoutParams llH   = (LinearLayout.LayoutParams) holder.TvHora.getLayoutParams();
        LinearLayout.LayoutParams f     = (LinearLayout.LayoutParams) holder.fecha.getLayoutParams();
        LinearLayout.LayoutParams llM   = (LinearLayout.LayoutParams) holder.TvMensaje.getLayoutParams();
        if (mensajeTextos.get(i).getTipoMensaje() == 1) {
            holder.BG.setBackgroundResource(R.drawable.right_message);
            rl.addRule(RelativeLayout.ALIGN_PARENT_END,0);
            rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            fla.gravity  = Gravity.END;
            f.gravity    = Gravity.END;
            fl.gravity   = Gravity.END;
            llH.gravity  = Gravity.END;
            llM.gravity  = Gravity.END;
            holder.TvMensaje.setGravity(Gravity.END);
        }else if (mensajeTextos.get(i).getTipoMensaje() == 2){
            holder.BG.setBackgroundResource(R.drawable.left_message);
            rl.addRule(RelativeLayout.ALIGN_PARENT_START,0);
            rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            fla.gravity  = Gravity.START;
            f.gravity    = Gravity.START;
            fl.gravity   = Gravity.START;
            llM.gravity  = Gravity.START;
            llH.gravity  = Gravity.START;
            holder.TvMensaje.setGravity(Gravity.START);
        }
        holder.cardView.setLayoutParams(rl);
        holder.BG.setLayoutParams(fl);
        holder.BG.setLayoutParams(fla);
        holder.TvHora.setLayoutParams(llH);
        holder.TvMensaje.setLayoutParams(llM);
        holder.fecha.setLayoutParams(f);
        holder.TvMensaje.setText(mensajeTextos.get(i).getMensaje());
        holder.TvHora.setText(mensajeTextos.get(i).getHoraMensaje());

        holder.fecha.setVisibility(View.GONE);
        holder.fecha.setText(mensajeTextos.get(i).getFechaCompleta());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.fecha.getVisibility() == View.VISIBLE) { //si es Visible lo pones Gone
                    holder.fecha.setVisibility(View.GONE);
                } else { // si no es Visible, lo pones
                    holder.fecha.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mensajeTextos.size();
    }

    static class MensajeViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView TvHora, fecha , TvMensaje;
        LinearLayout BG, BGA;
        MensajeViewHolder (View item){
            super(item);

            cardView  = itemView.findViewById(R.id.boxmensaje);
            TvMensaje = itemView.findViewById(R.id.mensaje);
            TvHora    = itemView.findViewById(R.id.hora);
            fecha     = itemView.findViewById(R.id.fecha);
            BG        = itemView.findViewById(R.id.BG);
            BGA       = itemView.findViewById(R.id.BGA);
        }
    }

}
