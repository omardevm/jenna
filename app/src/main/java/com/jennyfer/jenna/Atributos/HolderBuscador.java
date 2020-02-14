package com.jennyfer.jenna.Atributos;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jennyfer.jenna.R;

public class HolderBuscador extends RecyclerView.ViewHolder {

    private ImageView perfil;
    private TextView nombre, carrera,estadoB;
    private Button enviarS, botonTemp;
    private CardView card;

    public HolderBuscador(@NonNull View v) {
        super(v);

        perfil    = itemView.findViewById(R.id.fotoBuscador);
        nombre    = itemView.findViewById(R.id.nombreBuscador);
        estadoB   = itemView.findViewById(R.id.estadoNumero);
        carrera   = itemView.findViewById(R.id.carreraBuscador);
        enviarS   = itemView.findViewById(R.id.enviarSolicitud);
        botonTemp = itemView.findViewById(R.id.botonEstado3);
        card      = itemView.findViewById(R.id.card_solicitud);

    }

    public ImageView getPerfil() {
        return perfil;
    }
    public void setPerfil(ImageView perfil) {
        this.perfil = perfil;
    }

    public TextView getNombre() {
        return nombre;
    }
    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getCarrera() { return carrera; }
    public void setCarrera(TextView carrera) {
        this.carrera = carrera;
    }

    public Button getEnviarS() {
        return enviarS;
    }
    public void setEnviarS(Button enviarS) {
        this.enviarS = enviarS;
    }

    public TextView getEstadoB() { return estadoB; }
    public void setEstadoB(TextView estadoB) { this.estadoB = estadoB; }

    public Button getBotonTemp() { return botonTemp; }
    public void setBotonTemp(Button botonTemp) { this.botonTemp = botonTemp; }

    public CardView getCard() { return card; }
    public void setCard(CardView card) { this.card = card; }


}
