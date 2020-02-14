package com.jennyfer.jenna.Atributos;

public class MensajeTexto {

    private String mensaje;
    private String horaMensaje;
    private String fechaCompleta;
    private int tipoMensaje;

    public MensajeTexto() { }

    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getHoraMensaje() { return horaMensaje; }
    public void setHoraMensaje(String horaMensaje) {
        this.horaMensaje = horaMensaje;
    }

    public int getTipoMensaje() {
        return tipoMensaje;
    }
    public void setTipoMensaje(int tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public String getFechaCompleta() { return fechaCompleta; }

    public void setFechaCompleta(String fechaCompleta) { this.fechaCompleta = fechaCompleta; }
}
