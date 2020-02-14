package com.jennyfer.jenna.Asesoria;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.jennyfer.jenna.Asesoria.VistaAsesoria;
import com.jennyfer.jenna.Inicio;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Atributos.AsesoriasAtributos;
import com.jennyfer.jenna.Tools.DescargarImagen;

import java.io.Serializable;

public class AsesoriasAlumno extends Fragment {

    private ImageView ase;

    //final static String URL_WEB_SERVICE="http://pita.x10host.com/PHP/Asesorias_GETALL.php";
    final static String URL_WEB_SERVICE="https://pitav3.000webhostapp.com/PRUEBAPHP/Actividades_GETALL.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        View view = inflater.inflate(R.layout.asesorias_alumno, container, false);

        ((Inicio) getActivity()).getSupportActionBar().setTitle("Asesorías recomendadas");

        ase = view.findViewById(R.id.ase);
        //String urlA = "http://pita.x10host.com/Imagenes/asesor.png";
        String urlA = "https://pitav3.000webhostapp.com/Imagenes/asesor.png";
        Glide.with(getContext())
                .load(urlA)
                .centerCrop()
                .thumbnail(0.2f)
                .into(ase);

        final ListView lv = view.findViewById(R.id.rv_asesorias);
        new DescargarImagen(getActivity(),URL_WEB_SERVICE,lv).execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AsesoriasAtributos a = (AsesoriasAtributos) parent.getItemAtPosition(position);
                Intent act = new Intent(getContext(), VistaAsesoria.class);
                act.putExtra("objetoData",(Serializable) a);
                startActivity(act);
            }
        });

        return view;
    }
}


