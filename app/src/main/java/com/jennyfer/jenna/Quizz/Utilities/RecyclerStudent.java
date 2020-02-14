package com.jennyfer.jenna.Quizz.Utilities;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jennyfer.jenna.Quizz.Models.GraphData;
import com.jennyfer.jenna.Quizz.Models.Studentt;
import com.jennyfer.jenna.R;

import java.util.ArrayList;

public class RecyclerStudent extends RecyclerView.Adapter<RecyclerStudent.ViewHolder> {

    private ArrayList<Studentt> usuario;


    public RecyclerStudent(ArrayList<Studentt> usuario) {
        this.usuario = usuario;
    }

    @NonNull
    @Override
    public RecyclerStudent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new RecyclerStudent.ViewHolder(layoutInflater.inflate(R.layout.student_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerStudent.ViewHolder holder, int position) {
        Studentt item = usuario.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return usuario.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView usuario;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            usuario = itemView.findViewById(R.id.label_usuario);
        }

        public void bind(Studentt factordatas) {

            usuario.setText(factordatas.getUsuario() );
        }




    }

}
