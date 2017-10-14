package co.appreactor.eventos.persistencia.dto;

/**
 * Created by lord_nightmare on 13/10/17.
 */

public class FechaDTO {

    private String fechaTexto;
    private Long fechaUnix;

    public String getFechaTexto() {
        return fechaTexto;
    }

    public void setFechaTexto(String fechaTexto) {
        this.fechaTexto = fechaTexto;
    }

    public Long getFechaUnix() {
        return fechaUnix;
    }

    public void setFechaUnix(Long fechaUnix) {
        this.fechaUnix = fechaUnix;
    }
}
