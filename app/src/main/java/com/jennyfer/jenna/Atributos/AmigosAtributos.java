package com.jennyfer.jenna.Atributos;

import com.jennyfer.jenna.ComunicaciónFragments.Comunicacion;

public class AmigosAtributos extends Comunicacion {
    private String tipoM;

    public AmigosAtributos() { }

    public AmigosAtributos(String id, String nombre, String mensajito, String horamensajito, String fotoPerfil, String carrera, String tipoM) {
        super(id, nombre, mensajito, horamensajito, fotoPerfil, carrera);
        this.tipoM = tipoM;
    }

    public String getTipoM() {
        return tipoM;
    }

    public void setTipoM(String tipoM) {
        this.tipoM = tipoM;
    }
}

