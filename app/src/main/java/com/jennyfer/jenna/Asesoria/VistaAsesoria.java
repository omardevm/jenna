package com.jennyfer.jenna.Asesoria;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.jennyfer.jenna.Atributos.AsesoriasAtributos;
import com.jennyfer.jenna.R;

public class VistaAsesoria extends AppCompatActivity {
    private TextView tit, des,fecha,aut;
    private ImageView fot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_asesoria);

        tit   = findViewById(R.id.vista_titulo);
        des   = findViewById(R.id.vista_descripcion);
        fot   = findViewById(R.id.vista_imagen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }});

        AsesoriasAtributos item = (AsesoriasAtributos) getIntent().getExtras().getSerializable("objetoData");
        tit.setText(item.getTituloAses());
        toolbar.setTitle(item.getTituloAses());
        des.setText(item.getDescripcion());
        Glide.with(this)
                .load(item.getFoto())
                .into(fot);


    }
}
