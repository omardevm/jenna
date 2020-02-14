package com.jennyfer.jenna.Quizz.Models;

import java.io.Serializable;

public class Pregunta implements Serializable {
    private int idpreguntas;
    private String contenido;
    private String opciones;
    private String atributo;
    private int idfactores;

    public Pregunta(){}


    public int getIdpreguntas() {
        return idpreguntas;
    }

    public void setIdpreguntas(int idpreguntas) {
        this.idpreguntas = idpreguntas;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getOpciones() {
        return opciones;
    }

    public void setOpciones(String opciones) {
        this.opciones = opciones;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public int getIdfactores() {
        return idfactores;
    }

    public void setIdfactores(int idfactores) {
        this.idfactores = idfactores;
    }
}
