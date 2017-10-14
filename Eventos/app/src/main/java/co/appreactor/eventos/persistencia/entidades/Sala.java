package co.appreactor.eventos.persistencia.entidades;

import java.io.Serializable;

/**
 * Created by lord_nightmare on 5/10/17.
 */

public class Sala implements Serializable {

    private Long idSala;
    private String nombre;
    private String direccion;
    private String telefono;
    private Boolean disponible;

    public Sala() {
    }

    public Sala(Long idSala) {
        this.idSala = idSala;
    }

    public Long getIdSala() {
        return idSala;
    }

    public void setIdSala(Long idSala) {
        this.idSala = idSala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}
