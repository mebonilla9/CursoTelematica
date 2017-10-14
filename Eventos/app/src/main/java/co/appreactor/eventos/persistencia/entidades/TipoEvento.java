package co.appreactor.eventos.persistencia.entidades;

import java.io.Serializable;

/**
 * Created by lord_nightmare on 5/10/17.
 */

public class TipoEvento implements Serializable {

    private Long idTipoEvento;
    private String nombre;
    private Boolean estado;

    public TipoEvento() {
    }

    public TipoEvento(Long idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    public Long getIdTipoEvento() {
        return idTipoEvento;
    }

    public void setIdTipoEvento(Long idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
