package com.jennyfer.jenna.Quizz.Models;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GraphData implements Serializable {

    private String titulo;
    private String color_gnal;
    private ArrayList<FactoresResp> factores;

    public GraphData(){}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getColor_gnal() {
        return color_gnal;
    }

    public void setColor_gnal(String color_gnal) {
        this.color_gnal = color_gnal;
    }

    public ArrayList<FactoresResp> getFactores() {
        return factores;
    }

    public void setFactores(ArrayList<FactoresResp> factores) {
        this.factores = factores;
    }
}
