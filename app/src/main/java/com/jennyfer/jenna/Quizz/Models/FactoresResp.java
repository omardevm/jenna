package com.jennyfer.jenna.Quizz.Models;

import java.io.Serializable;

public class FactoresResp implements Serializable {
    private String tittulo;
    private Boolean status;
    private String color;
    private String calif;

    public FactoresResp(String tittulo, Boolean status, String color, String calif){
        this.tittulo = tittulo;
        this.status = status;
        this.color = color;
        this.calif = calif;
    }


    public String getTittulo() {
        return tittulo;
    }

    public void setTittulo(String tittulo) {
        this.tittulo = tittulo;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCalif() {
        return calif;
    }

    public void setCalif(String calif) {
        this.calif = calif;
    }
}



