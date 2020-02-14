package com.jennyfer.jenna.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Atributos.AsesoriasAtributos;

import java.util.ArrayList;

public class AsesoriasAdapter extends BaseAdapter {
    public Context c;
    public ArrayList<AsesoriasAtributos> asesorias;
    public LayoutInflater inflater;

    public AsesoriasAdapter(Context c, ArrayList<AsesoriasAtributos> asesorias) {
        this.c = c;
        this.asesorias = asesorias;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return asesorias.size();
    }

    @Override
    public Object getItem(int position) { return asesorias.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.card_asesorias, parent, false);
        }
        TextView titulo = convertView.findViewById(R.id.TituloAses);
        TextView descri = convertView.findViewById(R.id.DescripAses);
        ImageView image = convertView.findViewById(R.id.asesDefault);

        AsesoriasAtributos actividad = asesorias.get(position);

        //animacion de los cardview
        Animation animation = AnimationUtils.loadAnimation(c, R.anim.side_left);
        convertView.startAnimation(animation);

        //obtencion de los datos
        descri.setText(actividad.getDescripcion());
        titulo.setText(actividad.getTituloAses());
        Glide.with(c).load(actividad.getImagen())
                //.placeholder(R.drawable.profile)
                .override(1080,775)
                .into(image);

        return convertView;
    }
}
