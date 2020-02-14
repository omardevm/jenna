package com.jennyfer.jenna.Quizz.Models;

import java.io.Serializable;

public class Dimension implements Serializable {

    private String tag;
    private int dim_imagen;
    private String[] factores;
    private int[] factores_imagenes;

    public Dimension(){}

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getDim_imagen() {
        return dim_imagen;
    }

    public void setDim_imagen(int dim_imagen) {
        this.dim_imagen = dim_imagen;
    }

    public String[] getFactores() {
        return factores;
    }

    public void setFactores(String[] factores) {
        this.factores = factores;
    }

    public int[] getFactores_imagenes() {
        return factores_imagenes;
    }

    public void setFactores_imagenes(int[] factores_imagenes) {
        this.factores_imagenes = factores_imagenes;
    }
}
