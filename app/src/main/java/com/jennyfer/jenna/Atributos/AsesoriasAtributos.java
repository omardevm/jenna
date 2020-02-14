package com.jennyfer.jenna.Atributos;

import java.io.Serializable;

public class AsesoriasAtributos implements Serializable{

    private int idAsesoria;
    private String tituloAses;
    private String descripcion;
    private String foto;
    private String imagen;

    public int getIdAsesoria() { return idAsesoria; }
    public void setIdAsesoria(int idAsesoria) { this.idAsesoria = idAsesoria; }

    public String getTituloAses() { return tituloAses; }
    public void setTituloAses(String tituloAses) { this.tituloAses = tituloAses; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
}
