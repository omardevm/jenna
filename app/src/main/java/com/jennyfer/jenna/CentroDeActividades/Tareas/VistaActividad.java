package com.jennyfer.jenna.CentroDeActividades.Tareas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jennyfer.jenna.R;

public class VistaActividad extends AppCompatActivity {

    private TextView titulo, hora, fecha, grupoD, descri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_actividad);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish();  }});

        titulo = findViewById(R.id.tituloA);
        hora   = findViewById(R.id.horaA);
        fecha  = findViewById(R.id.fechaA);
        grupoD = findViewById(R.id.grupoDivision);
        descri = findViewById(R.id.vista_descripcion);


        String tituloB = getIntent().getExtras().getString("key_titulo");
        titulo.setText(tituloB);
        String horaB = getIntent().getExtras().getString("key_hora");
        hora.setText(horaB);
        String fechaB = getIntent().getExtras().getString("key_fecha");
        fecha.setText(fechaB);
        String descrip = getIntent().getExtras().getString("key_descripcion");
        descri.setText(descrip);
        String grupo = getIntent().getExtras().getString("key_grupo");
        String division = getIntent().getExtras().getString("key_division");
        grupoD.setText(grupo +" - " +division);

    }
}
