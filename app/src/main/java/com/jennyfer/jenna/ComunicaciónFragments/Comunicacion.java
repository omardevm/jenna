package com.jennyfer.jenna.ComunicaciónFragments;

public class Comunicacion {

    private String id;
    private String nombre;
    private int estado;
    private String mensajito;
    private String horamensajito;
    private String fotoPerfil;
    private String carrera;

    public Comunicacion() {}


    public Comunicacion(String id, String nombre, int estado, String fotoPerfil, String carrera) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.fotoPerfil = fotoPerfil;
        this.carrera = carrera;
    }

    public Comunicacion(String id, String nombre, String mensajito, String horamensajito, String fotoPerfil, String carrera) {
        this.id = id;
        this.nombre = nombre;
        this.mensajito = mensajito;
        this.horamensajito = horamensajito;
        this.fotoPerfil = fotoPerfil;
        this.carrera = carrera;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }

    public String getMensajito() { return mensajito; }
    public void setMensajito(String mensajito) { this.mensajito = mensajito; }

    public String getHoramensajito() { return horamensajito; }
    public void setHoramensajito(String horamensajito) { this.horamensajito = horamensajito; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }
}
